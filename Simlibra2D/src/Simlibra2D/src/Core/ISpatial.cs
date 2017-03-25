using System.Collections.Generic;

namespace Simlibra2D
{
    public interface ISpatial : IMobile
    {
        IVector2f Position { get; }

        void SetPosition(float x, float y);

        /// <summary>
        /// This is just a helper, equivalent to calling SetPosition(position.X, position.Y).
        /// </summary>
        void SetPosition(IVector2f position);

        Box BoundingBox { get; }        

        IEnumerable<Box> ConstituentBoxes { get; }

        bool IsTouching(ISpatial spatial);
    }
}