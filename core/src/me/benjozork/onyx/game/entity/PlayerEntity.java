package me.benjozork.onyx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.backend.handlers.KeymapHandler;
import me.benjozork.onyx.backend.handlers.RessourceHandler;
import me.benjozork.onyx.game.GameScreen;
import me.benjozork.onyx.game.GameWorld;
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
    private final int ACCELERATION = 15;

    /**
     * The maximum speed, in both the x and y axis, of the player
     */
    private final int MAX_SPEED = 400;

    /**
     * The number, multiplied by the delta time, which is subtracted from the velocity vector in both the x and y axis every frame
     */
    private final int SPEED_DECAY_DELTA = 175;

    /**
     * The target rotation angle, in degrees, of the player sprite
     */
    private final int ROTATION_ANGLE_TARGET = 5;

    /**
     * The number, multiplied by the delta time, which is added/subtracted from the player sprite angle every frame
     */
    private final int ROTATION_ANGLE_DELTA = 10;

    /**
     * The level, above or below, at which the player sprite angle "snaps" to zero
     */
    private final float ROTATION_ANGLE_TOLERANCE = 0.1f;

    /**
     * The rate at which the player can fire when holding the firing key, in fires per minute.
     */
    private final float FIRING_RATE = 600;

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

        RessourceHandler.getWorldCamera().position.set(position.cpy().add(0, Gdx.graphics.getHeight() / 3), 0);

        // Adjust camera rotation

        RessourceHandler.getWorldCamera().up.set(0, 1, 0);
        RessourceHandler.getWorldCamera().direction.set(0, 0, -1);
        RessourceHandler.getWorldCamera().rotate(-playerSprite.getRotation());

        // Update camera

        RessourceHandler.getWorldCamera().update();

        RessourceHandler.getBatch().setProjectionMatrix(RessourceHandler.getWorldCamera().combined);

        // Make sure player doesn't exit world bounds

        if (this.getX() - (playerSprite.getWidth() / 2) < - GameWorld.WORLD_SIZE_X / 2) {
            this.setX(- GameWorld.WORLD_SIZE_X / 2 + playerSprite.getWidth() / 2);
        }

        if (this.getX() + (playerSprite.getWidth() / 2) > GameWorld.WORLD_SIZE_X / 2) {
            this.setX(GameWorld.WORLD_SIZE_X / 2 - playerSprite.getWidth() / 2);
        }

        if (this.getY() - (playerSprite.getHeight() / 2) < - GameWorld.WORLD_SIZE_Y / 2) {
            this.setY(- GameWorld.WORLD_SIZE_Y / 2 + playerSprite.getHeight() / 2);
        }

        if (this.getY() + (playerSprite.getHeight() / 2) > GameWorld.WORLD_SIZE_Y / 2) {
            this.setY(GameWorld.WORLD_SIZE_Y / 2 - playerSprite.getHeight() / 2);
        }

        // Accelerate if movement keys are pressed

        if (Gdx.input.isKeyPressed(KeymapHandler.getKeyCode("player_movement_forward"))) {
            this.accelerate(0, ACCELERATION);
        }

        if (Gdx.input.isKeyPressed(KeymapHandler.getKeyCode("player_movement_backward"))) {
            this.accelerate(0, -ACCELERATION);
        }

        if (Gdx.input.isKeyPressed(KeymapHandler.getKeyCode("player_movement_left"))) {
            this.accelerate(-ACCELERATION, 0);
            this.currDirection = Direction.LEFT;
        }

        if (Gdx.input.isKeyPressed(KeymapHandler.getKeyCode("player_movement_right"))) {
            this.accelerate(ACCELERATION, 0);
            this.currDirection = Direction.RIGHT;
        }

        if (! Gdx.input.isKeyPressed(KeymapHandler.getKeyCode("player_movement_right"))
                && ! Gdx.input.isKeyPressed(KeymapHandler.getKeyCode("player_movement_left"))) {
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

        if (Gdx.input.isKeyPressed(KeymapHandler.getKeyCode("player_fire_primary")) &&
                fireTimer > fireInterval) {

            Vector2 mouseTarget = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            mouseTarget.set(Utils.unprojectWorld(mouseTarget));

            GameScreen.getGameWorld().addEntity(new ProjectileEntity(this.getX(), this.getY(), mouseTarget.x, mouseTarget.y, INITIAL_PROJECTILE_SPEED));
            this.fireTimer = 0;
        }

    }

    @Override
    public void draw() {
        playerSprite.draw(RessourceHandler.getBatch());
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