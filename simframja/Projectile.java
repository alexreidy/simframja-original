package simframja;

/**
 * A CompoundEntity that can be launched.
 */
public abstract class Projectile extends CompoundEntity {
    
    private Entity launcher;

    /**
     * Constructs a Projectile, setting its Group ID to that of its launcher.
     * @param x the initial x coordinate
     * @param y the initial y coordinate
     * @param velocity the initial velocity
     * @param launcher the Entity that launched this projectile
     */
    public Projectile(double x, double y, Vector2 velocity, Entity launcher) {
        setPosition(x, y);
        setVelocity(new Vector2(velocity.x, velocity.y));
        this.launcher = launcher;
        if (launcher != null) {
            setGroupId(launcher.getGroupId());
        }
    }
    
    /**
     * Returns the Entity that launched this projectile.
     */
    public Entity launcher() {
        return launcher;
    }
    
}