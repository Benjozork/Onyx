package me.benjozork.onyx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.internal.PolygonHelper;
import me.benjozork.onyx.utils.Utils;

/**
 * @author Benjozork
 */
public class EntityPlayer extends LivingEntity {

    // Player textures
    private final Texture PLAYER_TEXTURE = new Texture("entity/player/texture_0.png");
    private final Texture FIRING_PLAYER_TEXTURE = new Texture("entity/player/texture_2.png");
    private final Texture MOVING_FIRING_PLAYER_TEXTURE = new Texture("entity/player/texture_3.png");
    private final Texture MOVING_PLAYER_TEXTURE = new Texture("entity/player/texture_1.png");

    Sprite currentTexture = new Sprite(PLAYER_TEXTURE);

    private Vector3 mouse = new Vector3();

    private DrawState state = DrawState.IDLE;
    private Direction direction = Direction.STRAIGHT;
    private float spriteRotation;
    private boolean accelerated_right = false, accelerated_left = false;

    public EntityPlayer(float x, float y) {
        super(new Vector2(x, y));
    }

    @Override
    public void init() {
        // Initialize hitbox
        bounds = PolygonHelper.getPolygon(getX(), getY(), PLAYER_TEXTURE.getWidth(), PLAYER_TEXTURE.getHeight());
    }

    @Override
    public void update() {

        if (direction == Direction.STRAIGHT) {
            if (spriteRotation < 0.1 && spriteRotation > - 0.1) spriteRotation = 0f;
            if (spriteRotation < 0 * MathUtils.degreesToRadians)
                spriteRotation += (200 * MathUtils.degreesToRadians) * Utils.delta();
            else if (spriteRotation > 0 * MathUtils.degreesToRadians)
                spriteRotation -= (200 * MathUtils.degreesToRadians) * Utils.delta();
        } else if (direction == Direction.RIGHT) {
            if (spriteRotation < 25 * MathUtils.degreesToRadians)
                spriteRotation += (200 * MathUtils.degreesToRadians) * Utils.delta();
            velocity.setAngle(- 180f);
            if (! accelerated_right) {
                accelerate(5f);
                accelerated_right = true;
                accelerated_left = false;
            }
            if (velocity.x < 0) {
                velocity.x -= velocity.x * 2;
            }
        } else if (direction == Direction.LEFT) {
            if (spriteRotation > - 25 * MathUtils.degreesToRadians)
                spriteRotation -= (200 * MathUtils.degreesToRadians) * Utils.delta();
            velocity.setAngle(180f);
            if (! accelerated_left) {
                accelerate(5f);
                accelerated_left = true;
                accelerated_right = false;
            }
            if (velocity.x > 0) {
                velocity.x -= velocity.x * 2;
            }
        }

        if (getSpeed() > 0) setSpeed(getSpeed() - 15f);
        else setSpeed(getSpeed() + 5f);

        if (state == DrawState.IDLE) {
            currentTexture.setTexture(PLAYER_TEXTURE);
        } else if (state == DrawState.MOVING) {
            currentTexture.setTexture(MOVING_PLAYER_TEXTURE);
        } else if (state == DrawState.FIRING) {
            currentTexture.setTexture(FIRING_PLAYER_TEXTURE);
        } else if (state == DrawState.FIRING_MOVING) {
            currentTexture.setTexture(MOVING_FIRING_PLAYER_TEXTURE);
        }

        //sprite.setColor(GameManager.getCurrentColor());
        currentTexture.setPosition(getX(), getY());
        currentTexture.setRotation((float) - spriteRotation * MathUtils.radiansToDegrees);
    }

    @Override
    public void draw() {
        SpriteBatch batch = GameManager.getBatch();
        //GameManager.getBatch().draw(img, getX(), getY(), 0, 0, sprite.getTexture().getWidth(), sprite.getTexture().getHeight(), 1f, 1f, (float) -angle, 0, 0, 0, 0, false, false);
        currentTexture.draw(batch);
    }

    @Override
    public void dispose() {
        PLAYER_TEXTURE.dispose();
        FIRING_PLAYER_TEXTURE.dispose();
        MOVING_FIRING_PLAYER_TEXTURE.dispose();
        MOVING_PLAYER_TEXTURE.dispose();
    }

    @Override
    public void move(float vx, float vy) {
        if ((getX() + vx * Utils.delta()) + 50 > Gdx.graphics.getWidth()
                || (getY() + vy * Utils.delta()) + 50 > Gdx.graphics.getHeight()
                || (getX() + vx * Utils.delta()) < 0
                || (getY() + vy * Utils.delta()) < 0) {
            return;
        }
        super.move(vx, vy);
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
