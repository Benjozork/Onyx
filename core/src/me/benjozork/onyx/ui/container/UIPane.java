package me.benjozork.onyx.ui.container;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import me.benjozork.onyx.GameManager;
import me.benjozork.onyx.ui.UIElement;
import me.benjozork.onyx.utils.Utils;

/**
 * Displays and organizes {@link UIElement} objects.
 * @author Benjozork
 */
public class UIPane extends UIContainer {

    /**
     * The background color of the {@link UIPane}
     */
    public Color backgroundColor = Utils.rgba(110, 110, 110, 200);

    public UIPane(float x, float y, float w, float h, UIContainer parent) {
        super(x, y, w, h, parent);
    }

    @Override
    public void draw() {
        GameManager.setIsShapeRendering(true);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        GameManager.getRenderer().set(ShapeRenderer.ShapeType.Filled);
        GameManager.getRenderer().setColor(backgroundColor);
        GameManager.getRenderer().rect(getAbsolutePosition().x, getAbsolutePosition().y, getWidth(), getHeight());
        GameManager.setIsShapeRendering(false);
        Gdx.gl.glDisable(GL20.GL_BLEND);
        super.draw();
    }

}