package me.benjozork.onyx.game.weapon.projectile;

import com.badlogic.gdx.graphics.g2d.Sprite;

import me.benjozork.onyx.game.weapon.ProjectileWeapon;
import me.benjozork.onyx.object.Drawable;

/**
 * Describes a projectile fired by a {@link ProjectileWeapon}.
 *
 * @param <T> the {@link ProjectileWeapon} that can fire this projectile
 *
 * @author Benjozork
 */
public abstract class Projectile<T extends ProjectileWeapon> extends Drawable {

    private T parent;

    private LivingEntity entity;

    protected Sprite sprite;

    public Projectile(T parent) {
        super(parent.getParent().getBulletShootOrigin().x, parent.getParent().getBulletShootOrigin().y);
        this.parent = parent;
        this.entity = parent.getParent();
    }

    public abstract void init();

    public abstract void update();

    public abstract void draw();

    public abstract void dispose();

    /**
     * @return the {@link ProjectileWeapon} that fired this projectile
     */
    public T getParent() {
        return parent;
    }

    /**
     * @return the {@link LivingEntity} which possesses the {@link ProjectileWeapon} that fired this projectile
     */
    public LivingEntity getParentEntity() {
        return entity;
    }

}
