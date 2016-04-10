package simframja;

/**
 * Represents something, such as an entity, that can be considered
 * definitively tangible or not. Things that are tangible are considered in
 * methods like touching(), which ignore intangible objects.
 * 
 * CompoundEntity constituent trees will typically "bottom out" with
 * PotentialTangible entities as leaves. This makes it possible for touching()
 * to work with compound entities that are only partially tangible.
 * Entities that implement PotentiallyTangible may be compound,
 * but this means their shapes will be considered a single tangible mass.
 * If a compound entity could have "phantom" constituents, it should not
 * be a PotentialTangible.
 */
public interface PotentialTangible {
    
    /**
     * Returns true if the object is tangible.
     */
    boolean isTangible();

}