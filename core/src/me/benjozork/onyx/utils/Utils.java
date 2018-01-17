package me.benjozork.onyx.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import me.benjozork.onyx.backend.handlers.RessourceHandler;


/**
 * Provides a few useful utilities to ease development
 * @author Benjozork
 * @author angelickite
 */
public class Utils {

    /*
     * Color instances used to markup console text.
     * WARN: Warning text. Non-failing problems.
     * ERROR: Error text. Failing problems.
     * FATAL: Critical problem that interrupts the game.
     * DEBUG: Debugging text. Technical information used for debugging.
     */
    public static final Color WARN = rgb(255, 255, 100);
    public static final Color ERROR = rgb(255, 100, 100);
    public static final Color FATAL = rgb(255, 65, 65);
    public static final Color DEBUG = rgb(255, 100, 255);

    /*
     * Cached instances used for calculations or returning results.
     * Note: This potentially leads to bugs if the same temporary instance is used
     * multiple times throughout a method without care. If more cached instances
     * are needed make sure to create a new one here.
     */
    private static final Vector2 v2 = new Vector2();
    private static final Vector3 v3 = new Vector3();

    private static final Random random = new Random();

    /**
     * Returns the current frame-time
     */
    public static float delta() {
        return Gdx.graphics.getDeltaTime();
    }

    /**
     * This method takes the width of an object and gives a corresponding x axis<br />
     * coordinate in order to center said object.
     *
     * @param w the width of the object
     *
     * @return a centered position
     */
    @Deprecated
    public static float getCenterPos(float w) {
        return (Gdx.graphics.getWidth() / 2) + w / 2;
    }

    /**
     * Translates a point from screen coordinates to world coordinates.<br>
     * This method returns an internally cached vector instance, do not store this instance!
     *
     * @param vec the position of the point
     *
     * @return the point position in world coordinates.
     */
    public static Vector2 unprojectWorld(Vector2 vec) {
        return unprojectWorld(vec.x, vec.y);
    }

    /**
     * Translates a point from screen coordinates to world coordinates.<br>
     * This method returns an internally cached vector instance, do not store this instance!
     *
     * @param x the x position of the point
     * @param y the y position of the point
     *
     * @return the point position in world coordinates.
     */
    public static Vector2 unprojectWorld(float x, float y) {
        v3.set(x, y, 0);
        OrthographicCamera camera = RessourceHandler.getWorldCamera();
        camera.unproject(v3);
        v2.set(v3.x, v3.y);
        return v2;
    }

    /**
     * Translate a point from screen coordinates to world coordinates.<br>
     * This method returns an internally cached vector instance, do not store this instance!
     *
     * @param vec the position of the point
     *
     * @return the point position in world coordinates.
     */
    public static Vector2 unprojectGui(Vector2 vec) {
        return unprojectGui(vec.x, vec.y);
    }

    /**
     * Translate a point from screen coordinates to gui coordinates.<br>
     * This method returns an internally cached vector instance, do not store this instance!
     *
     * @param x the x position of the point
     * @param y the y position of the point
     *
     * @return the point position in world coordinates.
     */
    public static Vector2 unprojectGui(float x, float y) {
        v3.set(x, y, 0);
        OrthographicCamera camera = RessourceHandler.getGuiCamera();
        camera.unproject(v3);
        v2.set(v3.x, v3.y);
        return v2;
    }

    /**
     * Returns a {@link Color} object from three integer RGB values.
     *
     * @param r the red value
     * @param g the green value
     * @param b the blue value
     *
     * @return the Color object
     */
    public static Color rgb(int r, int g, int b) {
        float rf = r / 255.0f;
        float gf = g / 255.0f;
        float bf = b / 255.0f;
        return new Color(rf, gf, bf, 1);
    }

    /**
     * Returns a {@link Color} object from three integer RGBA values.
     *
     * @param r the red value
     * @param g the green value
     * @param b the blue value
     * @param a the alpha value
     *
     * @return the Color object
     */
    public static Color rgba(int r, int g, int b, int a) {
        float rf = r / 255.0f;
        float gf = g / 255.0f;
        float bf = b / 255.0f;
        float af = a / 255.0f;
        return new Color(rf, gf, bf, af);
    }

    /**
     * Formats a String list with commas and endpoints.
     * @param strings the strings to format
     * @return the formatted list
     */
    public String list(String[] strings) {
        StringBuilder ret = new StringBuilder(new String());
        for (int i = 0; i < strings.length; i++) {
            if (i < strings.length - 1) ret.append(strings[i] + ", ");
            else ret.append(strings[i] + ".");
        }
        return ret.toString();
    }

    /**
     * Transforms a group of Strings into an Array of Strings
     * @param strings the strings to use
     * @return the array
     */
    public Array<String> array(String... strings) {
        Array<String> ret = new Array<String>();
        for (String s : strings) {
            ret.add(s);
        }
        return ret;
    }


    /**
     * Returns a random value between two floats
     * @param min the lower bound
     * @param max the upper bound
     * @return the random value generated
     */
    public static float randomBetween(float min, float max) {
        return random.nextFloat() * (max - min) + min;
    }

    /**
     * Removes package names in instances where {@link Class#getSimpleName()} cannot be called, i.e. reflection.
     * @param s the unsanitized class name
     * @return the sanitized class name
     */
    public static String sanitizeClassName(String s) {
        return s.substring(s.lastIndexOf(".") + 1);
    }

    /**
     * Returns a string that can be used in the formatting of {@link BitmapFont}
     * @param c the color to be used
     * @return the converted string
     */
    public static String toMarkupColor(Color c) {
        return "[#" + c.toString().toUpperCase() + "]";
    }

}