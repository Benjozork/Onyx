package me.benjozork.onyx.internal.console;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.internal.ScreenManager;
import me.benjozork.onyx.screen.GameScreen;
import me.benjozork.onyx.screen.GameScreenManager;
import me.benjozork.onyx.utils.Utils;

/**
 * Manages the debugging console and the command processing.<br/>
 * In order for a non-Onyx command to process correctly, a {@link CommandProcessor}<br/>
 * needs to be assigned to it using {@link Console#registerCommands(CommandProcessor, Array<String>)}.<br/>
 *
 * @author Benjozork
 */
public class Console {

    private static String lines = "", prevLines = "";

    private static GlyphLayout layout = new GlyphLayout();
    private static BitmapFont font = GameManager.getFont();

    private static Rectangle textBox = new Rectangle();

    private static SpriteBatch batch;
    private static ShapeRenderer renderer;

    private static HashMap<CommandProcessor, Array<String>> cmdProcessorList = new HashMap<CommandProcessor, Array<String>>();

    private static boolean isTextBoxFocused = false;

    /**
     * Prepares the console. Should only be called once in the code, before any call<br/>
     * of {@link Console#update()} or {@link Console#draw()}.
     */
    public static void init() {
        textBox.set(10, Gdx.graphics.getHeight() - 600 + 10, 580, 25);
        font = new BitmapFont();

        batch = GameManager.getBatch();
        renderer = GameManager.getShapeRenderer();

        /*
        Here, we add all the commands from the base game.
        ALWAYS add the commands here, or else the commands won't be processed !
        Thanks,
                    - Ben
         */

        OnyxCommandProcessor ocpInstance = new OnyxCommandProcessor();
        Array<String> ocpCommands = new Array<String>();
        ocpCommands.add("screen");
        ocpCommands.add("debug");
        ocpCommands.add("exit");
        ocpCommands.add("echo");
        registerCommands(ocpInstance, ocpCommands);
    }

    /**
     * Updates the console. Should be called every frame before {@link Console#draw()}.
     */
    public static void update() {
        if (Gdx.input.justTouched()) {
            if (textBox.contains(Utils.unprojectGui(Gdx.input.getX(), Gdx.input.getY()))) {
                isTextBoxFocused = true;
            }
        }

        if (isTextBoxFocused) {

        }
    }

    /**
     * Draw the console. Should be called every frame after {@link Console#update()}.
     */
    public static void draw() {

        /*
        This code contains a lot of SpriteBatch#begin()/end() via GameManager#setIsRendering().
        This is NECESSARY so no OpenGL conflicts happen.
        Please do not remove those lines.
        Thanks,
                    - Ben
         */

        GameManager.setIsShapeRendering(true);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        renderer.setAutoShapeType(true);
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.setProjectionMatrix(GameManager.getGuiCamera().combined);
        renderer.setColor(0.1f, 0.1f, 0.1f, 0.6f);
        renderer.rect(0, Gdx.graphics.getHeight() - 600, 600, 600);
        renderer.setColor(0.1f, 0.1f, 0.1f, 0.6f);
        renderer.rect(10, Gdx.graphics.getHeight() - 600 + 10, 580, 25);
        GameManager.setIsShapeRendering(false);
        Gdx.gl.glDisable(GL20.GL_BLEND);
        GameManager.setIsRendering(true);

        batch.setProjectionMatrix(GameManager.getGuiCamera().combined);

        font.getData().markupEnabled = true;

        // Draw debug info

        if (ScreenManager.getCurrentScreen() instanceof GameScreen) {

            // Draw FPS and entity count

            font.draw (
                batch,
                "[#FF00FF]"
                    + Gdx.graphics.getFramesPerSecond()
                    + "  []fps,  [#FF00FF]"
                    + GameScreenManager.getEntities().size()
                    + "  []entities",
                20, Gdx.graphics.getHeight() - 10
            );

            // Draw current screen

            font.draw (
                batch,
                "current screen:  [#FF00FF]"
                    + ScreenManager.getCurrentScreen().getClass().getSimpleName()
                    + "[]",
                20, Gdx.graphics.getHeight() - 30
            );
        } else {

            // Draw FPS

            font.draw (
                batch,
                Gdx.graphics.getFramesPerSecond()
                    + " fps ",
                20, Gdx.graphics.getHeight() - 10
            );

            // Draw current screen

            font.draw (
                batch,
                "current screen:  [#FF00FF]"
                    + ScreenManager.getCurrentScreen().getClass().getSimpleName()
                    + "[]",
                20, Gdx.graphics.getHeight() - 30
            );
        }

        if (! lines.equals("")) {
            if (! prevLines.equals(lines)) layout.setText(font, lines);
            prevLines = lines;
            font.draw(batch, lines, 20, Gdx.graphics.getHeight() - 600 + layout.height + 45);
        }

        GameManager.setIsRendering(false);

    }

    /**
     * Prints a string to the console on a new line
     * @param x the object to print
     */
    public static void println(Object x) {
        newLine();
        lines += x.toString();
    }

    /**
     * Prints a string to the console
     * @param x the object to print
     */
    public static void print(Object x) {
        lines += x.toString();
    }

    /**
     * Prints a formatted string to the console on a new line
     * @param x the object to print
     * @param f the objects to format the string with
     */
    public static void printfln(Object x, Object... f) {
        newLine();
        lines += String.format(String.valueOf(x), f);
    }

    /**
     * Prints a formatted string to the console
     * @param x the object to print
     * @param f the objects to format the string with
     */
    public static void printf(Object x, Object... f) {
        lines += String.format(String.valueOf(x), f);
    }

    private static void newLine() {
        lines += "[]\n"; // Resets markup and breaks lines
    }


    /**
     * Registers commands so they can be listened to by a {@link CommandProcessor}
     * @param commandProcessor the {@link CommandProcessor} which will listen to the given commands
     * @param cmds the commands to listen to
     */
    public static void registerCommands(CommandProcessor commandProcessor, Array<String> cmds) {
        cmdProcessorList.put(commandProcessor, cmds);
    }

    /**
     * Sends a {@link ConsoleCommand} to be processed
     * @param cmd the {@link ConsoleCommand} to process
     */
    public static void dispatchCommand(ConsoleCommand cmd) {
        for (CommandProcessor cp : cmdProcessorList.keySet()) {
            if (cmdProcessorList.get(cp).contains(cmd.getCommand(), false)) {
                cp.onCommand(cmd);
            }
        }
    }

    /**
     * Sends a {@link ConsoleCommand} to be processed
     * @param cmdstr the {@link ConsoleCommand} to process
     */
    public static void dispatchCommand(String cmdstr) {
        ConsoleCommand cmd = new ConsoleCommand(cmdstr);
        for (CommandProcessor cp : cmdProcessorList.keySet()) {
            if (cmdProcessorList.get(cp).contains(cmd.getCommand(), false)) {
                cp.onCommand(cmd);
            }
        }
    }

}