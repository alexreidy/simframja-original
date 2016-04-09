package simframja;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

public class Rectangle extends ShapeEntity {
    
    public Rectangle(double x, double y, double width, double height, Color color) {
        addShape(new Rectangle2D.Double(x, y, width, height));
        setColor(color);
    }

}