package simframja;

/**
 * CompoundEntity constituent trees will typically "bottom out" with
 * PotentialTangible entities as leaves. This makes it possible for touching()
 * to work with compound entities that are only partially tangible.
 * Entities that implement PotentiallyTangible may be compound,
 * but this means their shapes will be considered a single tangible mass.
 * If a compound entity could have "phantom" constituents, it should not
 * be a PotentialTangible.
 */
public interface PotentialTangible {
    
    boolean isTangible();

}