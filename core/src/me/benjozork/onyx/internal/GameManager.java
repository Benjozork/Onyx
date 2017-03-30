package me.benjozork.onyx.internal;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import me.benjozork.onyx.entity.EntityPlayer;
import me.benjozork.onyx.screen.GameScreen;

/**
 * Created by Benjozork on 2017-03-19.
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

    private static Screen currentScreen;

    private static ShapeRenderer renderer;

    private static SpriteBatch batch;

    private static EntityPlayer player;

    /**
     * The camera instance that is used when rendering world objects
     *
     * @return the world camera
     */
    public static OrthographicCamera getWorldCamera() {
        return worldCamera;
    }

    /**
     * Set the camera instance to be used when rendering world objects
     *
     * @param worldCamera the camera instance to be used
     */
    public static void setWorldCamera(OrthographicCamera worldCamera) {
        GameManager.worldCamera = worldCamera;
    }

    /**
     * The camera instance that is used when rendering gui objects
     *
     * @return the gui camera
     */
    public static OrthographicCamera getGuiCamera() {
        return guiCamera;
    }

    /**
     * Set the camera instance to be used when rendering gui objects
     *
     * @param guiCamera the camera instance to be used
     */
    public static void setGuiCamera(OrthographicCamera guiCamera) {
        GameManager.guiCamera = guiCamera;
    }

    public static Screen getCurrentScreen() {
        return currentScreen;
    }

    public static void setCurrentScreen(Screen currentScreen) {
        Logger.log("Changed screen to " + currentScreen.getClass().getName().replace("me.benjozork.onyx.screen.", ""));
        GameManager.currentScreen = currentScreen;
    }

    /**
     * The ShapeRenderer
     *
     * @return the ShapeRenderer
     */
    public static ShapeRenderer getShapeRenderer() {
        return renderer;
    }

    /**
     * Set the ShapeRenderer
     *
     * @param renderer the ShapeRenderer to be used
     */
    public static void setRenderer(ShapeRenderer renderer) {
        GameManager.renderer = renderer;
    }

    /**
     * The SpriteBatch
     *
     * @return the SpriteBatch
     */
    public static SpriteBatch getBatch() {
        return batch;
    }

    /**
     * Set the SpriteBatch
     *
     * @param batch the SpriteBatch to be used
     */
    public static void setBatch(SpriteBatch batch) {
        GameManager.batch = batch;
    }

    public static void setIsRendering(boolean v) {
        if (v) if (! batch.isDrawing()) batch.begin();
        if (! v) if (batch.isDrawing()) batch.end();
    }

    /**
     * The player entity used for GameScreen logic
     * @throws IllegalStateException
     * @return the player
     */
    public static EntityPlayer getPlayer() {
        if (currentScreen instanceof GameScreen) return player;
        else throw new IllegalStateException("player does not exist in this screen");
    }

    /**
     * Sets the player instance
     * @param player the player instance to be used
     */
    public static void setPlayer(EntityPlayer player) {
        GameManager.player = player;
    }
}
