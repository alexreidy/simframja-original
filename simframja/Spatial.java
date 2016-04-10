package simframja;

import java.util.Collection;
import java.awt.geom.Rectangle2D;

/**
 * Spatials can have shapes and positions.
 */
public interface Spatial {
    
    void setPosition(double x, double y);
    
    void setPosition(Vector2 position);
    
    Vector2 getPosition();
    
    /**
     * Returns the raw Rectangle2D shapes that constitute this Spatial.
     */
    Collection<? extends Rectangle2D> shapes();
    
    /**
     * Returns any Spatials comprising this Spatial that are declared tangible.
     * @see simframja.PotentialTangible
     */
    Collection<? extends Spatial> tangibles();
    
    /**
     * Returns true if touching the given Spatial.
     */
    boolean touching(Spatial s);
    
    /**
     * Returns true if any shapes in this and given Spatial intersect.
     */
    boolean intersects(Spatial s);
    
    /**
     * Returns true if the given points are inside this Spatial.
     */
    boolean contains(double x, double y);
    
    /**
     * Returns true if the given point is inside this Spatial.
     */
    boolean contains(Vector2 point);
    
    /**
     * Returns a bounding box, with dimensions that
     * fit all constituent shapes inside.
     */
    public Rectangle2D getBoundingBox();

}