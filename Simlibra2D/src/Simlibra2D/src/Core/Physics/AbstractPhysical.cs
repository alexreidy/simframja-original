using System;

namespace Simlibra2D
{
    public abstract class AbstractPhysical : IPhysical
    {
        public ISpatial TargetSpatial { get; set; }

        public abstract IVector2f Velocity { get; }

        public abstract float Mass { get; set; }

        public abstract void ApplyForce(float xComponent, float yComponent);

        public void ApplyForce(IVector2f force)
        {
            this.ApplyForce(force.X, force.Y);
        }

        public abstract void SetVelocity(float xComponent, float yComponent);

        public void SetVelocity(IVector2f velocity)
        {
            this.SetVelocity(velocity.X, velocity.Y);
        }

        public void ChangeVelocity(float dx, float dy)
        {
            this.SetVelocity(this.Velocity.X + dx, this.Velocity.Y + dy);
        }

        public void ChangeVelocity(IVector2f delta)
        {
            this.ChangeVelocity(delta.X, delta.Y);
        }

        public abstract void UpdatePhysics();
    }
}