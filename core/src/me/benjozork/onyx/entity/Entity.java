package me.benjozork.onyx.entity;

import com.badlogic.gdx.math.Rectangle;

import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.internal.GameUtils;
import me.benjozork.onyx.object.Location;

/**
 * Created by Benjozork on 2017-03-03.
 */
public abstract class Entity {

    public Entity(float x, float y) {
        loc.setX(x);
        loc.setY(y);
    }

    public Entity(Location loc) {
        this.loc.setX(loc.getX());
        this.loc.setY(loc.getY());
    }

    protected Rectangle boundingRectangle = new Rectangle();
    private boolean collisionEnabled = true;
    protected Location loc = new Location(0, 0);
    private int dx, dy;

    public abstract void init();

    public abstract void draw();

    public void dispose() {
        GameManager.getRegisteredEntities().remove(this);
    }

    public float getX() {
        return loc.getX();
    }

    public void setX(float x) {
        this.loc.setX(x);
        this.boundingRectangle.setX(x);
    }

    public float getY() {
        return loc.getY();
    }

    public void setY(float y) {
        this.loc.setY(y);
        this.boundingRectangle.setY(y);
    }

    public void move(float vx, float vy) {
        this.loc.setX(loc.getX() + vx * GameUtils.getDelta());
        this.boundingRectangle.setX(boundingRectangle.getX() + vx);
        this.loc.setY(loc.getY() + vy * GameUtils.getDelta());
        this.boundingRectangle.setY(boundingRectangle.getY() + vy);
    }

    public void setPos(float x, float y) {
        this.loc.setX(x);
        this.boundingRectangle.setX(x);
        this.loc.setY(y);
        this.boundingRectangle.setY(y);
    }

    public void setPos(Location loc) {
        this.loc.setX(loc.getX());
        this.boundingRectangle.setX(loc.getX());
        this.loc.setY(loc.getY());
        this.boundingRectangle.setX(loc.getY());
    }

    public Location getPos() {
        return loc;
    }

    public int getDx() {
        return this.dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return this.dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public Rectangle getBoundingRectangle() {
        return this.boundingRectangle;
    }

    public void setBoundingRectangle(Rectangle boundingRectangle) {
        this.boundingRectangle = boundingRectangle;
    }

    public boolean collidesWith(Entity e) {
        if (!collisionEnabled) return false;
        if (e == this) return false;
        return e.getBoundingRectangle().overlaps(this.boundingRectangle);
    }

    public void setCollisionEnabled(boolean v) {
        this.collisionEnabled = v;
    }

    public boolean getCollisionEnabled() {
        return collisionEnabled;
    }
}