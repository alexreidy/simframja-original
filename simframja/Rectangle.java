package simframja;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

/**
 * A rectangular ShapeEntity.
 */
public class Rectangle extends ShapeEntity {
    /**
     * Constructs a Rectangle.
     * @param x the initial x coordinate
     * @param y the initial y coordinate
     * @param width the rectangle's width
     * @param height the rectangle's height
     * @param color the rectangle's color
     */
    public Rectangle(double x, double y, double width, double height, Color color) {
        setPosition(x, y);
        addShape(new Rectangle2D.Double(x, y, width, height));
        setColor(color);
    }

}