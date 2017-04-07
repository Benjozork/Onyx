package me.benjozork.onyx.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.internal.PolygonHelper;
import me.benjozork.onyx.utils.TextComponent;

/**
 * @author Benjozork
 */
public class UILabel extends UIElement {

    private TextComponent component;

    public UILabel(float x, float y, TextComponent component) {
        super(x, y);
        bounds = PolygonHelper.getPolygon(getX(), getY(), getWidth(), getHeight());
        this.component = component;
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {
        component.updateLayout();
        PolygonHelper.setDimensions(bounds, component.getLayout().width, component.getLayout().height);
    }

    @Override
    public void draw() {
        component.drawCenteredInContainer(GameManager.getBatch(), getX(), getY(), component.getLayout().width, component.getLayout().height);
    }

    @Override
    public boolean click(Vector2 localPosition) {
        return false;
    }

    @Override
    public void dispose() {

    }
}
