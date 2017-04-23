package me.benjozork.onyx.game.weapon;

import com.badlogic.gdx.utils.Array;

import me.benjozork.onyx.game.entity.LivingEntity;
import me.benjozork.onyx.game.weapon.projectile.Projectile;
import me.benjozork.onyx.utils.Utils;

/**
 * @author Benjozork
 */
public abstract class ProjectileWeapon<T extends Projectile> extends Weapon {

    private Array<T> launchedProjectiles = new Array<T>();

    public ProjectileWeapon(LivingEntity parent) {
        super(parent);
    }

    public void update() {
        for (Projectile projectile : launchedProjectiles) {
            projectile.update(Utils.delta());
            projectile.update();
        }

        for (Projectile projectile : launchedProjectiles) {
            projectile.draw();
        }
    }

    public void disposeProjectile(Projectile projectile) {
        launchedProjectiles.removeValue((T) projectile, true);
    }

    public Array<T> getLaunchedProjectiles() {
        return launchedProjectiles;
    }

}
