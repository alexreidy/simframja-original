package simframja;

/**
 * A utility class for simframja.
 */
public class Util {
    
    /**
     * Not to be instantiated.
     */
    private Util() {}
    
    public static final Vector2
        UP    = new Vector2(0, -1),
        DOWN  = new Vector2(0, 1),
        LEFT  = new Vector2(-1, 0),
        RIGHT = new Vector2(1, 0);
    
    /**
     * Returns a random double on the interval [0,1].
     */
    public static double rn() {
        return Math.random();
    }
    
    /**
     * Returns a random double on the interval [0,range].
     */
    public static double rin(double range) {
        return rn() * range;
    }
    
    /**
     * Returns x with a random sign.
     */
    public static double rsign(double x) {
        if (rn() > 0.5) return x;
        return -x;
    }
    
}