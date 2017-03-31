package me.benjozork.onyx.internal;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

/**
 * Used to convert rectangles to polygons and perform operations of rectangle on the polygon
 * Created by Raj on 28-03-2017.
 */

public class PolygonHelper {
    private static Polygon p1 = new Polygon();
    private static Polygon p2 = new Polygon();
    private static Polygon p3 = new Polygon();
    /**
     * WARNING: EXPENSIVE OPERATION USE ONLY IN INITIALISATION STEPS
     * This method is used to create a new rectangular polygon
     *
     * @param x      the x coordinate of the polygon
     * @param y      the y coordinate of the polygon
     * @param width  the width of the polygon
     * @param height the height of the polygon
     * @return
     */
    public static Polygon getPolygon(float x, float y, float width, float height) {
        float vals[] = {0, 0, width, 0, width, height, 0, height};
        Polygon p = new Polygon();
        p.setOrigin(width/2,height/2);
        p.setScale(1,1);
        p.setVertices(vals);
        p.setPosition(x,y);
        return p;
    }

    /**
     * WARNING: EXPENSIVE OPERATION USE ONLY IN INITIALISATION STEPS
     * This method is used to create a new rectangular polygon
     *
     * @param rectangle The rectangle to be converted to polygon
     * @return
     */
    public static Polygon getPolygon(Rectangle rectangle) {
        return getPolygon(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }

    /**
     * Used to set the x co-ordinate of polygon
     * @param p polygon whose x co-ordinate is to be set
     * @param x new value for x co-ordinate of polygon
     */
    public static void setX(Polygon p, float x)
    {
        p.setPosition(x,p.getY());
    }

    /**
     * Used to set the y co-ordinate of polygon
     * @param p polygon whose y co-ordinate is to be set
     * @param y new value for y co-ordinate of polygon
     */
    public static void setY(Polygon p, float y) {
        p.setPosition(p.getX(),y);
    }
    /**
     * Used to set the width of a rectangular polygon
     * @param p polygon whose width is to be set
     * @param width new width for the polygon
     */
    public static void setWidth(Polygon p,float width) {
        float vals[] = p.getVertices();
        vals[2] = vals[0] + width;
        vals[4] = vals[0] + width;
        p.setVertices(vals);
    }

    /**
     * Used to set the height of a rectangular polygon
     * @param p polygon whose width is to be set
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
     * @param p polygon whose dimensions are to be set
     * @param width new width for the polygonial
     * @param height new height for the polygonial
     */
    public static void setDimensions(Polygon p, float width, float height) {
        float vals[] = p.getVertices();
        vals[5] = vals[1] + height;
        vals[7] = vals[1] + height;
        vals[2] = vals[0] + width;
        vals[4] = vals[0] + width;
        p.setVertices(vals);
    }
    public static boolean polygonCollide(Polygon pol1, Polygon pol2) {
        p1.setVertices(pol1.getTransformedVertices());
        p2.setVertices(pol2.getTransformedVertices());

        return Intersector.intersectPolygons(p1,p2,p3);

    }
}
