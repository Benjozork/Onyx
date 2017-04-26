package me.benjozork.onyx.ui.container;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import me.benjozork.onyx.GameManager;
import me.benjozork.onyx.utils.Utils;

/**
 * @author Benjozork
 */
public class UIPane extends UIContainer {

    private Color color = Utils.rgba(200, 200, 200, 150);

    public UIPane(float x, float y, float w, float h, UIContainer parent) {
        super(x, y, w, h, parent);
    }

    @Override
    public void draw() {
        GameManager.setIsShapeRendering(true);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        GameManager.getRenderer().set(ShapeRenderer.ShapeType.Filled);
        GameManager.getRenderer().setColor(color);
        GameManager.getRenderer().rect(getAbsolutePosition().x, getAbsolutePosition().y, getWidth(), getHeight());
        GameManager.setIsShapeRendering(false);
        Gdx.gl.glDisable(GL20.GL_BLEND);
        super.draw();
    }

}