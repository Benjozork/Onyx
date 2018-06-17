package me.benjozork.onyx.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array.ArrayIterator;
import me.benjozork.onyx.backend.handlers.RessourceHandler;
import me.benjozork.onyx.game.GameScreen;
import me.benjozork.onyx.utils.PolygonHelper;

/**
 * An entity for a basic projectile
 *
 * @author Benjozork
 */
public class ProjectileEntity extends Entity {

    private final float MAX_SPEED = 8000f;

    private final float DAMAGE = 10f;

    private Sprite projectileSprite;

    private Vector2 target;

    private Entity shooter;

    /**
     * @param x the initial x position of the projectile
     * @param y the initial y position of the projectile
     * @param targetX the x position of the target
     * @param targetY the y position of the target
     * @param initialSpeed the initial speed, aka. the initial length of the velocity vector
     */
    public ProjectileEntity(float x, float y, float targetX, float targetY, float initialSpeed, Entity shooter) {
        super(x, y);

        this.shooter = shooter;

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

        this.projectileSprite.setPosition(this.getX(), this.getY());

        // Check for collision

        for (ArrayIterator<Entity> iterator = new ArrayIterator<Entity>(GameScreen.getGameWorld().getEntities()); iterator.hasNext();) {

            Entity entity = iterator.next();

            // Make sure entity is LivingEntity

            if (entity instanceof LivingEntity) {

                    // Check for collision

                    if (this.collidesWith(entity.getBounds()) && entity.getClass() != this.getShooter().getClass()) {

                        // Apply damage

                        ((LivingEntity) entity).damage(DAMAGE);

                        // Dispose themselves

                        GameScreen.getGameWorld().removeEntity(this);
                        this.dispose();
                    }
            }
        }

    }

    @Override
    public void draw() {
        projectileSprite.draw(RessourceHandler.getBatch());
    }

    @Override
    public void dispose() {
        projectileSprite.getTexture().dispose();
    }

    public Entity getShooter() {
        return shooter;
    }

}