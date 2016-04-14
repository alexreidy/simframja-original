package simframja;

import java.awt.Color;

/**
 * VisualElements can be thought of as uniformly colored shapes.
 *
 * Implementations should typically return a single-element
 * collection containing "this" from visualElements()
 * so they will be useful when treated as Visuals.
 */
public interface VisualElement extends Visual, Spatial {
    
    void setColor(Color c);
    
    Color color();
    
}