package me.benjozork.onyx.console;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

import me.benjozork.onyx.GameManager;
import me.benjozork.onyx.ScreenManager;
import me.benjozork.onyx.game.GameScreen;
import me.benjozork.onyx.game.GameScreenManager;
import me.benjozork.onyx.utils.Utils;

/**
 * Manages the debugging console and the command processing.<br/>
 * In order for a non-Onyx command to process correctly, a {@link CommandProcessor}<br/>
 * needs to be assigned to it using {@link Console#registerCommands(CommandProcessor, Array<String>)}.<br/>
 *
 * @author Benjozork
 */
public class Console {

    private static Array<String> lines = new Array<String>(), prevLines = new Array<String>();

    private static GlyphLayout layout = new GlyphLayout();
    private static BitmapFont font = GameManager.getFont();

    private static Rectangle textBox = new Rectangle();

    private static SpriteBatch batch;
    private static ShapeRenderer renderer;

    private static HashMap<CommandProcessor, Array<String>> cmdProcessorList = new HashMap<CommandProcessor, Array<String>>();

    private static boolean isTextBoxFocused = false;

    private static final float LINE_OFFSET = 8;
    private static final float CONSOLE_VERTICAL_TEXT_OFFSET = 45;
    private static final float CONSOLE_HORIZONTAL_TEXT_OFFSET = 20;

    private static final float CONSOLE_HEIGHT = 600;
    private static final float CONSOLE_WIDTH = 600;

    private static final float CONSOLE_INNER_VERTICAL_OFFSET = 10;
    private static final float CONSOLE_INNER_HORIZONTAL_OFFSET = 10;

    private static final float CONSOLE_TEXTBOX_HEIGHT = 25;

    private static Color color = Color.WHITE;

    private static boolean resetColor = true;

    /**
     * Prepares the console. Should only be called once in the code, before any call<br/>
     * of {@link Console#update()} or {@link Console#draw()}.
     */
    public static void init() {

        textBox.set(10, Gdx.graphics.getHeight() - 600 + 10, 580, 25);
        font = new BitmapFont();

        batch = GameManager.getBatch();
        renderer = GameManager.getRenderer();

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
        renderer.rect(0, Gdx.graphics.getHeight() - CONSOLE_HEIGHT, CONSOLE_WIDTH, CONSOLE_HEIGHT);
        renderer.setColor(0.1f, 0.1f, 0.1f, 0.6f);
        renderer.rect(CONSOLE_INNER_HORIZONTAL_OFFSET, Gdx.graphics.getHeight() - CONSOLE_HEIGHT + CONSOLE_INNER_VERTICAL_OFFSET, CONSOLE_WIDTH - CONSOLE_INNER_HORIZONTAL_OFFSET * 2, CONSOLE_TEXTBOX_HEIGHT);
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
                    + GameScreenManager.getEntities().size
                    + "  []entities",
                400, Gdx.graphics.getHeight() - CONSOLE_INNER_VERTICAL_OFFSET
            );

            // Draw current screen

            font.draw (
                batch,
                "current screen:  [#FF00FF]"
                    + ScreenManager.getCurrentScreen().getClass().getSimpleName()
                    + "[]",
                20, Gdx.graphics.getHeight() - CONSOLE_INNER_VERTICAL_OFFSET
            );
        } else {

            // Draw FPS

            font.draw (
                batch,
                Gdx.graphics.getFramesPerSecond()
                    + " fps ",
                400, Gdx.graphics.getHeight() - 10
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

        if (lines.size != 0) {
            int i = lines.size;
            float currentdy = 0;
            while (i --> 0) {
                layout.setText(font, lines.get(i));
                if (! (lines.get(i).isEmpty())) currentdy += layout.height + LINE_OFFSET;
                font.draw(batch, lines.get(i), CONSOLE_HORIZONTAL_TEXT_OFFSET, Gdx.graphics.getHeight() - CONSOLE_HEIGHT + CONSOLE_VERTICAL_TEXT_OFFSET + currentdy, CONSOLE_WIDTH, Align.left, true);
            }
        }

        GameManager.setIsRendering(false);

    }

    /**
     * Prints a string to the console
     * @param x the object to print
     */
    public static void print(Object x) {
        if (x.toString().startsWith("->")) lines.add(x.toString().replace("->", Utils.toMarkupColor(color)));
        else lines.add(Utils.toMarkupColor(color) + x.toString());
        if (resetColor) Console.color(Color.WHITE);
    }

    /**
     * Prints a formatted string to the console
     * @param x the object to print
     * @param f the objects to format the string with
     */
    public static void printf(Object x, Object... f) {
        print(String.format(x.toString(), f));
    }

    /**
     * Prints a string to the console on a new line
     * @param x the object to print
     */
    public static void println(Object x) {
        print(x);
        newLine();
    }

    private static void newLine() {
        lines.add("[]\n"); // Resets markup and breaks lines
    }

     public static void color(Color color) {
        Console.color = color;
     }

    public static Color getColor() {
        return color;
    }

    public static void colorBegin(Color color) {
        color(color);
        resetColor = false;
    }

    public static void colorEnd() {
        color(Color.WHITE);
        resetColor = true;
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