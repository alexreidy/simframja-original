package simframja;

public interface PropertyExposer {
    
    Object get(Property key);
    
    void set(Property key, Object value);

}