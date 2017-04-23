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

    private float health = 100f;

    private boolean dead = false;

    private final float MAX_BULLET_TIME = 0.1f;
    private float bulletTimer = 0f;

    private final Vector2 bulletShootOrigin = new Vector2();
    private final Vector2 bulletImpactTarget = new Vector2();

    public Type type;

    private Array<Weapon> weapons = new Array<Weapon>();

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