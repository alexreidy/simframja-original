package simframja;

public enum Property {
    
    HEALTH;
    
    static Object[] makePropertyArray() {
        return new Object[Property.values().length];
    }

}
