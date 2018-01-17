package me.benjozork.onyx.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.backend.handlers.RessourceHandler;
import me.benjozork.onyx.utils.PolygonHelper;

/**
 * An entity for a basic projectile
 *
 * @author Benjozork
 */
public class ProjectileEntity extends Entity {

    private final float MAX_SPEED = 800;


    private Sprite projectileSprite;

    private Vector2 target;

    /**
     * @param x the initial x position of the projectile
     * @param y the initial y position of the projectile
     * @param targetX the x position of the target
     * @param targetY the y position of the target
     * @param initialSpeed the initial speed, aka. the initial length of the velocity vector
     */
    public ProjectileEntity(float x, float y, float targetX, float targetY, float initialSpeed) {
        super(x, y);

        this.projectileSprite = new Sprite(new Texture("entity/player/bullet.png"));

        this.width = 15;
        this.height = 15;

        this.projectileSprite.setSize(width, height);

        this.setBounds(PolygonHelper.getPolygon(this.getX(), this.getY(), this.width, this.height));

        this.setMaxSpeed(MAX_SPEED);

        // Set target

        this.target = new Vector2(targetX, targetY);

        // Set velocity vector according to target

        this.velocity.set(this.target.sub(this.position));
        this.velocity.setLength(initialSpeed);
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

        // Update sprite position to entity position

        this.projectileSprite.setPosition(this.getX() - width / 2, this.getY() - width / 2);

    }

    @Override
    public void draw() {
        projectileSprite.draw(RessourceHandler.getBatch());
    }

    @Override
    public void dispose() {
        projectileSprite.getTexture().dispose();
    }

}