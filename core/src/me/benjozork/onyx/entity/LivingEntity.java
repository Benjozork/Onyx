package me.benjozork.onyx.entity;

import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.internal.ScreenManager;
import me.benjozork.onyx.screen.GameScreenManager;
import me.benjozork.onyx.utils.Utils;
import me.benjozork.onyx.screen.GameScreen;

/**
 * @author Benjozork
 */
public abstract class LivingEntity extends Entity {

    protected float health = 100f;

    private float maxTime = 0.1f;
    private float timer = 0f;

    public LivingEntity(int x, int y) {
        super(new Vector2(x, y));
    }

    public LivingEntity(Vector2 pos) {
        super(pos);
    }

    public void fireProjectile(String path) {
        timer += Utils.delta();
        if (timer >= maxTime) {
            EntityProjectile projectile = new EntityProjectile(getX(), getY());
            projectile.setTexturePath(path);
            projectile.setSpeed(2550f);
            projectile.setDamage(10f);
            GameScreenManager.registerEntity(projectile);

            //if (ammo < 0) return;
            //ammo -= 1;

            timer = 0f;
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
}