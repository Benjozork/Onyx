package me.benjozork.onyx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

import me.benjozork.onyx.entity.ai.AI;
import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.utils.PolygonHelper;
import me.benjozork.onyx.screen.GameScreenManager;
import me.benjozork.onyx.utils.Utils;

/**
 * @author Benjozork
 */
public class EntityEnemy extends LivingEntity {

    // Enemy textures
    private final Texture ENEMY_TEXTURE = new Texture("entity/enemy/texture_0.png");
    private final Texture FIRING_ENEMY_TEXTURE = new Texture("entity/enemy/texture_0.png");
    private final Texture MOVING_FIRING_ENEMY_TEXTURE = new Texture("entity/enemy/texture_0.png");
    private final Texture MOVING_ENEMY_TEXTURE = new Texture("entity/enemy/texture_0.png");

    Sprite currentTexture = new Sprite(ENEMY_TEXTURE);

    private DrawState state = DrawState.IDLE;
    private Direction direction = Direction.STRAIGHT;
    private float spriteRotation;
    private boolean accelerated_right = false, accelerated_left = false;
    private AI ai;

    public EntityEnemy(float x, float y) {
        super(new Vector2(x, y));
    }

    @Override
    public void init() {
        // Initialize hitbox
        bounds = PolygonHelper.getPolygon(getX(), getY(), ENEMY_TEXTURE.getWidth(), ENEMY_TEXTURE.getHeight());
        currentTexture.flip(false, true);
        ai = new AI(this,GameScreenManager.getPlayer(), AI.AIStrategy.LINEAR, AI.ProjectileReluctance.HIGH);
        type = Type.ENEMY;
    }

    @Override
    public void update() {
        super.update(Utils.delta());
        ai.update(Utils.delta());
        // The simplest AI ever written
        if(velocity.x < -2)
            direction = Direction.LEFT;
        else if(velocity.x > 2)
            direction = Direction.RIGHT;
        else
            direction = Direction.STRAIGHT;

        if (direction == EntityEnemy.Direction.STRAIGHT) {
            if (spriteRotation < 0.1 && spriteRotation > - 0.1) spriteRotation = 0f;
            if (spriteRotation < 0 * MathUtils.degreesToRadians)
                spriteRotation += (200 * MathUtils.degreesToRadians) * Utils.delta();
            else if (spriteRotation > 0 * MathUtils.degreesToRadians)
                spriteRotation -= (200 * MathUtils.degreesToRadians) * Utils.delta();
        } else if (direction == EntityEnemy.Direction.RIGHT) {
            if (spriteRotation < 25 * MathUtils.degreesToRadians)
                spriteRotation += (200 * MathUtils.degreesToRadians) * Utils.delta();
        } else if (direction == EntityEnemy.Direction.LEFT) {
            if (spriteRotation > - 25 * MathUtils.degreesToRadians)
                spriteRotation -= (200 * MathUtils.degreesToRadians) * Utils.delta();
        }


        if (state == EntityEnemy.DrawState.IDLE) {
            currentTexture.setTexture(ENEMY_TEXTURE);
        } else if (state == EntityEnemy.DrawState.MOVING) {
            currentTexture.setTexture(MOVING_ENEMY_TEXTURE);
        } else if (state == EntityEnemy.DrawState.FIRING) {
            currentTexture.setTexture(FIRING_ENEMY_TEXTURE);
        } else if (state == EntityEnemy.DrawState.FIRING_MOVING) {
            currentTexture.setTexture(MOVING_FIRING_ENEMY_TEXTURE);
        }

        System.out.printf("X: %f Y: %f\n",bounds.getX(),bounds.getY());
        currentTexture.setPosition(bounds.getX(), bounds.getY());
        currentTexture.setRotation((float) - spriteRotation * MathUtils.radiansToDegrees);
    }

    @Override
    public void draw() {
        SpriteBatch batch = GameManager.getBatch();
        currentTexture.draw(batch);
    }

    @Override
    public void dispose() {
        GameScreenManager.removeEntity(this);
        GameScreenManager.setEnemy(null);
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
