package me.benjozork.onyx.game.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import me.benjozork.onyx.event.EventManager;
import me.benjozork.onyx.event.impl.EntityKilledEvent;
import me.benjozork.onyx.game.GameScreenManager;
import me.benjozork.onyx.game.weapon.Weapon;
import me.benjozork.onyx.object.Textured;

/**
 * @author Benjozork
 */
public abstract class LivingEntity extends Entity implements Textured {

    private float rotation;

    protected float health = 100f;

    protected boolean dead = false;

    private final float MAX_BULLET_TIME = 0.1f;
    private float bulletTimer = 0f;

    private final Vector2 bulletShootOrigin = new Vector2();
    private final Vector2 bulletImpactTarget = new Vector2();

    public Type type;

    private Array<Weapon> weapons = new Array<Weapon>();

    private boolean isFiring = false;

    // Cached instances for shootOrigin/impactTarget

    private Vector2 vector2 = new Vector2();
    private Vector2 vector21 = new Vector2();

    public LivingEntity(float x, float y) {
        super(x, y);
    }


    public Array<Weapon> getWeapons() {
        return weapons;
    }

    public void addWeapon(Weapon weapon) {
        weapons.add(weapon);
    }

    public void fireWeapon(Class<? extends Weapon> clazz, float targetx, float targety) {
        for (Array.ArrayIterator<Weapon> iter = new Array.ArrayIterator<Weapon>(weapons); iter.hasNext();) {
            Weapon weapon = iter.next();
            if (weapon.getClass() == clazz) weapon.fire(targetx, targety);
        }
    }

    @Override
    public void update() {
        for (Weapon weapon : weapons) {
            weapon.update();
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
        vector2.set(getX() + bulletShootOrigin.x, getY() + bulletShootOrigin.y);
        return vector2;
    }

    public void setBulletShootOrigin(float x, float y) {
        bulletShootOrigin.set(x, y);
    }

    public Vector2 getBulletImpactTarget() {
        vector21.set(getX() + bulletImpactTarget.x, getY() + bulletImpactTarget.y);
        return vector21;
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

    public boolean isFiring() {
        return isFiring;
    }

    public void setIsFiring(boolean firing) {
        isFiring = firing;
    }

    public enum Type {
        ENEMY,
        PLAYER
    }
}