namespace Simlibra2D
{
    public interface IPhysical
    {
        IVector2f Velocity { get; }

        float Mass { get; set; }

        void ApplyForce(float xComponent, float yComponent);

        void ApplyForce(IVector2f force);

        void SetVelocity(float xComponent, float yComponent);

        void SetVelocity(IVector2f velocity);

        void ChangeVelocity(float dx, float dy);

        void ChangeVelocity(IVector2f delta);

        void UpdatePhysics();
    }
}