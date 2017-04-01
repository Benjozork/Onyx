package me.benjozork.onyx.internal;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.JsonValue;

/**
 * Used to convert rectangles to polygons and perform operations of rectangle on the polygon
 * @author RishiRaj22
 */
public class PolygonHelper {

    private static Polygon p1 = new Polygon();
    private static Polygon p2 = new Polygon();
    private static Polygon p3 = new Polygon();

    /**
     * WARNING: EXPENSIVE OPERATION USE ONLY IN INITIALISATION STEPS
     * This method is used to create a new rectangular polygon
     * @param rectangle The rectangle to be converted to polygon
     * @return the created polygon
     */
    public static Polygon getPolygon(Rectangle rectangle) {
        return getPolygon(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }

    /**
     * WARNING: EXPENSIVE OPERATION USE ONLY IN INITIALISATION STEPS
     * This method is used to create a new rectangular polygon
     * @param x      the x coordinate of the polygon
     * @param y      the y coordinate of the polygon
     * @param width  the width of the polygon
     * @param height the height of the polygon
     * @return the created polygon
     */
    public static Polygon getPolygon(float x, float y, float width, float height) {
        float vals[] = {0, 0, width, 0, width, height, 0, height};
        Polygon p = new Polygon();
        p.setOrigin(width / 2, height / 2);
        p.setScale(1, 1);
        p.setVertices(vals);
        p.setPosition(x, y);
        return p;
    }

    /**
     * Used to set the x co-ordinate of polygon
     * @param p polygon whose x co-ordinate is to be set
     * @param x new value for x co-ordinate of polygon
     */
    public static void setX(Polygon p, float x) {
        p.setPosition(x, p.getY());
    }

    /**
     * Used to set the y co-ordinate of polygon
     * @param p polygon whose y co-ordinate is to be set
     * @param y new value for y co-ordinate of polygon
     */
    public static void setY(Polygon p, float y) {
        p.setPosition(p.getX(), y);
    }

    /**
     * Used to set the width of a rectangular polygon
     * @param p     polygon whose width is to be set
     * @param width new width for the polygon
     */
    public static void setWidth(Polygon p, float width) {
        float vals[] = p.getVertices();
        vals[2] = vals[0] + width;
        vals[4] = vals[0] + width;
        p.setVertices(vals);
    }

    /**
     * Used to set the height of a rectangular polygon
     * @param p      polygon whose width is to be set
     * @param height new height for the polygonial
     */
    public static void setHeight(Polygon p, float height) {
        float vals[] = p.getVertices();
        vals[5] = vals[1] + height;
        vals[7] = vals[1] + height;
        p.setVertices(vals);
    }

    /**
     * Used to set the dimensions of the rectangular polygon
     * @param p      polygon whose dimensions are to be set
     * @param width  the width to be used
     * @param height the height to be used
     */
    public static void setDimensions(Polygon p, float width, float height) {
        float vals[] = p.getVertices();
        vals[5] = vals[1] + height;
        vals[7] = vals[1] + height;
        vals[2] = vals[0] + width;
        vals[4] = vals[0] + width;
        p.setVertices(vals);
    }

    /**
     * Check the collsion of two polygons.
     * Use this method instead of intersector as this uses the transformed vertices
     * of the polygon to check for collision instead of directly using these vertices.
     * @param pol1 first polygon used in the check
     * @param pol2 second polygon used in the check
     * @return whether the two collide
     */
    public static boolean polygonCollide(Polygon pol1, Polygon pol2) {
        p1.setVertices(pol1.getTransformedVertices());
        p2.setVertices(pol2.getTransformedVertices());

        return Intersector.intersectPolygons(p1, p2, p3);

    }

    /**
     * WARNING: EXPENSIVE OPERATION USE ONLY IN INITIALISATION STEPS
     * Get a polygon from a JSON Value(JSON array of JSON objects having x,y co-ordinates)
     * @param value  Array denoting the pol
     * @param width
     * @param height
     * @return
     */
    public static Polygon loadPolygon(JsonValue value, float width, float height) {
        JsonValue.JsonIterator iter = value.iterator();
        int len = 0, i = 0;
        for (JsonValue val : iter) {
            len += 2;
        }
        float[] vertices = new float[len];
        for (JsonValue val : iter) {
            vertices[i] = val.getFloat("x") * width;
            vertices[i + 1] = val.getFloat("y") * height;
            i += 2;
        }
        return new Polygon(vertices);
    }
}
