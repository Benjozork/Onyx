package me.benjozork.onyx.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.GameManager;
import me.benjozork.onyx.ui.container.UIContainer;

/**
 * @author Benjozork
 */
public class UIImage extends UIElement {

    private Sprite sprite;

    public UIImage(float x, float y, Texture texture, UIContainer parent) {
        super(x, y, parent);
        this.sprite = new Sprite(texture);
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {
        sprite.draw(GameManager.getBatch());
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean click(Vector2 localPosition) {
        return false;
    }

    /**
     * @return the underlying {@link Sprite} object used to draw the image
     */
    public Sprite getSprite() {
        return sprite;
    }

}