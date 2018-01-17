package me.benjozork.onyx.game.entity;

import me.benjozork.onyx.backend.models.Drawable;

/**
 * @author Benjozork
 */
public abstract class Entity extends Drawable {

    public float width, height;

    public Entity(float x, float y) {
        super(x, y);
    }

}