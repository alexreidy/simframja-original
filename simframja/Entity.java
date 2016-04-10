package simframja;

import java.util.Collection;

/**
 * A versatile Spatial, Mobile, and Visual, designed to provide
 * a standard interface with all essential methods
 * for entity simulation and interaction.
 */
public interface Entity extends Spatial, Mobile, Visual {
    
    /**
     * Updates the Entity with respect to a context of other Entities.
     * @param context entities that may be considered during the update
     */
    void update(Collection<? extends Entity> context);
    
    void setVelocity(Vector2 v);
    
    Vector2 getVelocity();
    
    /**
     * Returns Entities that this Entity produces. For example,
     * a certain Entity might produce bullets or dropped items.
     * Products may be put into a "master" collection of entities
     * in a simulation engine.
     */
    Collection<? extends Entity> products();
    
    /**
     * What should happen when this Entity collides with the given Entity.
     * @param e the Entity to collide with
     */
    void collideWith(Entity e);
    
    /**
     * Checks for collisions, handles them as necessary, and returns
     * any entities that are in contact.
     * @param context the entities to potentially collide with
     * @return the entities that are found to be in contact
     */
    Collection<? extends Entity>
    manageCollisionsAndGetContacts(Collection<? extends Entity> context);
    
    double getHealth();
    
    void setHealth(double h);
    
    void changeHealth(double change);
    
    /**
     * Kills the Entity, such that isAlive() is false.
     */
    void die();
    
    /**
     * Returns true if the Entity is alive.
     */
    boolean isAlive();
    
    /**
     * Sets the Entity's lifespan, such that it will die once it has updated
     * the given number of times.
     * @param updates the number of updates that make up the lifespan
     */
    void setLifespan(int updates);
    
    /**
     * Sets the Group ID, which can be used to group various Entities.
     * May be used, for example, to prevent a projectile from harming
     * the Entity that launched it.
     */
    void setGroupId(Entity id);
    
    /**
     * Returns the Group ID, which can be used to group various Entities.
     * May be used, for example, to prevent a projectile from harming
     * the Entity that launched it.
     */
    Entity getGroupId();
    
}