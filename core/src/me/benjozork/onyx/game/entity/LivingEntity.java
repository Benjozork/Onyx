package me.benjozork.onyx.game.entity;

/**
 * @author Benjozork
 */
public abstract class LivingEntity extends Entity {

    private float health, maxHealth;

    public LivingEntity(float x, float y) {
        super(x, y);
    }

    public void damage(float v) {
        this.health -= v;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

}