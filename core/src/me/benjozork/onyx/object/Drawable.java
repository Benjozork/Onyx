package me.benjozork.onyx.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import me.benjozork.onyx.internal.PolygonHelper;
import me.benjozork.onyx.utils.Utils;

/**
 * Describes an object that is to be drawn on the screen
 * @author Benjozork
 */
public abstract class Drawable implements Disposable {

    protected Vector2 position;
    protected Vector2 velocity = new Vector2(0, 0);
    protected Vector2 acceleration = new Vector2(0, 0);

    protected float maxVelocity = 100f;
    protected float angle;
    protected Polygon bounds;
    private float speed, maxSpeed;

    private boolean boundsDebug = false;
    private boolean defaultMaxSpeed = true;

    public Drawable(int x, int y) {
        this.position = new Vector2(x, y);
    }

    public Drawable(Vector2 position) {
        this.position = position;
    }

    /**
     * Internally updates the Drawable
     * @param dt The delta time
     */
    public void update(float dt) {
        if (defaultMaxSpeed) maxSpeed = speed + 1f;
        bounds.setPosition(position.x, position.y);

        if (speed > maxSpeed) speed = maxSpeed;
        if (speed < - maxSpeed) speed = - maxSpeed;

        if (angle != 0) {
            velocity.x = (float) Math.sin(angle);
            velocity.y = (float) Math.cos(angle);
        }

        position.add(velocity.nor().scl(dt).scl(speed));
    }

    //Abstract methods

    public abstract void init();

    public abstract void update();

    public abstract void draw();

    public abstract void dispose();

    /**
     * Checks if the Drawable collides with a specified {@link Polygon}
     * @param otherBounds the polygon used to perform the check
     * @return whether the Drawable collides with otherBounds
     */
    public boolean collidesWith(Polygon otherBounds) {
        return PolygonHelper.collidePolygon(bounds,otherBounds);
    }

    /**
     * The Drawable's position
     * @return The Drawable's position
     */
    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
        this.bounds.setPosition(position.x, position.y);
    }

    /**
     * The X coordinate value
     * @return The X coordinate value
     */
    public float getX() {
        return position.x;
    }

    public void setX(float v) {
        position.x = v;
        bounds.setPosition(position.x, position.y);
    }

    /**
     * The Y coordinate value
     * @return The Y coordinate value
     */
    public float getY() {
        return position.y;
    }

    public void setY(float v) {
        position.y = v;
        bounds.setPosition(position.x, position.y);
    }

    public void move(float dx, float dy) {
        // here, we move the position and the bounding box
        position.x += dx * Utils.delta();
        position.y += dy * Utils.delta();
        bounds.setPosition(position.x, position.y);
    }

    /**
     * The velocity
     * @return The velocity
     */
    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    /**
     * The acceleration
     * @return The acceleration
     */
    public Vector2 getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }

    /**
     * Changes the speed
     * @param v the speed offset
     */
    public void accelerate(float v) {
        speed += v;
        if (velocity.x == 0 && velocity.y == 0 && speed > 0f) velocity.set(0, 1); // Prevent this from having no effect
    }

    /**
     * The max velocity
     * @return The max velocity
     */
    public float getMaxVelocity() {
        return maxVelocity;
    }

    public void setMaxVelocity(float maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public void rotate(float v) {
        angle += v * Utils.delta();
    }

    /**
     * The speed
     * @return The speed
     */
    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
        defaultMaxSpeed = false;
    }

    /**
     * Checks if the mouse hovers above the hitbox
     */
    public boolean hovering() {
        Vector2 mouse = Utils.unprojectWorld(Gdx.input.getX(), Gdx.input.getY());
        return getBounds().contains(mouse);
//        if (mouse.x > getBounds().getX()
//                && mouse.x < (getBounds().getX() + getBounds().getWidth())
//                && mouse.y > getBounds().getY()
//                && mouse.y < (getBounds().getY() + getBounds().getHeight())) {
//            return true;
//        } else return false;
    }

    /**
     * The bounding box
     * @return The bounding box
     */
    public Polygon getBounds() {
        return bounds;
    }

    public void setBounds(Polygon bounds) {
        this.bounds = bounds;
    }

    /**
     * Toggle the hitbox debug rendering
     */
    public void toggleBoundsDebug() {
        boundsDebug = ! boundsDebug;
    }

}
