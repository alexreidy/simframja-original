package simframja;

public class Vector2 implements Mobile {
    
    public double x, y;
    
    private static Vector2 recycledCopy = new Vector2();
    
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public Vector2(Vector2 v) {
        this(v.x, v.y);
    }
    
    public Vector2() {
        this(0, 0);
    }
    
    public Vector2 norm() {
        double h = Math.hypot(x, y);
        return new Vector2(x/h, y/h);
    }
    
    public Vector2 add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }
    
    public Vector2 add(Vector2 v) {
        add(v.x, v.y);
        return this;
    }
    
    public Vector2 multiply(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }
    
    @Override
    public void move(double dx, double dy) {
        add(dx, dy);
    }
    
    @Override
    public void move(Vector2 offset) {
        add(offset);
    }
    
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Vector2)) return false;
        if (other == this) return true;
        Vector2 otherVector = (Vector2) other;
        return otherVector.x == x && otherVector.y == y;
    }
    
    public Vector2 copy() {
        return new Vector2(x, y);
    }
    
    public void setValuesTo(Vector2 v) {
        x = v.x;
        y = v.y;
    }
    
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