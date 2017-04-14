package me.benjozork.onyx.entity;

import me.benjozork.onyx.object.Drawable;
import me.benjozork.onyx.game.GameScreenManager;

/**
 * @author Benjozork
 */
public abstract class Entity extends Drawable {

    public Entity(float x, float y) {
        super(x, y);
        if (! GameScreenManager.exists()) {
            dispose();
            throw new IllegalStateException("entities cannot be created outside of a GameScreen");
        }
    }

    public abstract void init();

    public abstract void update();

    public abstract void draw();

    public abstract void dispose();

}