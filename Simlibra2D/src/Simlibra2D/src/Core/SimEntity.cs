using System.Collections.Generic;

namespace Simlibra2D
{
    public abstract class SimEntity<T> : AbstractSpatial, IPhysical
        where T : SimEntity<T>
    {
        protected IPhysical BackingPhysical { get; set; } = new StandardPhysical();

        public IVector2f Velocity => this.BackingPhysical.Velocity;

        public virtual float Mass
        {
            get
            {
                return this.BackingPhysical.Mass;
            }
            set
            {
                this.BackingPhysical.Mass = value;
            }
        }

        public void ApplyForce(float xComponent, float yComponent)
        {
            this.BackingPhysical.ApplyForce(xComponent, yComponent);
        }

        public void ApplyForce(IVector2f force)
        {
            this.BackingPhysical.ApplyForce(force);
        }

        public void SetVelocity(float xComponent, float yComponent)
        {
            this.BackingPhysical.SetVelocity(xComponent, yComponent);
        }

        public void SetVelocity(IVector2f velocity)
        {
            this.BackingPhysical.SetVelocity(velocity);
        }

        public void ChangeVelocity(float dx, float dy)
        {
            this.BackingPhysical.ChangeVelocity(dx, dy);
        }

        public void ChangeVelocity(IVector2f delta)
        {
            this.BackingPhysical.ChangeVelocity(delta);
        }

        public void UpdatePhysics()
        {
            this.BackingPhysical.UpdatePhysics();
        }

        /// <summary>
        /// Phantoms are detected during collision handling and considered contacts,
        /// but WhileTouching(phantom) and OnCollisionWith(phantom) are never called.
        /// You're aware of the phantom, and when in contact it can
        /// affect you, but you can't affect it.
        /// </summary>
        public bool IsPhantom { get; protected set; }

        private List<Box> _boxes = new List<Box>();

        /// <summary>
        /// The boxes that are local parts of this object,
        /// in contrast to ConstituentBoxes, which include LocalBoxes
        /// and, recursively, all ConstituentBoxes of constituents.
        /// </summary>
        public IReadOnlyCollection<Box> LocalBoxes => _boxes;

        public void AddBox(Box box)
        {
            _boxes.Add(box);
        }

        public void RemoveBox(Box box)
        {
            _boxes.Remove(box);
        }

        /// <summary>
        /// Returns the entities, including "this" and any constituents,
        /// that are touching the given entity.
        /// </summary>
        public abstract ICollection<T> EntitiesThatAreTouching(ISpatial spatial);

        /// <summary>
        /// Searches the entity context for entities that are touching this one,
        /// calls OnCollisionWith/WhileTouching appropriately, and returns the set of contacts.
        /// </summary>
        public abstract ISet<T> HandleCollisionsAndGetContacts(IEnumerable<T> context, bool applyBoundingBoxFilter = true);

        /// <summary>
        /// Called during collision handling when this entity is found to be touching an
        /// entity that it was not touching when collisions were previously checked.
        /// </summary>
        public abstract void OnCollisionWith(T entity);

        /// <summary>
        /// Called during collision handling when this entity is found to be
        /// touching a given entity.
        /// </summary>
        public abstract void WhileTouching(T entity);

    }
}