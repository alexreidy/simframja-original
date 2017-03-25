namespace Simlibra2D
{
    public class NotPhysical : AbstractPhysical
    {
        private static Vector2f _velocity = new Vector2f();

        public override IVector2f Velocity => _velocity;

        public override float Mass { get; set; } = 1f;

        public override void ApplyForce(float xComponent, float yComponent)
        {
        }

        public override void SetVelocity(float xComponent, float yComponent)
        {
        }

        public override void UpdatePhysics()
        {
        }
    }
}