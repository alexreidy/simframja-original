package simframja;

/**
 * A 2-double vector
 */
public class Vector2 implements Mobile {
    
    /** The x component */
    public double x;
    
    /** The y component */
    public double y;
    
    private static Vector2 recycledCopy = new Vector2();
    
    /**
     * Constructs a Vector2 with the given components.
     */
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Constructs a Vector2, copying the components of the given Vector2.
     */
    public Vector2(Vector2 v) {
        this(v.x, v.y);
    }
    
    /**
     * Constructs a Vector2 with (x=0,y=0)
     */
    public Vector2() {
        this(0, 0);
    }
    
    /**
     * Returns the norm of this vector.
     */
    public Vector2 norm() {
        double h = Math.hypot(x, y);
        return new Vector2(x/h, y/h);
    }
    
    /**
     * Adds the given components to the respective components of this vector.
     * @return this vector (for convenience)
     */
    public Vector2 add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }
    
    /**
     * Adds the given vector to this vector.
     * @return this vector (for convenience)
     */
    public Vector2 add(Vector2 v) {
        add(v.x, v.y);
        return this;
    }
    
    /**
     * Multiplies this vector by the given scalar.
     * @return this vector (for convenience)
     */
    public Vector2 multiply(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }
    
    /**
     * An alias for vector addition.
     * @see add(double x, double y)
     */
    @Override
    public void move(double dx, double dy) {
        add(dx, dy);
    }
    
    /**
     * An alias for vector addition.
     * @see add(Vector2 v)
     */
    @Override
    public void move(Vector2 offset) {
        add(offset);
    }
    
    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof Vector2)) return false;
        Vector2 otherVector = (Vector2) other;
        return otherVector.x == x && otherVector.y == y;
    }
    
    /**
     * Copies the vector by returning a new one with identical components.
     */
    public Vector2 copy() {
        return new Vector2(this);
    }
    
    /**
     * Sets the vector's components to those of the given vector.
     */
    public void setValuesTo(Vector2 v) {
        x = v.x;
        y = v.y;
    }
    
    /**
     * Returns a shared static Vector2 with identical components.
     * Emphatically not thread safe.
     */
    public Vector2 recycledCopy() {
        recycledCopy.x = x;
        recycledCopy.y = y;
        return recycledCopy;
    }
    
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
    
}