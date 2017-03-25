package me.benjozork.onyx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.internal.Utils;

/**
 * Created by Benjozork on 2017-03-04.
 */
public class EntityPlayer extends LivingEntity {


    private final Texture PLAYER_TEXTURE = new Texture("ship/ship.png");
    private final Texture FIRING_PLAYER_TEXTURE = new Texture("ship/ship_weapon_fire.png");
    private final Texture MOVING_FIRING_PLAYER_TEXTURE = new Texture("ship/ship_weapon_engine_fire.png");
    private final Texture MOVING_PLAYER_TEXTURE = new Texture("ship/ship_engine_fire.png");

    Sprite currentTexture = new Sprite(PLAYER_TEXTURE);

    private Vector3 mouse = new Vector3();

    private DrawState state = DrawState.IDLE;

    public EntityPlayer(float x, float y) {
        super(new Vector2(x, y));
    }

    @Override
    public void init() {
        // Initialize hitbox
        bounds = new Rectangle(getX(), getY(), 50, 50);
    }

    @Override
    public void update() {
        if (angle > 360) {
            angle = 120 * Utils.delta();
        } else if (angle < - 360) {
            angle = - 120 * Utils.delta();
        }

          /*// Check for out of bounds
          if ((getX() + 50 > Gdx.graphics.getWidth())) {
               setX(Gdx.graphics.getWidth() - 51);
               setSpeed(- 10f);
          } else if ((getX()) < 0) {
               setX(1);
               setSpeed(10f);
          } else if ((getY() + 50) > Gdx.graphics.getHeight()) {
               setY(Gdx.graphics.getHeight() - 51);
               setSpeed(- 10f);
          } else if ((getY()) < 0) {
               setY(1);
               setSpeed(10f);
          }*/


    }

    @Override
    public void draw() {

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
        currentTexture.setRotation((float) - angle * MathUtils.radiansToDegrees);

        SpriteBatch batch = GameManager.getBatch();
        batch.begin();
        //GameManager.getBatch().draw(img, getX(), getY(), 0, 0, sprite.getTexture().getWidth(), sprite.getTexture().getHeight(), 1f, 1f, (float) -angle, 0, 0, 0, 0, false, false);
        currentTexture.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {

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

    public DrawState getState() {
        return state;
    }

    public void setState(DrawState v) {
        this.state = v;
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

}
