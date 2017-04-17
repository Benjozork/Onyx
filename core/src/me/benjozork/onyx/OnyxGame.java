package me.benjozork.onyx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Version;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import me.benjozork.onyx.config.Configs;
import me.benjozork.onyx.config.ProjectConfig;
import me.benjozork.onyx.console.Console;
import me.benjozork.onyx.console.ConsoleCommand;
import me.benjozork.onyx.game.GameScreenManager;
import me.benjozork.onyx.logger.Log;
import me.benjozork.onyx.utils.Utils;

/**
 * The main Onyx client
 *
 * @version 0.5.0-alpha
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

        // Load config

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
        GameManager.getRenderer().setAutoShapeType(true);

        GameManager.setBatch(new SpriteBatch());

        GameManager.setFont(new BitmapFont());

        // Init console

        Console.init();

        // Init PolygonLoader

        PolygonLoader.init();

        // Init KeymapLoader

        KeymapLoader.init();

        // Setup Initial Screen

        Console.dispatchCommand("screen " + projectConfig.initial_screen);
    }


    public void update() {

        if (Gdx.input.isKeyJustPressed(KeymapLoader.getKeyCode("game_toggle_debug"))) {
            Console.dispatchCommand(new ConsoleCommand("screen"));
            toggleDebug();
        }

        // Update console

        Console.update();

        // Update camera

        GameManager.getWorldCamera().update();

        if (ScreenManager.getCurrentScreen() != getScreen())
            setScreen(ScreenManager.getCurrentScreen());

        // Process input

        OnyxInputProcessor.getCurrentProcessor().processInput();

    }


    @Override
    public void render() {
        update();

        // Clear screen

        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render frame

        GameManager.setIsRendering(true);
        getScreen().render(Utils.delta());
        GameManager.setIsRendering(false);

        // Draw console

        if (debug)
            Console.draw();
    }

    @Override
    public void dispose() {

        // Dispose active screen

        ScreenManager.getCurrentScreen().dispose();

        // Dispose various resources

        GameManager.dispose();
        FTFGeneratorCache.dispose();
        PolygonLoader.dispose();
        if (GameScreenManager.exists()) GameScreenManager.dispose();

    }

    private static void toggleDebug() {
        debug = ! debug;
    }

    @Override
    public void resize(int width, int height) {

        // Update cameras

        OrthographicCamera worldCam = GameManager.getWorldCamera();
        worldCam.setToOrtho(false);
        worldCam.viewportWidth = Gdx.graphics.getWidth();
        worldCam.viewportHeight = Gdx.graphics.getHeight();

        OrthographicCamera guiCam = GameManager.getGuiCamera();
        guiCam.setToOrtho(false);
        guiCam.viewportWidth = Gdx.graphics.getWidth();
        guiCam.viewportHeight = Gdx.graphics.getHeight();

    }

}
