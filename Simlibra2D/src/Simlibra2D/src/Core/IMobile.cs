namespace Simlibra2D
{
    public interface IMobile
    {
        void Move(float xOffset, float yOffset);

        void Move(IVector2f offset);
    }
}