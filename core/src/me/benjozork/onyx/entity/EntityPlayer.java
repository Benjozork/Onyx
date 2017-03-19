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

import me.benjozork.onyx.GameManager;
import me.benjozork.onyx.internal.GameUtils;

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

     public enum DrawState {
          IDLE,
          FIRING,
          MOVING,
          FIRING_MOVING,
     }

     @Override
     public void init() {
          // Get the ShapeRenderer
          renderer = GameManager.getShapeRenderer();
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
               angle = 120 * GameUtils.getDelta();
          } else if (angle < - 360) {
               angle = - 120 * GameUtils.getDelta();
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

          sprite.setPosition(getX(), getY());
          sprite.setRotation((float) - angle * MathUtils.radiansToDegrees);

          GameManager.getBatch().begin();
          //GameManager.getBatch().draw(img, getX(), getY(), 0, 0, sprite.getTexture().getWidth(), sprite.getTexture().getHeight(), 1f, 1f, (float) -angle, 0, 0, 0, 0, false, false);
          sprite.draw(GameManager.getBatch());
          GameManager.getBatch().end();
     }

     @Override
     public void move(float vx, float vy) {
          if ((getX() + vx * GameUtils.getDelta()) + 50 > Gdx.graphics.getWidth()
                  || (getY() + vy * GameUtils.getDelta()) + 50 > Gdx.graphics.getHeight()
                  || (getX() + vx * GameUtils.getDelta()) < 0
                  || (getY() + vy * GameUtils.getDelta()) < 0) {
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

}
