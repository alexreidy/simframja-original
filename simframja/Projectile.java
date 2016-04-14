package simframja;

import java.util.Collection;

/**
 * A CompoundEntity that can be launched.
 */
public abstract class Projectile extends CompoundEntity {
    
    private Entity launcher;
    
    private Vector2 target;
    
    double speed = 0;

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
        init();
    }
    
    public void init() {
        if (launcher != null) {
            setGroupId(launcher.getGroupId());
        }
    }
    
    public void goTo(Vector2 target, double speed) {
        setVelocity(new Vector2( target.copy().add(getPosition().copy().multiply(-1)).norm().multiply(speed)) );
    }
    
    public void seek(Vector2 target, double speed) {
        this.target = target;
        this.speed = speed;
    }
    
    @Override
    public void update(Collection<? extends Entity> context) {
        super.update(context);
        
        manageCollisionsAndGetContacts(context);
        
        if (target != null) {
            goTo(target, speed);
        }
    }
    
    /**
     * Returns the Entity that launched this projectile.
     */
    public Entity launcher() {
        return launcher;
    }
    
}