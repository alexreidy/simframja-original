package simframja;

public abstract class Projectile extends CompoundEntity {
    
    private Entity launcher;

    public Projectile(double x, double y, Vector2 velocity, Entity launcher) {
        setPosition(x, y);
        setVelocity(new Vector2(velocity.x, velocity.y));
        this.launcher = launcher;
        if (launcher != null) {
            setGroupId(launcher.getGroupId());
        }
    }
    
    public Entity launcher() {
        return launcher;
    }
    
}