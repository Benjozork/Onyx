package me.benjozork.onyx.object;

/**
 * Created by Benjozork on 2017-03-04.
 */
public class Location {

    private float x = 0;
    private float y = 0;

    public Location(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String toString() {
        return "x:" + x + " y:" + y;
    }
}
