package me.benjozork.onyx.internal.console;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.utils.Logger;
import me.benjozork.onyx.utils.Utils;

/**
 * @author Benjozork
 */
public class Console {

    private static String lines = "";
    private static GlyphLayout layout = new GlyphLayout();
    private static Rectangle textBox = new Rectangle();

    private static boolean isTextBoxFocused = false;

    public static void init() {
        textBox.set(10, Gdx.graphics.getHeight() - 600 + 10, 580, 25);
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
        BitmapFont font = new BitmapFont(); // Unoptimmized
        ShapeRenderer shapeRenderer = GameManager.getShapeRenderer(); // Unoptimized

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
        GameManager.getShapeRenderer().setProjectionMatrix(GameManager.getGuiCamera().combined);
        GameManager.getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
        GameManager.getShapeRenderer().setColor(0.1f, 0.1f, 0.1f, 0.6f);
        GameManager.getShapeRenderer().rect(0, Gdx.graphics.getHeight() - 600, 600, 600);
        GameManager.getShapeRenderer().setColor(0.1f, 0.1f, 0.1f, 0.6f);
        GameManager.getShapeRenderer().rect(10, Gdx.graphics.getHeight() - 600 + 10, 580, 25);
        GameManager.getShapeRenderer().end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        batch.setProjectionMatrix(GameManager.getGuiCamera().combined);

        if (! lines.equals("")) {
            layout.setText(font, lines);
            font.getData().markupEnabled = true;
            if (batch.isDrawing()) batch.end();
            if (! batch.isDrawing()) batch.begin();
            font.draw(batch, lines, 20, Gdx.graphics.getHeight() - 600 + layout.height + 45);
            if (batch.isDrawing()) batch.end();
        }
    }

}
