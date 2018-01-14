package me.benjozork.onyx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import me.benjozork.onyx.GameManager;
import me.benjozork.onyx.KeymapLoader;
import me.benjozork.onyx.utils.PolygonHelper;
import me.benjozork.onyx.utils.Utils;

/**
 * @author Benjozork
 */
public class PlayerEntity extends Entity {

    private final int ACCELERATION = 10;

    private final int MAX_SPEED = 400;

    private final int SPEED_DECAY_DELTA = 400;

    private final int ROTATION_ANGLE_TARGET = 25;

    private final int ROTATION_ANGLE_DELTA = 155;

    private final float ROTATION_ANGLE_TOLERANCE = 0.1f;


    private Sprite playerSprite;

    private Direction currDirection = Direction.STRAIGHT;

    public PlayerEntity(float x, float y) {
        super(x, y);

        this.playerSprite = new Sprite(new Texture("entity/player/texture_0.png"));

        this.width = playerSprite.getWidth();
        this.height = playerSprite.getHeight();

        this.setBounds(PolygonHelper.getPolygon(this.getX(), this.getY(), this.width, this.height));

        this.setMaxSpeed(MAX_SPEED);
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

    }

    @Override
    public void draw() {
        playerSprite.draw(GameManager.getBatch());
    }

    @Override
    public void dispose() {

    }

    private enum Direction {
        LEFT,
        STRAIGHT,
        RIGHT
    }

}