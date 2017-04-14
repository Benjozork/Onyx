package me.benjozork.onyx.ui;

import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.object.TextComponent;
import me.benjozork.onyx.utils.PolygonHelper;

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
