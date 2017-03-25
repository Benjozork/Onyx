package me.benjozork.onyx.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.internal.Utils;

/**
 * Created by Benjozork on 2017-03-16.
 */
public abstract class Drawable {

    protected Vector2 position;
    protected Vector2 velocity = new Vector2(0, 0);
    protected Vector2 acceleration = new Vector2(0, 0);

    protected float maxVelocity = 100f;
    private float speed;
    protected float angle;

    protected Rectangle bounds;

    private boolean boundsDebug = false;

    public Drawable(int x, int y) {
        this.position = new Vector2(x, y);
    }

    public Drawable(Vector2 position) {
        this.position = position;
    }

    /**
     * Internally upadte the Drawable
     *
     * @param dt The delta time
     */
    public void update(float dt) {

        if (boundsDebug) {
            GameManager.getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
            GameManager.getShapeRenderer().rect(bounds.x, bounds.y, bounds.width, bounds.height);
            GameManager.getShapeRenderer().end();
        }

        bounds.setPosition(position);
        // double angle = Math.atan2((Gdx.graphics.getWidth() - Gdx.input.getX()) - loc.getX(), (Gdx.graphics.getHeight() - Gdx.input.getY()) - loc.getY());
        // set velocity/direction vector with angle, if angle isnt zero ( to prevent garbage data in velocity vector )
        if (angle != 0) {
            velocity.setAngle(angle);
        }

        //velocity.add(acceleration);
        // add velocity scaled to speed and timestep to position
        position.add(velocity.nor().scl(speed).scl(Utils.delta()));
    }

    /**
     * Initialize the Drawable
     */
    public abstract void init();

    /**
     * Update the Drawable
     */
    public abstract void update();

    /**
     * Draw the Drawable
     */
    public abstract void draw();

    /**
     * The bounding box
     *
     * @return The bounding box
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Set the bounding box
     *
     * @param bounds The bounding box to be used
     */
    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    /**
     * Check if the Drawable collides with a rectangle
     *
     * @param otherBounds The rectangle used to check
     * @return If the Drawable collides with otherBounds
     */
    public boolean collidesWith(Rectangle otherBounds) {
        return bounds.overlaps(otherBounds);
    }

    /**
     * The Drawable's position
     *
     * @return The Drawable's position
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Set the position
     *
     * @param position The position to be used
     */
    public void setPosition(Vector2 position) {
        this.position = position;
        this.bounds.setPosition(position);
    }

    /**
     * The X coordinate value
     *
     * @return The X coordinate value
     */
    public float getX() {
        return position.x;
    }

    /**
     * Set the X coordinate value
     *
     * @param v The X coordinate value to be used
     */
    public void setX(float v) {
        position.x = v;
        bounds.x = v;
    }

    /**
     * The Y coordinate value
     *
     * @return The Y coordinate value
     */
    public float getY() {
        return position.y;
    }

    /**
     * Set the Y coordinate value
     *
     * @param v The Y coordinate value to be used
     */
    public void setY(float v) {
        position.y = v;
        bounds.y = v;
    }

    /**
     * Move the Drawable
     *
     * @param dx The X coordinate offset
     * @param dy The Y coordinate offset
     */
    public void move(float dx, float dy) {
        // here, we move the position and the bounding box
        position.x += dx * Utils.delta();
        bounds.x += dx * Utils.delta();
        position.y += dy * Utils.delta();
        bounds.y += dy * Utils.delta();
    }

    /**
     * The velocity
     *
     * @return The velocity
     */
    public Vector2 getVelocity() {
        return velocity;
    }

    /**
     * Set the velocity
     *
     * @param velocity The velocity to be used
     */
    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    /**
     * The acceleration
     *
     * @return The acceleration
     */
    public Vector2 getAcceleration() {
        return acceleration;
    }

    /**
     * Set the velocity
     *
     * @param acceleration The acceleration to be used
     */
    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }

    /**
     * Change the speed
     *
     * @param v The speed offset
     */
    public void accelerate(float v) {
        speed += v;
    }

    /**
     * The max velocity
     *
     * @return The max velocity
     */
    public float getMaxVelocity() {
        return maxVelocity;
    }

    /**
     * Set the max velocity
     *
     * @param maxVelocity The max velocity to be used
     */
    public void setMaxVelocity(float maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    /**
     * Rotate the Drawable
     *
     * @param v The rotation offset
     */
    public void rotate(float v) {
        angle += v * Utils.delta();
    }

    /**
     * The speed
     *
     * @return The speed
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Set the speed
     *
     * @param speed The speed to be used
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * Check if the mouse hovers above the hitbox
     *
     * @return If the mouse hovers above the hitbox
     */
    public boolean hovering() {
        Vector2 mouse = Utils.unprojectWorld(Gdx.input.getX(), Gdx.input.getY());

        if (mouse.x > getBounds().getX()
                && mouse.x < (getBounds().getX() + getBounds().getWidth())
                && mouse.y > getBounds().getY()
                && mouse.y < (getBounds().getY() + getBounds().getHeight())) {
            return true;
        } else return false;
    }

    /**
     * Toggle the hitbox debug rendering
     */
    public void toggleBoundsDebug() {
        boundsDebug = !boundsDebug;
    }

    /**
     * Dispose of the Drawable
     */
    public abstract void dispose();

}
