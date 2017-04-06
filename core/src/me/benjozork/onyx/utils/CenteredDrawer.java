package me.benjozork.onyx.utils;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Benjozork
 */
public class CenteredDrawer {

    public static Vector2 get(CenteredDrawingType type, float x, float y, float w, float h) {
        switch (type) {
            case CENTERED_AT_POINT:
                float xc_cap = x - w / 2;
                float yc_cap = y + h / 2;
                return new Vector2(xc_cap, yc_cap);
            case CENTERED_HORIZONTALLY_AT_POINT:
                float xc_chap = x - w / 2;
                return new Vector2(xc_chap, y);
            case CENTERED_VERTICALLY_AT_POINT:
                float yc_cvap = y + h / 2;
                return new Vector2(x, yc_cvap);
            default:
                throw new IllegalArgumentException("CenteredDrawingType invalid for standard centering method");
        }
    }

    public static Vector2 getContained(CenteredDrawingType type, float x, float y, float w, float h, float cw, float ch) {
        switch (type) {
            case CENTERED_IN_CONTAINER:
                float xc_cic = x + cw / 2 - w / 2;
                float yc_cic = y + ch / 2 + h / 2;
                return new Vector2(xc_cic, yc_cic);
            case CENTERED_HORIZONTALLY_IN_CONTAINER:
                float xc_chic = x + cw / 2 - w / 2;
                return new Vector2(xc_chic, y);
            case CENTERED_VERTICALLY_IN_CONTAINER:
                float yc_cvic = y + ch / 2 + h / 2;
                return new Vector2(x, yc_cvic);
            default:
                throw new IllegalArgumentException("CenteredDrawingType invalid for contained centering method");
        }
    }


    public enum CenteredDrawingType {
        CENTERED_AT_POINT,
        CENTERED_HORIZONTALLY_AT_POINT,
        CENTERED_VERTICALLY_AT_POINT,
        CENTERED_IN_CONTAINER,
        CENTERED_HORIZONTALLY_IN_CONTAINER,
        CENTERED_VERTICALLY_IN_CONTAINER,
    }

}
