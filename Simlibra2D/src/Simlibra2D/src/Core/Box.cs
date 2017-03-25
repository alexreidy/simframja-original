using System.Collections.Generic;

namespace Simlibra2D
{
    /// <summary>
    /// The Box is the elemental polygon of this framework.
    /// </summary>
    public class Box : AbstractSpatial
    {
        public float Width { get; set; }

        public float Height { get; set; }

        public Box(float x, float y, float width, float height)
        {
            this.SetPosition(x, y);
            this.Width = width;
            this.Height = height;
        }

        public Box(Vector2f position, float width, float height)
            : this(position.X, position.Y, width, height)
        {
        }

        public Box(Vector2f position, Vector2f dimension)
            : this(position, dimension.X, dimension.Y)
        {
        }

        public Box()
        {
        }

        // todo: should probably copy this defensively and cache.
        public override Box BoundingBox => this;

        public override IEnumerable<Box> ConstituentBoxes
        {
            get
            {
                yield return this;
            }
        }

        public override bool IsTouching(ISpatial spatial)
        {
            foreach (Box box in spatial.ConstituentBoxes)
            {
                if (box.Position.X > this.Position.X + this.Width)
                    continue;
                if (box.Position.X + box.Width < this.Position.X)
                    continue;
                if (box.Position.Y > this.Position.Y + this.Height)
                    continue;
                if (box.Position.Y + box.Height < this.Position.Y)
                    continue;

                return true;
            }

            return false;
        }

    }
}