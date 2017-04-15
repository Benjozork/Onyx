package me.benjozork.onyx.game.entity;

import me.benjozork.onyx.game.GameScreenManager;
import me.benjozork.onyx.object.Drawable;

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