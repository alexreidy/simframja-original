package simframja;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.Collections;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

public abstract class AbstractEntity implements Entity {
    
    private Vector2 position = new Vector2();
    
    private double health = 100;
    
    private boolean lifespanSet = false;
    
    private int lifespanUpdates = 0, updateCount = 0;
    
    Entity groupId = this;
        
    @Override
    public void setPosition(double x, double y) {
        move(x - position.x, y - position.y);
    }
    
    @Override
    public void setPosition(Vector2 position) {
        setPosition(position.x, position.y);
    }
    
    @Override
    public Vector2 getPosition() {
        return position.recycledCopy();
    }
    
    @Override
    public boolean contains(double x, double y) {
        for (Shape s : shapes()) {
            if (s.contains(x, y))
                return true;
        }
        return false;
    }
    
    @Override
    public boolean contains(Vector2 point) {
        return contains(point.x, point.y);
    }
    
    @Override
    public boolean intersects(Spatial other) {
        for (Rectangle2D shape : shapes()) {
            for (Rectangle2D otherShape : other.shapes()) {
                if (shape.intersects(otherShape)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean touching(Spatial other) {
        for (Spatial tangible : tangibles()) {
            for (Spatial otherTangible : other.tangibles()) {
                if (tangible.intersects(otherTangible)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public void move(double dx, double dy) {
        position.x += dx;
        position.y += dy;
    }
    
    @Override
    public void move(Vector2 offset) {
        move(offset.x, offset.y);
    }
    
    @Override
    public Collection<? extends VisualElement> visualElements() {
        return Collections.emptyList();
    }
    
    public void collideWith(Entity e) {}
    
    public Collection<? extends Entity>
    manageCollisionsAndGetContacts(Collection<? extends Entity> context) {
        List<Entity> contacts = new ArrayList<>();
        for (Entity e : context) {
            if (e.getGroupId() != this.getGroupId() && touching(e)) {
                contacts.add(e);
                collideWith(e);
            }
        }
        return contacts;
    }
    
    Rectangle2D box = new Rectangle2D.Double();
    @Override
    public Rectangle2D getBoundingBox() {
        double xMin = Double.MAX_VALUE, yMin = xMin,
               xMax = Double.MIN_VALUE, yMax = xMax;
        
        for (Rectangle2D s : shapes()) {
            Rectangle2D sbox = s.getBounds2D();
            if (sbox.getMinX() < xMin) xMin = sbox.getMinX();
            if (sbox.getMinY() < yMin) yMin = sbox.getMinY();
            if (sbox.getMaxX() > xMax) xMax = sbox.getMaxX();
            if (sbox.getMaxY() > yMax) yMax = sbox.getMaxY();
        }
        
        box.setFrame(xMin, yMin, xMax-xMin, yMax-yMin);
        
        return box;
    }
    
    @Override
    public double getHealth() {
        return health;
    }
    
    @Override
    public void setHealth(double h) {
        health = h;
    }
    
    @Override
    public void changeHealth(double change) {
        health += change;
    }
    
    @Override
    public void die() {
        health = 0;
    }
    
    @Override
    public boolean isAlive() {
        return health > 0;
    }
    
    @Override
    public final void setLifespan(int updates) {
        lifespanUpdates = updates;
        lifespanSet = true;
    }
    
    @Override
    public void update(Collection<? extends Entity> context) {
        if (lifespanSet && updateCount++ >= lifespanUpdates) {
            die();
        }
    }
    
    @Override
    public Collection<? extends Entity> products() {
        return Collections.emptyList();
    }
    
    @Override
    public Entity getGroupId() {
        return groupId;
    }
    
    @Override
    public void setGroupId(Entity id) {
        groupId = id;
    }
    
}