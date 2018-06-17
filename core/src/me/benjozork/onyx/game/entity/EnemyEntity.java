package me.benjozork.onyx.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import me.benjozork.onyx.backend.handlers.RessourceHandler;
import me.benjozork.onyx.game.GameScreen;
import me.benjozork.onyx.game.entity.ai.AI;
import me.benjozork.onyx.utils.PolygonHelper;

/**
 * @author Benjozork
 */
public class EnemyEntity extends LivingEntity {

    /**
     * The maximum speed, in both the x and y axis, of the player
     */
    private final int MAX_SPEED = 400;

    /**
     * The number, multiplied by the delta time, which is subtracted from the velocity vector in both the x and y axis every frame
     */
    private final int SPEED_DECAY_DELTA = 175;

    /**
     * The rate at which the player can fire when holding the firing key, in fires per minute.
     */
    private final float FIRING_RATE = 600;

    /**
     * The initial speed of every projectile.
     */
    private final float INITIAL_PROJECTILE_SPEED = 800;

    /**
     * The initial and maximum health.
     */
    private final float MAX_HEALTH = 100f;

    private final AI ai;

    private final Sprite enemySprite;

    private float fireInterval, fireTimer = 0;

    public EnemyEntity(float x, float y) {
        super(x, y);

        this.ai = new AI(this, GameScreen.getGameWorld().getPlayer());

        this.enemySprite = new Sprite(new Texture("entity/enemy/texture_0.png"));
        this.enemySprite.setOrigin(this.enemySprite.getWidth() / 2, this.enemySprite.getHeight() / 2);
        this.enemySprite.flip(false, true);

        this.width = enemySprite.getWidth();
        this.height = enemySprite.getHeight();

        this.setBounds(PolygonHelper.getPolygon(this.getX(), this.getY(), this.width, this.height));

        this.setMaxSpeed(MAX_SPEED);

        this.setMaxHealth(MAX_HEALTH);
        this.setHealth(this.getMaxHealth());

        this.fireInterval = (60 / FIRING_RATE);
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

        // Update the AI

        this.ai.update();

        // Update sprite position to entity position and rotation

        this.enemySprite.setPosition(this.getX(), this.getY());
        this.enemySprite.setRotation(this.getRotation());
    }

    @Override
    public void draw() {
        enemySprite.draw(RessourceHandler.getBatch());
    }

    @Override
    public void dispose() {

    }

    /**
     * @return the {@link AI} object for this entity
     */
    public AI getAI() {
        return ai;
    }

}