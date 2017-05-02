package me.benjozork.onyx.game.weapon;

import me.benjozork.onyx.game.entity.LivingEntity;

/**
 * Describes a weapon that can be fired by a {@link LivingEntity}.
 * @author Benjozork
 */
public abstract class Weapon {

    private LivingEntity parent;

    public Weapon(LivingEntity parent) {
        this.parent = parent;
    }

    public abstract void update();

    /**
     * Fires the weapon at the specified coordinates
     * @param targetx the x coordinate of the target
     * @param targety the y coordinate of the target
     */
    public abstract void fire(float targetx, float targety);

    /**
     * @return the {@link LivingEntity} which possesses this weapon
     */
    public LivingEntity getParent() {
        return parent;
    }

}
