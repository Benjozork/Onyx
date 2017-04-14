package me.benjozork.onyx.utils;

import com.badlogic.gdx.math.Vector2;

/**
 * Allows to get centered position vectors following various centering cases.
 * @author Benjozork
 */
public class CenteredDrawer {

    private static boolean bitmapDrawingMode = false;

    /**
     * Returns a {@link Vector2} containing a centered position
     *
     * @param type the centering method that is wished to be used
     * @param x the x position of the desired center of the object
     * @param y the y position of the desired center of the object
     * @param w the width of the object
     * @param h the height of the object
     *
     * @return a centered position vector
     */
    public static Vector2 get(CenteredDrawingType type, float x, float y, float w, float h) {
        switch (type) {
            case CENTERED_AT_POINT:
                float xc_cap = x - w / 2;
                float yc_cap = bitmapDrawingMode ? y + h / 2 : y - h / 2;
                return new Vector2(xc_cap, yc_cap);
            case CENTERED_HORIZONTALLY_AT_POINT:
                float xc_chap = x - w / 2;
                return new Vector2(xc_chap, y);
            case CENTERED_VERTICALLY_AT_POINT:
                float yc_cvap = bitmapDrawingMode ? y + h / 2 : y - h / 2;
                return new Vector2(x, yc_cvap);
            default:
                throw new IllegalArgumentException("CenteredDrawingType invalid for standard centering method");
        }
    }

    /**
     * Returns a {@link Vector2} containing a centered position in a container
     *
     * @param type the centering method that is wished to be used
     * @param x the x position of the container
     * @param y the y position of the container
     * @param w the width of the object
     * @param h the height of the object
     * @param cw the width of the container
     * @param ch the height of the container
     *
     * @return a centered position vector
     */
    public static Vector2 getContained(CenteredDrawingType type, float x, float y, float w, float h, float cw, float ch) {
        switch (type) {
            case CENTERED_IN_CONTAINER:
                return get(CenteredDrawingType.CENTERED_AT_POINT, x + cw / 2, y + ch / 2, w, h);
            case CENTERED_HORIZONTALLY_IN_CONTAINER:
                return get(CenteredDrawingType.CENTERED_HORIZONTALLY_AT_POINT, x + cw / 2, y + ch / 2, w, h);
            case CENTERED_VERTICALLY_IN_CONTAINER:
                return get(CenteredDrawingType.CENTERED_VERTICALLY_AT_POINT, x + cw / 2, y + ch / 2, w, h);
            default:
                throw new IllegalArgumentException("CenteredDrawingType invalid for contained centering method");
        }
    }

    /**
     * Allows to center {@link com.badlogic.gdx.graphics.g2d.BitmapFont} objects.<br/>
     * WARNING: ALWAYS set use {@link CenteredDrawer#switchToPixel()} after using this!
     */
    public static void switchToBitmap() {
        bitmapDrawingMode = true;
    }

    /**
     * Allows to center {@link com.badlogic.gdx.graphics.Pixmap} objects.<br/>
     * WARNING: This method should ALWAYS be called after centering with {@link CenteredDrawer#switchToBitmap()}!
     */
    public static void switchToPixel() {
        bitmapDrawingMode = false;
    }

    /**
     * Defines various centering methods to be used with {@link CenteredDrawer}.
     */
    public enum CenteredDrawingType {
        CENTERED_AT_POINT,
        CENTERED_HORIZONTALLY_AT_POINT,
        CENTERED_VERTICALLY_AT_POINT,
        CENTERED_IN_CONTAINER,
        CENTERED_HORIZONTALLY_IN_CONTAINER,
        CENTERED_VERTICALLY_IN_CONTAINER,
    }

}
