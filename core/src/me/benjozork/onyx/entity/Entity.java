package me.benjozork.onyx.entity;

import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.internal.ScreenManager;
import me.benjozork.onyx.object.Drawable;
import me.benjozork.onyx.screen.GameScreen;

/**
 * @author Benjozork
 */
public abstract class Entity extends Drawable {

    public Entity(Vector2 position) {
        super(position);
        if (! (ScreenManager.getCurrentScreen() instanceof GameScreen)) {
            dispose();
            throw new IllegalStateException("entities cannot be created outside of a GameScreen");
        }
    }

    public abstract void init();

    public abstract void update();

    public abstract void draw();

    public abstract void dispose();

}