using System;

using Xunit;
using Simlibra2D;

namespace VectorTests
{
    public class Tests
    {
        private Vector2f _v1 = new Vector2f(7, 7);
        private Vector2f _v2 = new Vector2f(42, 42);

        [Fact]
        public void CanAddVectors()
        {
            Vector2f result = _v1 + _v2;
            Assert.True(result.X == _v1.X + _v2.X && result.Y == _v1.Y + _v2.Y);
        }

        [Fact]
        public void CanSubtractVectors()
        {
            Vector2f result = _v1 - _v2;
            Assert.True(result.X == _v1.X - _v2.X && result.Y == _v1.Y - _v2.Y);
        }

        [Fact]
        public void CanMultiplyVectors()
        {
            Vector2f result = _v1 * _v2;
            Assert.True(result.X == _v1.X * _v2.X && result.Y == _v1.Y * _v2.Y);
        }

        [Fact]
        public void CanDivideVectors()
        {
            Vector2f result = _v1 / _v2;
            Assert.True(result.X == _v1.X / _v2.X && result.Y == _v1.Y / _v2.Y);
        }

        [Fact]
        public void CanMultiplyVectorByScalar()
        {
            Vector2f result = _v1 * 20;
            Assert.True(result.X == _v1.X * 20 && result.Y == _v1.Y * 20);
        }

        [Fact]
        public void CanDivideVectorByScalar()
        {
            Vector2f result = _v1 / 2;
            Assert.True(result.X == _v1.X / 2 && result.Y == _v1.Y / 2);
        }

        [Fact]
        public void CanCopyVector()
        {
            float originalX = _v1.X;
            float originalY = _v1.Y;

            Vector2f copy = _v1.Copy();
            Assert.True(copy.X == _v1.X && copy.Y == _v1.Y);

            copy.X += 10;
            copy.Y += 5;
            Assert.True(_v1.X == originalX && _v1.Y == originalY);
        }

    }
}