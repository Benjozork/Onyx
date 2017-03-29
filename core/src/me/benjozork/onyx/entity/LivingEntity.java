package me.benjozork.onyx.entity;

import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.internal.GameConfiguration;
import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.utils.Utils;
import me.benjozork.onyx.screen.GameScreen;

/**
 * Created by Benjozork on 2017-03-04.
 */
public abstract class LivingEntity extends Entity {

    private float health = GameConfiguration.DEFAULT_HEALTH;
    private int ammo = GameConfiguration.DEFAULT_AMMO;

    private float maxTime = 0.1f;
    private float timer = 0f;

    public LivingEntity(int x, int y) {
        super(new Vector2(x, y));
    }

    public LivingEntity(Vector2 pos) {
        super(pos);
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public void damage(float v) {
        if (health <= 0) {
            this.dispose();
            return;
        }
        health -= v;
    }

    public void fireProjectile(String path) {
        timer += Utils.delta();
        if (timer >= maxTime) {
            EntityProjectile projectile = new EntityProjectile(getX(), getY());
            projectile.setTexturePath(path);
            projectile.setSpeed(1550f);
            ((GameScreen) GameManager.getCurrentScreen()).registerEntity(projectile);

            //if (ammo < 0) return;
            //ammo -= 1;

            timer = 0f;
        }
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }
}