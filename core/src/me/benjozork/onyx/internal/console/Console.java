package me.benjozork.onyx.internal.console;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.screen.GameScreen;
import me.benjozork.onyx.utils.Logger;
import me.benjozork.onyx.utils.Utils;

/**
 * @author Benjozork
 */
public class Console {

    private static String lines = "", prevLines = "";
    private static GlyphLayout layout = new GlyphLayout();
    private static BitmapFont font;
    private static ShapeRenderer renderer;
    private static Rectangle textBox = new Rectangle();

    private static boolean isTextBoxFocused = false;

    public static void init() {
        textBox.set(10, Gdx.graphics.getHeight() - 600 + 10, 580, 25);
        font = new BitmapFont();
        renderer = GameManager.getShapeRenderer();
    }

    public static void update() {
        if (Gdx.input.justTouched()) {
            if (textBox.contains(Utils.unprojectGui(Gdx.input.getX(), Gdx.input.getY()))) {
                isTextBoxFocused = true;
                Logger.log("box");
            }
        }

        if (isTextBoxFocused) {

        }
    }

    /**
     * Prints a string to the console on a new line
     * @param x the object to pdrint
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

    private static void newLine() {
        lines += "[]\n"; // Resets markup and breaks lines
    }

    public static void draw(SpriteBatch batch) {
        /*
        This code contains a lot of SpriteBatch#begin()/end().
        This is NECESSARY so no OpenGL conflicts happen.
        Please do not remove those lines.
        Thanks,
                    - Ben
         */

        if (batch.isDrawing()) batch.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        renderer.setProjectionMatrix(GameManager.getGuiCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(0.1f, 0.1f, 0.1f, 0.6f);
        renderer.rect(0, Gdx.graphics.getHeight() - 600, 600, 600);
        renderer.setColor(0.1f, 0.1f, 0.1f, 0.6f);
        renderer.rect(10, Gdx.graphics.getHeight() - 600 + 10, 580, 25);
        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        batch.setProjectionMatrix(GameManager.getGuiCamera().combined);

        if (batch.isDrawing()) batch.end();
        if (! batch.isDrawing()) batch.begin();
        font.getData().markupEnabled = true;

        // Draw debug info
        if (GameManager.getCurrentScreen() instanceof GameScreen) {
            font.draw (
                batch,
                    "[#FF00FF]"
                    + Gdx.graphics.getFramesPerSecond()
                    + "  []fps,  [#FF00FF]"
                    + ((GameScreen) GameManager.getCurrentScreen()).getRegisteredEntities().size()
                    + "  []entities",
                20, Gdx.graphics.getHeight() - 10
            );
            font.draw (
                batch,
                    "current screen:  [#FF00FF]"
                    + GameManager.getCurrentScreen().getClass().getName().replace("me.benjozork.onyx.screen.", "")
                    +  "[]",
                20, Gdx.graphics.getHeight() - 30
            );
        }
        else {
            font.draw (
                    batch,
                    Gdx.graphics.getFramesPerSecond()
                            + " fps ",
                    20, Gdx.graphics.getHeight() - 10
            );
        }


        if (! lines.equals("")) {
            if (! prevLines.equals(lines)) layout.setText(font, lines);
            prevLines = lines;
            font.draw(batch, lines, 20, Gdx.graphics.getHeight() - 600 + layout.height + 45);
            if (batch.isDrawing()) batch.end();
        }
    }

}
