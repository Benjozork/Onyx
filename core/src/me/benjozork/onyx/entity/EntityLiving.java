package me.benjozork.onyx.entity;

import com.badlogic.gdx.Gdx;
import me.benjozork.onyx.internal.GameConfiguration;
import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.internal.GameUtils;
import me.benjozork.onyx.object.Location;

/**
 * Created by Benjozork on 2017-03-04.
 */
public abstract class EntityLiving extends Entity {

    private float health = GameConfiguration.DEFAULT_HEALTH;
    private int ammo = GameConfiguration.DEFAULT_AMMO;

    public EntityLiving(int x, int y) {
        super(x, y);
    }

    public EntityLiving(Location loc) {
        super(loc);
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public void damage(float v)  {
        if (health <= 0) {
            this.dispose();
            return;
        }
        health -=v;
    }

    public void fireProjectile(String path, int vx, int vy, float damage) {
        EntityProjectile projectile = new EntityProjectile(GameUtils.getCenterPos(13), loc.getY());
        projectile.setTexturePath(path);
        projectile.setVelocity(vx, vy);
        projectile.setDamage(damage);
        GameManager.registerEntity(projectile);
        if (ammo < 0) return;
        ammo -= 1;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }
}
