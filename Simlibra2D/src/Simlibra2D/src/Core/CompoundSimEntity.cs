using System.Collections.Generic;

namespace Simlibra2D
{
    public abstract class CompoundSimEntity<T> : SimEntity<T>
        where T : SimEntity<T>
    {
        private ISet<T> _constituents = new HashSet<T>();

        protected IEnumerable<T> Constituents => _constituents;

        private List<Box> _constituentBoxes;

        public virtual void AddConstituent(T entity)
        {
            _constituents.Add(entity);

            this.BackingPhysical.Mass += entity.Mass;

            _constituentBoxes = null;
            _boundingBox = null;
        }

        public virtual void RemoveConstituent(T entity)
        {
            _constituents.Remove(entity);

            this.BackingPhysical.Mass -= entity.Mass;

            _constituentBoxes = null;
            _boundingBox = null;
        }

        public override void SetPosition(float x, float y)
        {
            float xOffset = x - this.Position.X;
            float yOffset = y - this.Position.Y;

            foreach (Box box in this.LocalBoxes)
            {
                box.SetPosition(box.Position.X + xOffset, box.Position.Y + yOffset);
            }

            foreach (T ent in _constituents)
            {
                ent.SetPosition(ent.Position.X + xOffset, ent.Position.Y + yOffset);
            }

            if (_boundingBox != null)
            {
                _boundingBox.SetPosition(
                    _boundingBox.Position.X + xOffset,
                    _boundingBox.Position.Y + yOffset);
            }

            base.SetPosition(x, y);
        }

        public override IEnumerable<Box> ConstituentBoxes
        {
            get
            {
                if (_constituentBoxes != null)
                {
                    return _constituentBoxes;
                }

                _constituentBoxes = new List<Box>();

                foreach (Box box in this.LocalBoxes)
                {
                    _constituentBoxes.Add(box);
                }

                foreach (T ent in _constituents)
                {
                    foreach (Box box in ent.ConstituentBoxes)
                    {
                        _constituentBoxes.Add(box);
                    }
                }

                return _constituentBoxes;
            }
        }

        private Box _boundingBox;

        public override Box BoundingBox
        {
            get
            {
                if (_boundingBox != null)
                {
                    return _boundingBox;
                }

                float xMin = float.PositiveInfinity;
                float yMin = float.PositiveInfinity;
                float xMax = float.NegativeInfinity;
                float yMax = float.NegativeInfinity;

                foreach (Box box in this.ConstituentBoxes)
                {
                    if (box.Position.X < xMin)
                    {
                        xMin = box.Position.X;
                    }
                    if (box.Position.Y < yMin)
                    {
                        yMin = box.Position.Y;
                    }
                    if (box.Position.X + box.Width > xMax)
                    {
                        xMax = box.Position.X + box.Width;
                    }
                    if (box.Position.Y + box.Height > yMax)
                    {
                        yMax = box.Position.Y + box.Height;
                    }
                }
                
                return _boundingBox = new Box(
                    x: xMin,
                    y: yMin,
                    width: xMax - xMin,
                    height: yMax - yMin);
            }
        }

        public override bool IsTouching(ISpatial spatial)
        {
            if (!this.BoundingBox.IsTouching(spatial.BoundingBox))
            {
                return false;
            }

            foreach (Box myBox in this.ConstituentBoxes)
            {
                foreach (Box otherBox in spatial.ConstituentBoxes)
                {
                    if (myBox.IsTouching(otherBox))
                    {
                        return true;
                    }
                }
            }

            return false;
        }

        public override ICollection<T> EntitiesThatAreTouching(ISpatial spatial)
        {
            var contacts = new HashSet<T>();

            // maybe someday consider the number of constituents vs the number of LocalBoxes
            // to see which to check first...

            foreach (T constituent in _constituents)
            {
                foreach (T contact in constituent.EntitiesThatAreTouching(spatial))
                {
                    contacts.Add(contact);
                }
            }

            // If any constituents are touching the spatial, so is this by extension.
            if (contacts.Count > 0)
            {
                contacts.Add((T)(SimEntity<T>)this);
            }
            else
            {
                foreach (Box box in this.LocalBoxes)
                {
                    if (box.IsTouching(spatial))
                    {
                        contacts.Add((T)(SimEntity<T>)this);
                        break;
                    }
                }
            }

            return contacts;
        }

        public override void OnCollisionWith(T entity)
        {
        }

        public override void WhileTouching(T entity)
        {
        }

        private ISet<T> _previousContacts = new HashSet<T>();

        public override ISet<T> HandleCollisionsAndGetContacts(IEnumerable<T> context, bool applyBoundingBoxFilter = true)
        {
            var allContacts = new HashSet<T>();

            if (applyBoundingBoxFilter)
            {
                var potentialContacts = new List<T>();
                foreach (T entity in context)
                {
                    if (this.BoundingBox.IsTouching(entity.BoundingBox))
                    {
                        potentialContacts.Add(entity);
                    }
                }

                context = potentialContacts;
            }

            foreach (Box box in this.LocalBoxes)
            {
                foreach (T entity in context)
                {
                    foreach (T contact in entity.EntitiesThatAreTouching(box))
                    {
                        allContacts.Add(contact);
                    }
                }
            }

            /*
            foreach (T constituent in _constituents)
            {
                ISet<T> contacts = constituent.HandleCollisionsAndGetContacts(context, false);
                
                foreach (T contact in contacts)
                {
                    if (!contact.IsPhantom)
                    {
                        this.WhileTouching(contact);

                        if (!_previousContacts.Contains(contact))
                        {
                            this.OnCollisionWith(contact);
                        }
                    }
                    
                    allContacts.Add(contact);
                }

                _previousContacts = contacts;
            }*/

            foreach (T constituent in _constituents)
            {
                foreach (T contact in constituent.HandleCollisionsAndGetContacts(context, false))
                {
                    allContacts.Add(contact);
                }
            }

            foreach (T contact in allContacts)
            {
                if (!contact.IsPhantom)
                {
                    this.WhileTouching(contact);

                    if (!_previousContacts.Contains(contact))
                    {
                        this.OnCollisionWith(contact);
                    }
                }
            }

            _previousContacts = allContacts;

            return allContacts;
        }

    }
}