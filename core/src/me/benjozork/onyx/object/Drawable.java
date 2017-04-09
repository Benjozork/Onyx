package me.benjozork.onyx.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import me.benjozork.onyx.utils.PolygonHelper;
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

    private float maxSpeed;
    private float accelerationScalar;

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
        if (defaultMaxSpeed) maxSpeed = velocity.len() + 1f;

        if (velocity.len() > maxSpeed) velocity.setLength(maxSpeed);
        if (velocity.len() < - maxSpeed) velocity.setLength(-maxSpeed);

        if (angle != 0) {
            velocity.set(velocity.x * (float)Math.sin(angle), velocity.y * (float)Math.cos(angle));
        }

        position.add(velocity.scl(dt));
        velocity.add(acceleration.scl(dt));

        setPosition(position);
        setAcceleration(acceleration);

        bounds.setPosition(position.x, position.y);

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
        return PolygonHelper.collidePolygon(bounds, otherBounds);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
        this.bounds.setPosition(position.x, position.y);
    }

    public float getX() {
        return position.x;
    }

    public void setX(float v) {
        position.x = v;
        bounds.setPosition(position.x, position.y);
    }

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

    /**
     * Changes the speed
     * @param v the speed offset
     */
    public void accelerate(float v) {
        if (velocity.x == 0 && velocity.y == 0) velocity.set(0, 1); // Prevent this from having no effect
        velocity.scl(v);
    }

    public float getMaxVelocity() {
        return maxVelocity;
    }

    public void setMaxVelocity(float maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public void rotate(float v) {
        angle += v * Utils.delta();
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

    public Polygon getBounds() {
        return bounds;
    }

    public void setBounds(Polygon bounds) {
        this.bounds = bounds;
    }

    public void toggleBoundsDebug() {
        boundsDebug = ! boundsDebug;
    }

}
