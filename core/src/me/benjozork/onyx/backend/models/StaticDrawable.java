package me.benjozork.onyx.backend.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import me.benjozork.onyx.backend.handlers.RessourceHandler;
import me.benjozork.onyx.utils.PolygonHelper;
import me.benjozork.onyx.utils.Utils;

/**
 * Describes an object that is to be drawn on the screen
 * @author Benjozork
 */
public abstract class StaticDrawable implements Disposable {

    protected Vector2 position;
    protected float angle;

    protected Polygon bounds;

    private static boolean debug = false;

    public StaticDrawable(float x, float y) {
        this.position = new Vector2(x, y);
    }

    /**
     * Internally updates the StaticDrawable
     * @param dt The delta time
     */
    public void update(float dt) {
        bounds.setPosition(position.x, position.y);

        // Draw bounds polygon if debug enabled

        if (debug) {
            RessourceHandler.getRenderer().setProjectionMatrix(RessourceHandler.getWorldCamera().combined);
            RessourceHandler.getRenderer().setColor(Color.WHITE);
            RessourceHandler.setIsShapeRendering(true);
            RessourceHandler.getRenderer().polygon(this.getBounds().getTransformedVertices());
            RessourceHandler.setIsShapeRendering(false);
        }
    }

    // Abstract methods

    public abstract void init();

    public abstract void update();

    public abstract void draw();

    public abstract void dispose();

    /**
     * Checks if the StaticDrawable collides with a specified {@link Polygon}
     * @param otherBounds the polygon used to perform the check
     * @return whether the StaticDrawable collides with otherBounds
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

    public void rotate(float v) {
        angle += v * Utils.delta();
    }

    /**
     * Checks if the mouse hovers above the hitbox
     */
    public boolean hovering() {
        Vector2 mouse = Utils.unprojectWorld(Gdx.input.getX(), Gdx.input.getY());
        return getBounds().contains(mouse);
    }

    public Polygon getBounds() {
        return bounds;
    }

    public void setBounds(Polygon bounds) {
        this.bounds = bounds;
    }

    public static void toggleDebug() {
        debug = ! debug;
    }

}
