package me.benjozork.onyx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Version;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.internal.Utils;
import me.benjozork.onyx.screen.GameScreen;

public class OnyxGame extends Game {

    @Override
    public void create() {
        Gdx.app.log("[onyx/info] ", "Onyx 0.0.1 starting");
        Gdx.app.log("[onyx/debug] ", "Current libGDX version is " + Version.VERSION);
        Gdx.app.log("[onyx/debug] ", "Current backend is " + Gdx.app.getType() + "/" + System.getProperty("os.name"));
        Gdx.app.log("[onyx/debug] ", "Current JRE version is " + System.getProperty("java.version"));

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

        // Setup Initial Screen
        GameManager.setCurrentScreen(new GameScreen());
        setScreen(GameManager.getCurrentScreen());
    }

    @Override
    public void dispose() {
        // Dispose active screen
        GameManager.getCurrentScreen().dispose();

        // Dispose graphics resources
        GameManager.getBatch().dispose();
        GameManager.getShapeRenderer().dispose();
    }

    @Override
    public void render() {
        // Update cameras
        OrthographicCamera worldCamera = GameManager.getWorldCamera();
        worldCamera.update();

        // Clear screen
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render frame
        getScreen().render(Utils.delta());
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
}
