package me.benjozork.onyx.game.weapon;

import com.badlogic.gdx.utils.Array;

import me.benjozork.onyx.game.weapon.projectile.Projectile;
import me.benjozork.onyx.utils.Utils;

/**
 * Describes a {@link Weapon} that fires single projectiles.
 *
 * @param <T> the {@link Projectile} that this weapon can fire
 *
 * @author Benjozork
 */
public abstract class ProjectileWeapon<T extends Projectile> extends Weapon {

    private Array<T> launchedProjectiles = new Array<T>();

    public ProjectileWeapon(LivingEntity parent) {
        super(parent);
    }

    /**
     * Updates the {@link Projectile} objects that have been fired by the weapon, and are currently valid
     */
    public void update() {
        for (Projectile projectile : launchedProjectiles) {
            projectile.update(Utils.delta());
            projectile.update();
        }

        for (Projectile projectile : launchedProjectiles) {
            projectile.draw();
        }
    }

    /**
     * Disposes of a {@link Projectile}
     * @param projectile the {@link Projectile} to be disposed of
     */
    public void disposeProjectile(Projectile projectile) {
        launchedProjectiles.removeValue((T) projectile, true);
    }

    /**
     * @return an {@link Array} of projectiles that have been launched by the weapon, and are currently valid
     */
    public Array<T> getLaunchedProjectiles() {
        return launchedProjectiles;
    }

}
