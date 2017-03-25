using System.Collections.Generic;

namespace Simlibra2D
{
    public abstract class AbstractSpatial : ISpatial
    {
        private Vector2f _position = new Vector2f();

        public IVector2f Position => _position;

        public virtual void SetPosition(float x, float y)
        {
            _position.X = x;
            _position.Y = y;
        }

        public void SetPosition(IVector2f position)
        {
            this.SetPosition(position.X, position.Y);
        }

        public virtual void Move(float xOffset, float yOffset)
        {
            this.SetPosition(_position.X + xOffset, _position.Y + yOffset);
        }

        public void Move(IVector2f offset)
        {
            this.Move(offset.X, offset.Y);
        }

        public abstract Box BoundingBox { get; }

        public abstract IEnumerable<Box> ConstituentBoxes { get; }

        public abstract bool IsTouching(ISpatial spatial);
    }
}