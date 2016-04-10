package simframja;

import java.util.Collection;

/**
 * A Visual is an object comprised of VisualElements
 * (or at least one that provides VisualElements).
 * Can be rendered by simframja.Canvas.
 */
public interface Visual {
    
    /**
     * Returns the VisualElements comprising this Visual.
     */
    Collection<? extends VisualElement> visualElements();

}