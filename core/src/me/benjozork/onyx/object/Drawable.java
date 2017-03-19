package me.benjozork.onyx.object;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.internal.GameUtils;

/**
 * Created by Benjozork on 2017-03-16.
 */
public abstract class Drawable {

     protected Vector2 position;
     protected Vector2 velocity = new Vector2(0, 0);
     protected Vector2 acceleration = new Vector2(0, 0);
     protected float maxVelocity = 100f;
     protected double angle;
     protected State state;
     protected Rectangle bounds;
     private float speed;

     public Drawable(int x, int y) {
          this.position = new Vector2(x, y);
     }

     public Drawable(Vector2 position) {
          this.position = position;
     }

     public void update(float dt) {

          bounds.setPosition(position);
          // double angle = Math.atan2((Gdx.graphics.getWidth() - Gdx.input.getX()) - loc.getX(), (Gdx.graphics.getHeight() - Gdx.input.getY()) - loc.getY());
          // set velocity/direction vector with angle, if angle isnt zero ( to prevent garbage data in velocity vector )
          if (angle != 0) {
               velocity.x = (float) Math.sin(angle);
               velocity.y = (float) Math.cos(angle);
          }

          // normalize velocity
          velocity.nor();

          //velocity.add(acceleration);
          // add velocity scaled to speed and timestep to position
          position.add(velocity.scl(speed).scl(GameUtils.getDelta()));
     }

     public abstract void init();

     public abstract void update();

     public abstract void draw();

     public Rectangle getBounds() {
          return bounds;
     }

     public void setBounds(Rectangle bounds) {
          this.bounds = bounds;
     }

     public boolean collidesWith(Rectangle otherBounds) {
          return bounds.overlaps(otherBounds);
     }

     public Vector2 getPosition() {
          return position;
     }

     public void setPosition(Vector2 position) {
          this.position = position;
          this.bounds.setPosition(position);
     }

     public float getX() {
          return position.x;
     }

     public void setX(float v) {
          position.x = v;
          bounds.x = v;
     }

     public float getY() {
          return position.y;
     }

     public void setY(float v) {
          position.y = v;
          bounds.y = v;
     }

     public void move(float dx, float dy) {
          // here, we move the position and the bounding box
          position.x += dx * GameUtils.getDelta();
          bounds.x += dx * GameUtils.getDelta();
          position.y += dy * GameUtils.getDelta();
          bounds.y += dy * GameUtils.getDelta();
     }

     public Vector2 getVelocity() {
          return velocity;
     }

     public void setVelocity(Vector2 velocity) {
          this.velocity = velocity;
     }

     public Vector2 getAcceleration() {
          return acceleration;
     }

     public void setAcceleration(Vector2 acceleration) {
          this.acceleration = acceleration;
     }

     public void accelerate(float v) {
          speed += v;
     }

     public float getMaxVelocity() {
          return maxVelocity;
     }

     public void setMaxVelocity(float maxVelocity) {
          this.maxVelocity = maxVelocity;
     }

     /*public State getState() {
          return state;
     }*/

     public void setState(State state) {
          this.state = state;
     }

     public void rotate(float v) {
          angle += v * GameUtils.getDelta();
     }

     public float getSpeed() {
          return speed;
     }

     public void setSpeed(float speed) {
          this.speed = speed;
     }

     public abstract void dispose();

     public enum State {
          MOVE,
          STOP
     }

}
