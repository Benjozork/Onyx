package me.benjozork.onyx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import me.benjozork.onyx.GameManager;
import me.benjozork.onyx.PolygonLoader;
import me.benjozork.onyx.game.GameScreenManager;
import me.benjozork.onyx.game.object.HealthBar;
import me.benjozork.onyx.utils.Utils;

/**
 * @author Benjozork
 */
public class PlayerEntity extends LivingEntity {

    // Player textures

    private final Texture PLAYER_TEXTURE = new Texture("entity/player/texture_0.png");
    private final Texture FIRING_PLAYER_TEXTURE = new Texture("entity/player/texture_1.png");
    private final Texture MOVING_FIRING_PLAYER_TEXTURE = new Texture("entity/player/texture_3.png");
    private final Texture MOVING_PLAYER_TEXTURE = new Texture("entity/player/texture_2.png");

    Sprite currentTexture = new Sprite(PLAYER_TEXTURE);

    private final float ANGLE_DELTA = 100, TARGET_ANGLE = 25, ANGLE_DELTA_TOLERANCE = 0.1f;

    private float spriteRotation;

    private DrawState state = DrawState.IDLE;
    private Direction direction = Direction.STRAIGHT;

    private final HealthBar healthBar = new HealthBar(this, 100f, 10f, 100f);

    public PlayerEntity(float x, float y) {
        super(x, y);
    }

    @Override
    public void init() {
        // Initialize hitbox

        bounds = PolygonLoader.getPolygon("Ship",PLAYER_TEXTURE.getWidth(), PLAYER_TEXTURE.getHeight());

		type = Type.PLAYER;

		setBulletShootOrigin(PLAYER_TEXTURE.getWidth() / 2, PLAYER_TEXTURE.getHeight() / 2);
		setBulletImpactTarget(PLAYER_TEXTURE.getWidth() / 2, PLAYER_TEXTURE.getHeight() / 2);
    }

    @Override
    public void update() {
        super.update(Utils.delta());

        // Get rotation depending on direction

        if (direction == Direction.STRAIGHT) {
            if (spriteRotation < ANGLE_DELTA_TOLERANCE && spriteRotation > - ANGLE_DELTA_TOLERANCE) spriteRotation = 0f;
            if (spriteRotation < 0 * MathUtils.degreesToRadians)
                spriteRotation += (ANGLE_DELTA * MathUtils.degreesToRadians) * Utils.delta();
            else if (spriteRotation > 0 * MathUtils.degreesToRadians)
                spriteRotation -= (ANGLE_DELTA * MathUtils.degreesToRadians) * Utils.delta();
            velocity.y = 0;
        } else if (direction == Direction.RIGHT) {
            if (spriteRotation < TARGET_ANGLE * MathUtils.degreesToRadians)
                spriteRotation += (ANGLE_DELTA * MathUtils.degreesToRadians) * Utils.delta();
            velocity.setAngle(- 180f);
            velocity.x -= velocity.x * 2;
            velocity.y = 0;
        } else if (direction == Direction.LEFT) {
            if (spriteRotation > - 25 * MathUtils.degreesToRadians)
                spriteRotation -= (ANGLE_DELTA * MathUtils.degreesToRadians) * Utils.delta();
            velocity.setAngle(180f);
            velocity.x += velocity.x * 2;
            velocity.y = 0;
        }

        // Check and block out-of-bounds movement

        if (getX() < 0) setX(0);
        if (getX() > Gdx.graphics.getWidth() - PLAYER_TEXTURE.getWidth()) setX(Gdx.graphics.getWidth() - PLAYER_TEXTURE.getWidth());

        // Add/sub speed decay

        if (velocity.len() > 0) velocity.setLength(velocity.len() - 15f);
        else velocity.setLength(velocity.len() + 15f);

        // Update texture

        if (state == DrawState.IDLE) {
            currentTexture.setTexture(PLAYER_TEXTURE);
        } else if (state == DrawState.MOVING) {
            currentTexture.setTexture(MOVING_PLAYER_TEXTURE);
        } else if (state == DrawState.FIRING) {
            currentTexture.setTexture(FIRING_PLAYER_TEXTURE);
        } else if (state == DrawState.FIRING_MOVING) {
            currentTexture.setTexture(MOVING_FIRING_PLAYER_TEXTURE);
        }

        // Update texture

        currentTexture.setPosition(getX(), getY());
        currentTexture.setRotation(- spriteRotation * MathUtils.radiansToDegrees);

        // Rotate bounds

        bounds.setRotation(- spriteRotation * MathUtils.radiansToDegrees);
    }

    @Override
    public void draw() {
        SpriteBatch batch = GameManager.getBatch();
        currentTexture.draw(batch);
        healthBar.draw();
    }

    @Override
    public void dispose() {
        GameScreenManager.removeEntity(this);
        PLAYER_TEXTURE.dispose();
        FIRING_PLAYER_TEXTURE.dispose();
        MOVING_FIRING_PLAYER_TEXTURE.dispose();
        MOVING_PLAYER_TEXTURE.dispose();
    }

    /**
     * The current DrawState
     * @return the state
     */
    public DrawState getState() {
        return state;
    }

    /**
     * Sets the current DrawState
     * @param v the state to be used
     */
    public void setState(DrawState v) {
        this.state = v;
    }

    /**
     * The direction of the player
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Changes the direction of the player
     * @param v the direction to be used
     */
    public void setDirection(Direction v) {
        this.direction = v;
    }

    public boolean isFiring() {
        return Gdx.input.isKeyPressed(Input.Keys.SPACE);
    }

    @Override
    public float getTextureWidth() {
        return PLAYER_TEXTURE.getWidth();
    }

    @Override
    public float getTextureHeight() {
        return PLAYER_TEXTURE.getHeight();
    }

    public enum DrawState {
        IDLE,
        FIRING,
        MOVING,
        FIRING_MOVING,
    }

    public enum Direction {
        STRAIGHT,
        RIGHT,
        LEFT
    }

}
