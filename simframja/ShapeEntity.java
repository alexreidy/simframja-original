package simframja;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ShapeEntity extends AbstractEntity implements VisualElement, PotentialTangible {
    
    private final List<ShapeEntity> visualElementList = new ArrayList<>(1);

    private List<Rectangle2D> shapes = new ArrayList<>();
    
    private Color color;
    
    boolean tangible = true;
    
    Entity groupId;
    
    public ShapeEntity(List<Rectangle2D> shapes, Color color) {
        this.shapes = shapes; this.color = color;
        visualElementList.add(this);
    }
    
    public ShapeEntity(Rectangle2D shape, Color color) {
        shapes.add(shape); this.color = color;
        visualElementList.add(this);
    }
    
    public ShapeEntity() {
        visualElementList.add(this);
    }
    
    public void addShape(Rectangle2D s) {
        shapes.add(s);
    }
    
    public void setShapes(List<Rectangle2D> shapes) {
        this.shapes = shapes;
    }
    
    @Override
    public Collection<? extends Rectangle2D> shapes() {
        return shapes;
    }
    
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

    @Override
    public boolean isTangible() {
        return tangible;
    }

    @Override
    public Collection<? extends Spatial> tangibles() {
        if (this.isTangible()) return visualElementList;
        return Collections.emptyList();
    }

    @Override
    public void setGroupId(Entity id) {
        groupId = id;
    }

    @Override
    public Entity getGroupId() {
        return groupId;
    }

}