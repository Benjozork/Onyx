package me.benjozork.onyx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import me.benjozork.onyx.internal.Utils;
import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.screen.GameScreen;

/**
 * Created by Benjozork on 2017-03-04.
 */
public class EntityPlayer extends LivingEntity {

     ShapeRenderer renderer;
     Sprite sprite;
     Texture img;
     Texture img_firing;
     Texture img_firing_moving;
     Texture img_moving;
     private Vector3 mouse = new Vector3();

     private DrawState state = DrawState.IDLE;

     public EntityPlayer(float x, float y) {
          super(new Vector2(x, y));
     }

     @Override
     public void init() {
          // Get the ShapeRenderer
          renderer = ((GameScreen) GameManager.getCurrentScreen()).getShapeRenderer();
          // Initialize hitbox
          bounds = new Rectangle(getX(), getY(), 50, 50);

          sprite = new Sprite();

          img = new Texture("core/assets/ship/ship.png");
          img_firing = new Texture("core/assets/ship/ship_weapon_fire.png");
          img_firing_moving = new Texture("core/assets/ship/ship_weapon_engine_fire.png");
          img_moving = new Texture("core/assets/ship/ship_engine_fire.png");
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

          // Render box
        /*renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.WHITE);
        renderer.rect(getX(), getY(), 25, 25, 50, 50, 1f, 1f, (float) -angle * MathUtils.radiansToDegrees);
        renderer.end();*/

          if (state == DrawState.IDLE) {
               sprite = new Sprite(img);
          } else if (state == DrawState.MOVING) {
               sprite = new Sprite(img_moving);
          } else if (state == DrawState.FIRING) {
               sprite = new Sprite(img_firing);
          } else if (state == DrawState.FIRING_MOVING) {
               sprite = new Sprite(img_firing_moving);
          }

          //sprite.setColor(GameManager.getCurrentColor());
          sprite.setPosition(getX(), getY());
          sprite.setRotation((float) - angle * MathUtils.radiansToDegrees);

          ((GameScreen) GameManager.getCurrentScreen()).getBatch().begin();
          //GameManager.getBatch().draw(img, getX(), getY(), 0, 0, sprite.getTexture().getWidth(), sprite.getTexture().getHeight(), 1f, 1f, (float) -angle, 0, 0, 0, 0, false, false);
          sprite.draw(((GameScreen) GameManager.getCurrentScreen()).getBatch());
          ((GameScreen) GameManager.getCurrentScreen()).getBatch().end();
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
