package simframja;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.awt.geom.Rectangle2D;

/**
 * An AbstractEntity based on other constituent entities.
 */
public abstract class CompoundEntity extends AbstractEntity {
    
    private List<Entity> constituents = new ArrayList<>();
    
    /**
     * A cache of the constituent shapes.
     * When it's not null, shapes() will assume that the cache is up to date and
     * promptly return it. So when constituents are added or removed,
     * it should be set to null to indicate that shapes must be consolidated
     */
    private List<Rectangle2D> shapeCache = new ArrayList<>();
    
    /**
     * Adds a constituent Entity
     * @param e the Entity to add
     */
    public void addEntity(Entity e) {
        e.setGroupId(this.getGroupId());
        constituents.add(e);
        shapeCache = null;
    }
    
    /**
     * Removes a constituent Entity
     * @param e the entity to remove
     */
    public void removeEntity(Entity e) {
        e.setGroupId(e);
        constituents.remove(e);
        shapeCache = null;
    }
    
    @Override
    public Collection<? extends Rectangle2D> shapes() {
        if (shapeCache != null) return shapeCache;
        shapeCache = new ArrayList<>();
        for (Entity e : constituents) {
            for (Rectangle2D s : e.shapes()) {
                shapeCache.add(s);
            }
        }
        return shapeCache;
    }
    
    @Override
    public boolean touching(Spatial s) {
        for (Entity e : constituents) {
            if (e.touching(s)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void move(double dx, double dy) {
        super.move(dx, dy);
        for (Entity e : constituents) {
            e.move(dx, dy);
        }
    }
    
    @Override
    public Collection<? extends VisualElement> visualElements() {
        List<VisualElement> visualElements = new ArrayList<>();
        for (Entity e : constituents) {
            for (VisualElement v : e.visualElements()) {
                visualElements.add(v);
            }
        }
        return visualElements;
    }
    
    @Override
    public Collection<? extends Entity>
    manageCollisionsAndGetContacts(Collection<? extends Entity> context) {
        List<Entity> contacts = new ArrayList<>();
        for (Entity constituent : constituents) {
            for (Entity contact : constituent.manageCollisionsAndGetContacts(context)) {
                contacts.add(contact);
                collideWith(contact);
            }
        }
        return contacts;
    }

    @Override
    public Collection<? extends Spatial> tangibles() {
        // TODO refactor with predicate & live collection view?
        List<Spatial> tangibles = new ArrayList<>();
        for (Entity c : constituents) {
            for (Spatial t : c.tangibles()) {
                tangibles.add(t);
            }
        }
        return tangibles;
    }

}