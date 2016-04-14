package simframja;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A concrete implementation of Entity comprised of Rectangle2D shapes.
 * Implements VisualElement, and can thus be seen as a potentially complex
 * but unified shape of a single color. May or may not be tangible.
 */
public class ShapeEntity extends AbstractEntity implements VisualElement {
    
    private final List<ShapeEntity> visualElementList = new ArrayList<>(1);

    private List<Rectangle2D> shapes = new ArrayList<>();
    
    private Color color;
    
    boolean tangible = true;
    
    /**
     * Constructs a ShapeEntity with the given shapes and color.
     */
    public ShapeEntity(List<Rectangle2D> shapes, Color color) {
        this.shapes = shapes; this.color = color;
        visualElementList.add(this);
    }
    
    /**
     * Constructs a ShapeEntity with the given shape and color.
     */
    public ShapeEntity(Rectangle2D shape, Color color) {
        shapes.add(shape); this.color = color;
        visualElementList.add(this);
    }
    
    /**
     * Constructs a ShapeEntity without any initial shapes
     * and without an initial color.
     */
    public ShapeEntity() {
        visualElementList.add(this);
    }
    
    /**
     * Adds the given shape to the ShapeEntity.
     */
    public void addShape(Rectangle2D s) {
        shapes.add(s);
    }
    
    /**
     * Sets the constituent shapes to the given List.
     */
    public void setShapes(List<Rectangle2D> shapes) {
        this.shapes = shapes;
    }
    
    @Override
    public Collection<? extends Rectangle2D> shapes() {
        return shapes;
    }
    
    @Override
    public void setColor(Color c) {
        color = c;
    }
    
    @Override
    public Color color() {
        return color;
    }
    
    @Override
    public Collection<? extends VisualElement> visualElements() {
        return visualElementList;
    }
    
    @Override
    public void move(double dx, double dy) {
        super.move(dx, dy);
        for (Rectangle2D shape : shapes()) {
            shape.setFrame(
                shape.getX() + dx,
                shape.getY() + dy,
                shape.getWidth(),
                shape.getHeight());
        }
    }
    
    public void setTangible(boolean b) {
        tangible = b;
    }
    
    public boolean isTangible() {
        return tangible;
    }

    @Override
    public Collection<? extends Spatial> tangibles() {
        if (this.isTangible()) return visualElementList;
        return Collections.emptyList();
    }

}