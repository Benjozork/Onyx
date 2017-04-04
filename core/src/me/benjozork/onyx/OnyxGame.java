package me.benjozork.onyx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Version;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import me.benjozork.onyx.config.Configs;
import me.benjozork.onyx.config.ProjectConfig;
import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.internal.ScreenManager;
import me.benjozork.onyx.internal.console.Console;
import me.benjozork.onyx.internal.console.ConsoleCommand;
import me.benjozork.onyx.logger.Log;
import me.benjozork.onyx.screen.MenuScreen;
import me.benjozork.onyx.utils.Utils;

/**
 * The main Onyx client
 *
 * @version 0.3.0-alpha
 *
 * Written with <3 by :
 *
 * @author Benjozork
 * @author angelickite
 * @author RishiRaj22
 *
 * To whoever this may read this code, we wish you all happiness in the world !
 *      ;)
 */
public class OnyxGame extends Game {

    private static final Log log = Log.create("Onyx");
    public static ProjectConfig projectConfig;

    private static boolean debug = false;

    @Override
    public void create() {

        projectConfig = Configs.loadRequire("config/project.json", ProjectConfig.class);

        log.print("Onyx %s starting", projectConfig.version);
        log.print("Current libGDX version is %s", Version.VERSION);
        log.print("Current backend is %s/%s", Gdx.app.getType(), System.getProperty("os.name"));
        log.print("Current JRE version is %s", System.getProperty("java.version"));

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
        ScreenManager.setCurrentScreen(new MenuScreen());

    }

    @Override
    public void dispose() {

        // Dispose active screen

        ScreenManager.getCurrentScreen().dispose();

        // Dispose graphics resources

        GameManager.getBatch().dispose();
        GameManager.getShapeRenderer().dispose();
        try { // fixme
            GameManager.getPlayer().dispose();
        } catch (IllegalStateException ignored) {
        } catch (NullPointerException ignored) {
        }

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

        if (debug)
            Console.draw(GameManager.getBatch());
    }

    public void update() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
            Console.dispatchCommand(new ConsoleCommand("screen"));
            toggleDebug();
        }

        // Update console
        Console.update();

        // Update cameras
        OrthographicCamera worldCamera = GameManager.getWorldCamera();
        worldCamera.update();

        if (ScreenManager.getCurrentScreen() != getScreen())
            setScreen(ScreenManager.getCurrentScreen());
    }

    private static void toggleDebug() {
        debug = ! debug;
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
