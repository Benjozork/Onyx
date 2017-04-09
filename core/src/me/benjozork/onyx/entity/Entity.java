package me.benjozork.onyx.entity;

import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.internal.ScreenManager;
import me.benjozork.onyx.object.Drawable;
import me.benjozork.onyx.screen.GameScreen;
import me.benjozork.onyx.screen.GameScreenManager;

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