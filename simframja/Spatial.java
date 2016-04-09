package simframja;

import java.util.Collection;
import java.awt.geom.Rectangle2D;

public interface Spatial {
    
    void setPosition(double x, double y);
    
    void setPosition(Vector2 position);
    
    Vector2 getPosition();
    
    Collection<? extends Rectangle2D> shapes();
    
    Collection<? extends Spatial> tangibles();
    
    boolean touching(Spatial s); // Only considers tangibles
    
    
    // Returns true if any shapes in this and given Spatial intersect
    boolean intersects(Spatial s);
    
    boolean contains(double x, double y);
    
    boolean contains(Vector2 point);
    
    public Rectangle2D getBoundingBox();

}