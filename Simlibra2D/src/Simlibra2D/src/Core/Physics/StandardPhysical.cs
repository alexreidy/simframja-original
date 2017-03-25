using System;

namespace Simlibra2D
{
    public class StandardPhysical : AbstractPhysical
    {
        /// <summary>
        /// This is the accumulated force, which gets reset after
        /// each physics update.
        /// </summary>
        private Vector2f _force = new Vector2f();

        private Vector2f _velocity = new Vector2f();

        public override IVector2f Velocity => _velocity;

        public float? MaxVelocityComponent { get; set; }

        public override float Mass { get; set; } = 1f;

        public override void ApplyForce(float xComponent, float yComponent)
        {
            _force.X += xComponent;
            _force.Y += yComponent;
        }

        public override void SetVelocity(float xComponent, float yComponent)
        {
            if (this.MaxVelocityComponent.HasValue)
            {
                float maxSpeed = this.MaxVelocityComponent.Value;

                if (xComponent > maxSpeed)
                {
                    xComponent = maxSpeed;
                }
                else if (xComponent < -maxSpeed)
                {
                    xComponent = -maxSpeed;
                }

                if (yComponent > maxSpeed)
                {
                    yComponent = maxSpeed;
                }
                else if (yComponent < -maxSpeed)
                {
                    yComponent = -maxSpeed;
                }
            }
            _velocity.X = xComponent;
            _velocity.Y = yComponent;
        }

        public override void UpdatePhysics()
        {
            float xAccel = _force.X / this.Mass;
            float yAccel = _force.Y / this.Mass;

            this.ChangeVelocity(xAccel, yAccel);

            this.TargetSpatial.Move(this.Velocity);

            _force.X = 0;
            _force.Y = 0;
        }

    }
}