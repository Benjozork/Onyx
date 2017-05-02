package me.benjozork.onyx.ui.container;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import me.benjozork.onyx.GameManager;
import me.benjozork.onyx.object.TextComponent;
import me.benjozork.onyx.utils.Utils;

/**
 * @author Benjozork
 */
public class UIWindow extends UIPane {

    private TextComponent text;

    private ShapeRenderer renderer = GameManager.getRenderer();

    private final int WINDOW_BAR_HEIGHT = 25;
    private final int WINDOW_BAR_TEXT_HORIZONTAL_OFFSET = 15;

    private final Color WINDOW_BACKGROUND_COLOR = Utils.rgba(170, 170, 170, 1220);

    public UIWindow(float x, float y, float w, float h, TextComponent text, UIContainer parent) {
        super(x, y, w, h, parent);
        this.text = text;
    }

    @Override
    public void draw() {
        super.draw();
        GameManager.setIsShapeRendering(true);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(WINDOW_BACKGROUND_COLOR);
        renderer.rect(getAbsoluteX(), getAbsoluteY() + getHeight(), getWidth(), WINDOW_BAR_HEIGHT);
        GameManager.setIsShapeRendering(false);
        Gdx.gl.glDisable(GL20.GL_BLEND);
        text.drawCenteredInContainer(GameManager.getBatch(), getAbsoluteX() + WINDOW_BAR_TEXT_HORIZONTAL_OFFSET, getAbsoluteY() + getHeight(), getWidth(), WINDOW_BAR_HEIGHT, false, true);
    }

}