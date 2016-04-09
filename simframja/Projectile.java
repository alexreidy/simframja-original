package simframja;

import java.util.Collection;

public abstract class Projectile extends CompoundEntity {
    
    private Vector2 velocity;
    
    private Entity launcher;

    public Projectile(double x, double y, Vector2 velocity, Entity launcher) {
        setPosition(x, y);
        this.velocity = new Vector2(velocity.x, velocity.y);
        this.launcher = launcher;
    }
    
    public Entity launcher() {
        return launcher;
    }
    
    @Override
    public void update(Collection<? extends Entity> context) {
        super.update(context);
        move(velocity);
        for (Entity entity : context) {
            if (entity == this || entity == launcher)
                continue;
            if (touching(entity)) {
                collideWith(entity);
            }
        }
    }
    
}