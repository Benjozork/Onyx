package me.benjozork.onyx.internal;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Holds {@link SpriteBatch}, {@link OrthographicCamera}, {@link ShapeRenderer} and {@link BitmapFont} objects.
 * @author Benjozork
 */
public class GameManager {

    /**
     * The camera instance that is used when rendering world objects
     */
    private static OrthographicCamera worldCamera;

    /**
     * The camera instance that is used when rendering gui objects
     */
    private static OrthographicCamera guiCamera;

    private static ShapeRenderer renderer;

    private static SpriteBatch batch;

    private static BitmapFont font;

    private static boolean shapeCache = false, spriteCache = false;

    /**
     * Returns the camera instance that is used when rendering world objects
     * @return the world camera
     */
    public static OrthographicCamera getWorldCamera() {
        return worldCamera;
    }

    /**
     * Sets the camera instance to be used when rendering world objects
     * @param worldCamera the camera instance to be used
     */
    public static void setWorldCamera(OrthographicCamera worldCamera) {
        GameManager.worldCamera = worldCamera;
    }

    /**
     * Returns the camera instance that is used when rendering gui objects
     * @return the gui camera
     */
    public static OrthographicCamera getGuiCamera() {
        return guiCamera;
    }

    /**
     * Set the camera instance to be used when rendering gui objects
     * @param guiCamera the camera instance to be used
     */
    public static void setGuiCamera(OrthographicCamera guiCamera) {
        GameManager.guiCamera = guiCamera;
    }

    /**
     * Returns the {@link ShapeRenderer}
     * @return the {@link ShapeRenderer}
     */
    public static ShapeRenderer getRenderer() {
        return renderer;
    }

    /**
     * Sets the ShapeRenderer
     * @param renderer the ShapeRenderer to be used
     */
    public static void setRenderer(ShapeRenderer renderer) {
        GameManager.renderer = renderer;
    }

    /**
     * Returns the {@link SpriteBatch}
     * @return the {@link SpriteBatch}
     */
    public static SpriteBatch getBatch() {
        return batch;
    }

    /**
     * Sets the {@link SpriteBatch}
     * @param batch the {@link SpriteBatch} to be used
     */
    public static void setBatch(SpriteBatch batch) {
        GameManager.batch = batch;
    }

    /**
     * Sets whether the {@link SpriteBatch} instance is rendering or not.<br/>
     * Use when a {@link ShapeRenderer} needs to render shapes, without causing<br/>
     * OpenGL conflicts.
     *
     * @param v the desired state
     */
    public static void setIsRendering(boolean v) {
        if (v) {
            if (renderer.isDrawing() && ! batch.isDrawing()) {
                renderer.begin();
                shapeCache = true;
            }
            if (! batch.isDrawing()) batch.begin();
        } else {
            if (batch.isDrawing()) batch.end();
            if (shapeCache && ! renderer.isDrawing()) {
                renderer.begin(ShapeRenderer.ShapeType.Line);
                shapeCache = false;
            }
        }
    }

    /**
     * Sets whether the {@link ShapeRenderer} instance is rendering or not.<br/>
     * Use when a {@link SpriteBatch} needs to render pixmaps, without causing<br/>
     * OpenGL conflicts.
     *
     * @param v the desired state
     */
    public static void setIsShapeRendering(boolean v) {
        if (v) {
            if (batch.isDrawing() && ! renderer.isDrawing()) {
                batch.end();
                spriteCache = true;
            }
            if (! renderer.isDrawing()) renderer.begin(ShapeRenderer.ShapeType.Line);
        } else {
            if (renderer.isDrawing()) renderer.end();
            if (spriteCache && ! batch.isDrawing()) {
                batch.begin();
                spriteCache = false;
            }
        }
    }

    public static BitmapFont getFont() {
        return font;
    }

    public static void setFont(BitmapFont font) {
        GameManager.font = font;
    }

    public static void dispose() {
        batch.dispose();
        renderer.dispose();
        font.dispose();
    }

}
