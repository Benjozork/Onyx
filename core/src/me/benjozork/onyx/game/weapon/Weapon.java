package me.benjozork.onyx.game.weapon;

import me.benjozork.onyx.game.entity.LivingEntity;

/**
 * @author Benjozork
 */
public abstract class Weapon {

    private LivingEntity parent;

    public Weapon(LivingEntity parent) {
        this.parent = parent;
    }

    public abstract void update();

    public abstract void fire(float targetx, float targety);

    public LivingEntity getParent() {
        return parent;
    }

}
