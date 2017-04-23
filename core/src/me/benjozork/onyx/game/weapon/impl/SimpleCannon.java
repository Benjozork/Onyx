package me.benjozork.onyx.game.weapon.impl;

import me.benjozork.onyx.game.entity.LivingEntity;
import me.benjozork.onyx.game.weapon.ProjectileWeapon;
import me.benjozork.onyx.game.weapon.impl.projectile.SimpleCannonProjectile;

/**
 * @author Benjozork
 */
public class SimpleCannon extends ProjectileWeapon<SimpleCannonProjectile> {

    public SimpleCannon(LivingEntity parent) {
        super(parent);
    }

    @Override
    public void fire(float x, float y) {
        getLaunchedProjectiles().add(new SimpleCannonProjectile(this, x, y));
    }

}
