package me.benjozork.onyx.entity;

import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.game.GameScreenManager;
import me.benjozork.onyx.utils.Utils;

/**
 * @author Benjozork
 */
public abstract class LivingEntity extends Entity {

    private float health = 100f;

    private final float maxBulletTime = 0.1f;
    private float bulletTimer = 0f;
    private Vector2 bulletShootOrigin = new Vector2();

    public Type type;

    public LivingEntity(float x, float y) {
        super(x, y);
    }

    public void fireProjectile(String path) {
        bulletTimer += Utils.delta();
        if (bulletTimer >= maxBulletTime) {
            ProjectileEntity projectile = new ProjectileEntity(getX() + bulletShootOrigin.x, getY() + bulletShootOrigin.y);
            projectile.accelerate(2550f);
            projectile.setDamage(10f);
            projectile.source = type;

            GameScreenManager.addEntity(projectile);

            //if (ammo < 0) return;
            //ammo -= 1;

            bulletTimer = 0f;
        }
    }

    public void damage(float v) {
        health -= v;
        if (health < 0) {
            if (this instanceof EnemyEntity) GameScreenManager.addScore(100);
            this.dispose();
        }
    }

    public Vector2 getBulletShootOrigin() {
        return bulletShootOrigin;
    }

    public void setBulletShootOrigin(float x, float y) {
        bulletShootOrigin.set(x, y);
    }

    /*
    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }
    */

    public enum Type {
        ENEMY,
        PLAYER
    }
}