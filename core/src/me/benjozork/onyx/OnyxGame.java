package me.benjozork.onyx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Version;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.internal.console.Console;
import me.benjozork.onyx.screen.GameScreen;
import me.benjozork.onyx.screen.MenuScreen;
import me.benjozork.onyx.utils.Logger;
import me.benjozork.onyx.utils.Utils;

public class OnyxGame extends Game {

    public static final String VERSION = "0.2.0";

    private static boolean debug = false;

    @Override
    public void create() {
        Logger.log("Onyx " + VERSION + " starting");
        Logger.log("[#FF00FF]Current libGDX version is " + Version.VERSION);
        Logger.log("[#FF00FF]Current backend is " + Gdx.app.getType() + "/" + System.getProperty("os.name"));
        Logger.log("[#FF00FF]Current JRE version is " + System.getProperty("java.version"));

        // Setup cameras
        OrthographicCamera worldCam = new OrthographicCamera();
        worldCam.setToOrtho(false);
        worldCam.viewportWidth = Gdx.graphics.getWidth();
        worldCam.viewportHeight = Gdx.graphics.getHeight();

        OrthographicCamera guiCam = new OrthographicCamera();
        guiCam.setToOrtho(false);
        guiCam.viewportWidth = Gdx.graphics.getWidth();
        guiCam.viewportHeight = Gdx.graphics.getHeight();

        // Setup GameManager
        GameManager.setWorldCamera(worldCam);
        GameManager.setGuiCamera(guiCam);
        GameManager.setRenderer(new ShapeRenderer());
        GameManager.setBatch(new SpriteBatch());

        // Init console
        Console.init();

        // Setup Initial Screen
        GameManager.setCurrentScreen(new GameScreen());
    }

    @Override
    public void dispose() {
        // Dispose active screen
        GameManager.getCurrentScreen().dispose();

        // Dispose graphics resources
        GameManager.getBatch().dispose();
        GameManager.getShapeRenderer().dispose();
    }

    public void update() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
            toggleDebug();
        }

        // Update console
        Console.update();

        // Update cameras
        OrthographicCamera worldCamera = GameManager.getWorldCamera();
        worldCamera.update();

        if (GameManager.getCurrentScreen() != getScreen()) setScreen(GameManager.getCurrentScreen());
    }

    @Override
    public void render() {
        update();

        // Clear screen
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render frame
        getScreen().render(Utils.delta());
        // Draw console
        if (debug) Console.draw(GameManager.getBatch());
    }

    @Override
    public void resize(int width, int height) {
        // Update cameras
        OrthographicCamera worldCamera = GameManager.getWorldCamera();
        worldCamera.viewportWidth = width;
        worldCamera.viewportHeight = height;
        OrthographicCamera guiCamera = GameManager.getGuiCamera();
        guiCamera.viewportWidth = width;
        guiCamera.viewportHeight = height;
    }

    private static void toggleDebug() {
        debug = !debug;
    }

}
