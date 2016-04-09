package simframja;

import java.util.Collection;

public interface Entity extends Spatial, Mobile, Visual {
    
    void update(Collection<? extends Entity> context);
    
    Collection<? extends Entity> products();
    
    
    void collideWith(Entity e);
    
    Collection<? extends Entity>
    manageCollisionsAndGetContacts(Collection<? extends Entity> context);
    
    
    double getHealth();
    
    void setHealth(double h);
    
    void changeHealth(double change);
    
    void die();
    
    boolean isAlive();
    
    void setLifespan(int updates);
    
    
    void setGroupId(Entity id);
    
    Entity getGroupId();
    
}