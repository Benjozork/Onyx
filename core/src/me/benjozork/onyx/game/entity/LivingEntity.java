package me.benjozork.onyx.game.entity;

import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.event.EventManager;
import me.benjozork.onyx.event.impl.EntityKilledEvent;
import me.benjozork.onyx.game.GameScreenManager;
import me.benjozork.onyx.object.Textured;
import me.benjozork.onyx.utils.Utils;

/**
 * @author Benjozork
 */
public abstract class LivingEntity extends Entity implements Textured {

    private float rotation;

    private float health = 100f;

    private boolean dead = false;

    private final float MAX_BULLET_TIME = 0.1f;
    private float bulletTimer = 0f;

    private final Vector2 bulletShootOrigin = new Vector2();
    private final Vector2 bulletImpactTarget = new Vector2();

    public Type type;

    public LivingEntity(float x, float y) {
        super(x, y);
    }

    public void fireProjectileAt(String path, float targetx, float targety) {
        bulletTimer += Utils.delta();
        if (bulletTimer >= MAX_BULLET_TIME || ! (this instanceof PlayerEntity)) {
            ProjectileEntity projectile = new ProjectileEntity(getX() + bulletShootOrigin.x, getY() + bulletShootOrigin.y, targetx, targety, path);
            projectile.getVelocity().scl(2550f);
            projectile.setDamage(10f);
            projectile.source = this;

            GameScreenManager.addEntity(projectile);

            //if (ammo < 0) return;
            //ammo -= 1;

            bulletTimer = 0f;
        }
    }

    public void damage(float v, LivingEntity damageSource) {
        health -= v;
        if (health < 0 && ! dead) {
            dead = true;
            EntityKilledEvent deathEvent = new EntityKilledEvent();
            deathEvent.killer = damageSource;
            deathEvent.entity = this;
            EventManager.pushEvent(deathEvent);
            GameScreenManager.die(this);
        }
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public Vector2 getBulletShootOrigin() {
        return new Vector2(getX() + bulletShootOrigin.x, getY() + bulletShootOrigin.y);
    }

    public void setBulletShootOrigin(float x, float y) {
        bulletShootOrigin.set(x, y);
    }

    public Vector2 getBulletImpactTarget() {
        return new Vector2(getX() + bulletImpactTarget.x, getY() + bulletImpactTarget.y);
    }

    public void setBulletImpactTarget(float x, float y) {
        this.bulletImpactTarget.set(x,  y);
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public abstract float getTextureWidth();

    public abstract float getTextureHeight();

    public enum Type {
        ENEMY,
        PLAYER
    }
}