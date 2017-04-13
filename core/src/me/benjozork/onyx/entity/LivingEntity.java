package me.benjozork.onyx.entity;

import me.benjozork.onyx.screen.GameScreenManager;
import me.benjozork.onyx.utils.Utils;

/**
 * @author Benjozork
 */
public abstract class LivingEntity extends Entity {

    private float health = 100f;

    private final float maxBulletTime = 0.1f;
    private float bulletTimer = 0f;

    public Type type;

    public LivingEntity(float x, float y) {
        super(x, y);
    }

    public void fireProjectile(String path) {
        bulletTimer += Utils.delta();
        if (bulletTimer >= maxBulletTime) {
            ProjectileEntity projectile = new ProjectileEntity(getX(),getY());
            projectile.accelerate(2550f);
            projectile.setDamage(10f);
            projectile.source = type;

            GameScreenManager.registerEntity(projectile);

            //if (ammo < 0) return;
            //ammo -= 1;

            bulletTimer = 0f;
        }
    }

    public void damage(float v) {
        health -= v;
        if (health < 0) this.dispose();
    }

    /*
    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }
    */
    public enum Type{
        ENEMY,
        PLAYER
    }
}