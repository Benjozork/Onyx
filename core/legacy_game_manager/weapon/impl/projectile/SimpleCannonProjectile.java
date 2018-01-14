package me.benjozork.onyx.game.weapon.impl.projectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.GameManager;
import me.benjozork.onyx.game.GameScreenManager;
import me.benjozork.onyx.game.entity.EnemyEntity;
import me.benjozork.onyx.game.entity.ProjectileManager;
import me.benjozork.onyx.game.weapon.impl.SimpleCannon;
import me.benjozork.onyx.game.weapon.projectile.Projectile;
import me.benjozork.onyx.utils.PolygonHelper;

/**
 * Implementation of a {@link Projectile} fired by a {@link SimpleCannon}.
 * @author Benjozork
 */
public class SimpleCannonProjectile extends Projectile<SimpleCannon> {

    public SimpleCannonProjectile(SimpleCannon parent, float targetx, float targety) {
        super(parent);
        sprite = new Sprite(new Texture("entity/" + getParentEntity().type.name().toLowerCase() + "/bullet.png"));
        sprite.setSize(10, 10);

        Vector2 target = new Vector2(targetx, targety);

        bounds = PolygonHelper.getPolygon(getX(), getY(), sprite.getWidth(), sprite.getHeight());

        // Set velocity accordingly to target

        velocity.set(target.cpy().sub(getX(), getY()));

        velocity.nor().scl(900f);

        // Register in ProjectileManager

        ProjectileManager.addProjectile(this);
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {
        sprite.setPosition(getX(),  getY());
        bounds.setPosition(getX(), getY());

        if (getX() < - Gdx.graphics.getWidth()  || getX() > Gdx.graphics.getWidth() * 2 || getY() < - Gdx.graphics.getHeight() || getY() > Gdx.graphics.getHeight() * 2) {
            dispose();
        }

        if (GameScreenManager.getEnemies().size == 0) return;

        for (EnemyEntity enemy : GameScreenManager.getEnemies()) {
            if (this.collidesWith(enemy.getBounds()) && enemy.type != getParentEntity().type) {
                enemy.damage(10f, getParentEntity());
                this.dispose();
            }
        }

        PlayerEntity player = GameScreenManager.getLocalPlayerEntity();
        if (this.collidesWith(player.getBounds()) && player.type != getParentEntity().type) {
            player.damage(10f, getParentEntity());
            this.dispose();
        }
    }

    @Override
    public void draw() {
        sprite.draw(GameManager.getBatch());
    }

    @Override
    public void dispose() {
        sprite.getTexture().dispose();
        getParent().disposeProjectile(this);
    }
}
