package me.benjozork.onyx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Version;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import me.benjozork.onyx.backend.handlers.FTFGeneratorCache;
import me.benjozork.onyx.backend.handlers.KeymapHandler;
import me.benjozork.onyx.backend.handlers.PolygonHandler;
import me.benjozork.onyx.backend.handlers.RessourceHandler;
import me.benjozork.onyx.backend.handlers.ScreenHandler;
import me.benjozork.onyx.backend.models.TextComponent;
import me.benjozork.onyx.config.Configs;
import me.benjozork.onyx.config.ProjectConfig;
import me.benjozork.onyx.console.Console;
import me.benjozork.onyx.logger.Log;
import me.benjozork.onyx.utils.Utils;

/**
 * The main Onyx client
 *
 * @version 0.6.0-alpha
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

    private static TextComponent debugComponent;
    private static boolean debug = false;

    @Override
    public void create() {

        // Load config

        projectConfig = Configs.loadCached(ProjectConfig.class);

        // Force pre-caching of default font

        me.benjozork.onyx.backend.handlers.FTFGeneratorCache.getFTFGenerator(projectConfig.default_font);

        // Print debug info

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

        RessourceHandler.setWorldCamera(worldCam);
        RessourceHandler.setGuiCamera(guiCam);

        RessourceHandler.setRenderer(new ShapeRenderer());
        RessourceHandler.getRenderer().setAutoShapeType(true);

        RessourceHandler.setBatch(new SpriteBatch());

        RessourceHandler.setFont(new BitmapFont());

        // Init console

        Console.init();

        // Init PolygonLoader

        PolygonHandler.init();

        // Init KeymapLoader

        KeymapHandler.init();

        // Setup Initial Screen

        Console.dispatchCommand("screen " + projectConfig.initial_screen);

        // Setup info component

        debugComponent = new TextComponent("");

    }


    public void update() {

        // Open console on key press

        if (Gdx.input.isKeyJustPressed(KeymapHandler.getKeyCode("game_toggle_debug"))) {
            toggleDebug();
        }

        // Update console

        Console.update();

        // Update camera

        RessourceHandler.getWorldCamera().update();

        if (ScreenHandler.getCurrentScreen() != getScreen())
            setScreen(ScreenHandler.getCurrentScreen());

        // Update debug info component

        debugComponent.setText(DebugInfo.get());

    }

    @Override
    public void render() {
        update();

        // Clear screen

        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render frame

        RessourceHandler.setIsRendering(true);
        getScreen().render(Utils.delta());
        RessourceHandler.setIsRendering(false);

        // Draw console

        RessourceHandler.getRenderer().setProjectionMatrix(RessourceHandler.getGuiCamera().combined);
        RessourceHandler.getBatch().setProjectionMatrix(RessourceHandler.getGuiCamera().combined);

        DebugInfo.frameTimes.add(Utils.delta());

        RessourceHandler.setIsRendering(true);
        if (debug)
            Console.draw();
        else debugComponent.draw(RessourceHandler.getBatch(), 20, Gdx.graphics.getHeight() - 10);
        RessourceHandler.setIsRendering(false);

        RessourceHandler.getRenderer().setProjectionMatrix(RessourceHandler.getWorldCamera().combined);
        RessourceHandler.getBatch().setProjectionMatrix(RessourceHandler.getWorldCamera().combined);

    }

    @Override
    public void dispose() {

        // Dispose active screen

        ScreenHandler.getCurrentScreen().dispose();

        // Dispose various resources

        RessourceHandler.dispose();
        FTFGeneratorCache.dispose();
        PolygonHandler.dispose();

    }

    private static void toggleDebug() {
        debug = ! debug;
    }

    @Override
    public void resize(int width, int height) {

        // Update cameras

        OrthographicCamera worldCam = RessourceHandler.getWorldCamera();
        worldCam.setToOrtho(false);
        worldCam.viewportWidth = Gdx.graphics.getWidth();
        worldCam.viewportHeight = Gdx.graphics.getHeight();

        // ? fixme
        RessourceHandler.getBatch().setProjectionMatrix(worldCam.combined);

        OrthographicCamera guiCam = RessourceHandler.getGuiCamera();
        guiCam.setToOrtho(false);
        guiCam.viewportWidth = Gdx.graphics.getWidth();
        guiCam.viewportHeight = Gdx.graphics.getHeight();

    }

}
