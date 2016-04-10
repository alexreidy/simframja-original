package simframja;

/**
 * An interface describing something that can move.
 */
public interface Mobile {
    
    void move(double dx, double dy);
    
    void move(Vector2 offset);

}