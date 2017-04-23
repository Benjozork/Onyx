package me.benjozork.onyx.game.weapon.projectile;

import com.badlogic.gdx.graphics.g2d.Sprite;

import me.benjozork.onyx.game.entity.LivingEntity;
import me.benjozork.onyx.game.weapon.ProjectileWeapon;
import me.benjozork.onyx.object.Drawable;

/**
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

    public T getParent() {
        return parent;
    }

    public LivingEntity getParentEntity() {
        return entity;
    }

}
