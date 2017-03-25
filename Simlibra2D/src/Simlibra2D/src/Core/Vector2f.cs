using System;

namespace Simlibra2D
{
    public interface IVector2f
    {
        float X { get; }
        float Y { get; }
        float Magnitude { get; }
    }

    public class Vector2f : IVector2f
    {
        public float X { get; set; }

        public float Y { get; set; }

        public Vector2f(float x, float y)
        {
            X = x; Y = y;
        }

        public Vector2f()
        {
        }

        /// <summary>
        /// Constructs a new Vector2f, copying another vector's components.
        /// </summary>
        public Vector2f(IVector2f other) : this(other.X, other.Y)
        {
        }

        public float Magnitude => (float)Math.Sqrt(this.X * this.X + this.Y * this.Y);

        public Vector2f Norm()
        {
            float hypot = this.Magnitude;
            return new Vector2f(this.X / hypot, this.Y / hypot);
        }

        public static Vector2f operator +(Vector2f a, Vector2f b)
        {
            return new Vector2f(a.X + b.X, a.Y + b.Y);
        }

        public static Vector2f operator -(Vector2f a, Vector2f b)
        {
            return new Vector2f(a.X - b.X, a.Y - b.Y);
        }

        public static Vector2f operator *(Vector2f a, Vector2f b)
        {
            return new Vector2f(a.X * b.X, a.Y * b.Y);
        }

        public static Vector2f operator /(Vector2f a, Vector2f b)
        {
            return new Vector2f(a.X / b.X, a.Y / b.Y);
        }

        public static Vector2f operator *(Vector2f v, float scalar)
        {
            return new Vector2f(v.X * scalar, v.Y * scalar);
        }

        public static Vector2f operator /(Vector2f v, float scalar)
        {
            return new Vector2f(v.X / scalar, v.Y / scalar);
        }

        public Vector2f Copy()
        {
            return new Vector2f(this);
        }
    }
    
}