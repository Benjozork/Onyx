package me.benjozork.onyx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import me.benjozork.onyx.GameManager;
import me.benjozork.onyx.KeymapLoader;
import me.benjozork.onyx.game.GameScreen;
import me.benjozork.onyx.utils.PolygonHelper;
import me.benjozork.onyx.utils.Utils;

/**
 * Class for the player entity. Contains the necessary movement and shooting mechanics.
 *
 * @author Benjozork
 */
public class PlayerEntity extends Entity {

    /**
     * The number that is added to the velocity vector, in the direction of the key that was pressed
     */
    private final int ACCELERATION = 10;

    /**
     * The maximum speed, in both the x and y axis, of the player
     */
    private final int MAX_SPEED = 400;

    /**
     * The number, multiplied by the delta time, which is subtracted from the velocity vector in both the x and y axis every frame
     */
    private final int SPEED_DECAY_DELTA = 400;

    /**
     * The target rotation angle, in degrees, of the player sprite
     */
    private final int ROTATION_ANGLE_TARGET = 25;

    /**
     * The number, multiplied by the delta time, which is added/subtracted from the player sprite angle every frame
     */
    private final int ROTATION_ANGLE_DELTA = 155;

    /**
     * The level, above or below, at which the player sprite angle "snaps" to zero
     */
    private final float ROTATION_ANGLE_TOLERANCE = 0.1f;

    /**
     * The rate at which the player can fire when holding the firing key, in fires per minute.
     */
    private final float FIRING_RATE = 400;

    /**
     * The initial speed of every projectile.
     */
    private final float INITIAL_PROJECTILE_SPEED = 800;

    private Sprite playerSprite;

    private Direction currDirection = Direction.STRAIGHT;

    private float fireInterval, fireTimer = 0;

    public PlayerEntity(float x, float y) {
        super(x, y);

        this.playerSprite = new Sprite(new Texture("entity/player/texture_0.png"));

        this.width = playerSprite.getWidth();
        this.height = playerSprite.getHeight();

        this.setBounds(PolygonHelper.getPolygon(this.getX(), this.getY(), this.width, this.height));

        this.setMaxSpeed(MAX_SPEED);

        this.fireInterval = (60 / FIRING_RATE);
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

        // Center camera on player

        GameManager.getWorldCamera().position.set(position, 0);
        GameManager.getWorldCamera().update();

        GameManager.getBatch().setProjectionMatrix(GameManager.getWorldCamera().combined);

        // Accelerate if movement keys are pressed

        if (Gdx.input.isKeyPressed(KeymapLoader.getKeyCode("player_movement_forward"))) {
            this.accelerate(0, ACCELERATION);
        }

        if (Gdx.input.isKeyPressed(KeymapLoader.getKeyCode("player_movement_backward"))) {
            this.accelerate(0, -ACCELERATION);
        }

        if (Gdx.input.isKeyPressed(KeymapLoader.getKeyCode("player_movement_left"))) {
            this.accelerate(-ACCELERATION, 0);
            this.currDirection = Direction.LEFT;
        }

        if (Gdx.input.isKeyPressed(KeymapLoader.getKeyCode("player_movement_right"))) {
            this.accelerate(ACCELERATION, 0);
            this.currDirection = Direction.RIGHT;
        }

        if (! Gdx.input.isKeyPressed(KeymapLoader.getKeyCode("player_movement_right"))
                && ! Gdx.input.isKeyPressed(KeymapLoader.getKeyCode("player_movement_left"))) {
            this.currDirection = Direction.STRAIGHT;
        }

        // Decay the speed for positive x

        if (this.velocity.x > 0f) {
            this.velocity.x -= SPEED_DECAY_DELTA * Utils.delta();
            if (this.velocity.x < 0) this.velocity.x = 0;
        }

        // Decay the speed for negative x

        else if (this.velocity.x < 0f) {
            this.velocity.x += SPEED_DECAY_DELTA * Utils.delta();
            if (this.velocity.x > 0) this.velocity.x = 0;
        }

        // Decay the speed for positive y

        if (this.velocity.y > 0f) {
            this.velocity.y -= SPEED_DECAY_DELTA * Utils.delta();
            if (this.velocity.y < 0) this.velocity.y = 0;
        }

        // Decay the speed for negative y

        else if (this.velocity.y < 0f) {
            this.velocity.y += SPEED_DECAY_DELTA * Utils.delta();
            if (this.velocity.y > 0) this.velocity.y = 0;
        }

        // Rotate sprite to match direction

        if (this.currDirection == Direction.STRAIGHT && playerSprite.getRotation() < 0) {
            playerSprite.setRotation(playerSprite.getRotation() + ROTATION_ANGLE_DELTA * Utils.delta());
            if (playerSprite.getRotation() > - ROTATION_ANGLE_TOLERANCE) playerSprite.setRotation(0);
        }
        if (this.currDirection == Direction.STRAIGHT && playerSprite.getRotation() > 0) {
            playerSprite.setRotation(playerSprite.getRotation() - ROTATION_ANGLE_DELTA * Utils.delta());
            if (playerSprite.getRotation() < ROTATION_ANGLE_TOLERANCE) playerSprite.setRotation(0);
        }

        if (this.currDirection == Direction.LEFT && playerSprite.getRotation() < ROTATION_ANGLE_TARGET) {
            playerSprite.setRotation(playerSprite.getRotation() + ROTATION_ANGLE_DELTA * Utils.delta());
        }

        if (this.currDirection == Direction.RIGHT && playerSprite.getRotation() > - ROTATION_ANGLE_TARGET) {
            playerSprite.setRotation(playerSprite.getRotation() - ROTATION_ANGLE_DELTA * Utils.delta());
        }

        // Update sprite position to entity position

        this.playerSprite.setPosition(this.getX() - width / 2, this.getY() - width / 2);

        /////////// *-* ///////////

        // Fire projectile if firing key is pressed

        fireTimer += Utils.delta();

        if (Gdx.input.isKeyPressed(KeymapLoader.getKeyCode("player_fire_primary")) &&
                fireTimer > fireInterval) {
            GameScreen.getGameWorld().addEntity(new ProjectileEntity(this.getX(), this.getY(), 50, 50, INITIAL_PROJECTILE_SPEED));
            this.fireTimer = 0;
        }

    }

    @Override
    public void draw() {
        playerSprite.draw(GameManager.getBatch());
    }

    @Override
    public void dispose() {
        playerSprite.getTexture().dispose();
    }

    private enum Direction {
        LEFT,
        STRAIGHT,
        RIGHT
    }

}