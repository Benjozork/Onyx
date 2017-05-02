package me.benjozork.onyx.utils;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

import me.benjozork.onyx.logger.Log;

/**
 * Used to convert {@link Rectangle}s to {@link Polygon}s and perform operations on the polygon.<br/>
 * WARNING: The width/height methods in this class ONLY work on rectangular polygons.
 * @author Rishi Raj
 */
public class PolygonHelper {

    private static Polygon p1 = new Polygon();
    private static Polygon p2 = new Polygon();
    private static Polygon p3 = new Polygon();

    private static final Log log = Log.create("PolygonHelper");
    private static boolean debug = false;

    /**
     * WARNING: EXPENSIVE OPERATION USE ONLY IN INITIALISATION STEPS
     * This method is used to create a new rectangular polygon
     *
     * @param rectangle The rectangle to be converted to polygon
     * @return the created polygon
     */
    public static Polygon getPolygon(Rectangle rectangle) {
        return getPolygon(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }

    /**
     * WARNING: EXPENSIVE OPERATION USE ONLY IN INITIALISATION STEPS
     * This method is used to create a new rectangular polygon
     *
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
     *
     * @param p polygon whose x co-ordinate is to be set
     * @param x new value for x co-ordinate of polygon
     */
    public static void setX(Polygon p, float x) {
        p.setPosition(x, p.getY());
    }

    /**
     * Used to set the y co-ordinate of polygon
     *
     * @param p polygon whose y co-ordinate is to be set
     * @param y new value for y co-ordinate of polygon
     */
    public static void setY(Polygon p, float y) {
        p.setPosition(p.getX(), y);
    }

    /**
     * Used to set the width of a rectangular polygon
     *
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
     *
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
     *
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
     * Checks whether two polygons collide
     *
     * @param p1 the first polygon
     * @param p2 the second polygon
     * @return the result
     */
    public static boolean collidePolygon(Polygon p1, Polygon p2) {
        float[] v1 = p1.getTransformedVertices();
        float[] v2 = p2.getTransformedVertices();
        float p1x1, p1y1, p1x2, p1y2;
        float p2x1, p2y1, p2x2, p2y2;
        int i, j;

        // To check if any point of one polygon lies inside another polygon

        for(i = 0; i < v1.length-1 ;i+=2)
        {
            if((p2.contains(v1[i],v1[i+1])))
                return true;
        }

        //To check if any of the line segments of polygons intersect at any place

        for (i = 0; i < v1.length; i += 2) {
            p1x1 = v1[i];
            p1y1 = v1[i + 1];
            p1x2 = v1[(i + 2) % v1.length]; //To return back to 0 when i+2 > len
            p1y2 = v1[(i + 3) % v1.length]; //To return back to 0 when i+3 > len
            for (j = 0; j < v2.length; j += 2) {
                p2x1 = v2[j];
                p2y1 = v2[j + 1];
                p2x2 = v2[(j + 2) % v2.length]; //To return back to 0 when j+2 > len
                p2y2 = v2[(j + 3) % v2.length]; //To return back to 0 when j+3 > len
                if (collisionAtPoints(p1x1, p1y1, p1x2, p1y2, p2x1, p2y1, p2x2, p2y2)) {
                    if (debug) log.print("Collision at %d of p1 and %d of p2", i / 2 + 1, j / 2 + 1);
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean collisionAtPoints(float f1, float f2, float f3, float f4
            , float f5, float f6, float f7, float f8) {
        return Intersector.intersectSegments(f1, f2, f3, f4, f5, f6, f7, f8, null);
    }

    public static boolean toggleDebug() {
        return debug = ! debug;
    }
}
