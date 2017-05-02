package me.benjozork.onyx.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.GameManager;
import me.benjozork.onyx.object.TextComponent;
import me.benjozork.onyx.ui.object.ActionEvent;
import me.benjozork.onyx.utils.PolygonHelper;

/**
 * @author Benjozork
 */
public class UICheckbox extends UIElement {

    // Checkbox textures

    private final Texture CHECKBOX_TEXTURE = new Texture("ui/checkbox/checkbox_0.png");
    private final Texture TICKED_CHECKBOX_TEXTURE = new Texture("ui/checkbox/checkbox_2.png");
    private final Texture HOVERED_CHECKBOX_TEXTURE = new Texture("ui/checkbox/checkbox_1.png");
    private final Texture HOVERED_TICKED_CHECKBOX_TEXTURE = new Texture("ui/checkbox/checkbox_3.png");

    private Texture currentTexture = CHECKBOX_TEXTURE;

    private TextComponent component;


    private Vector2 dimension = new Vector2();

    private boolean checked = false;

    private float colorTimer;
    private float maxColorTimer = 0.1f;

    public UICheckbox(float x, float y, float width, float height, TextComponent component) {
        super(x, y);
        bounds = PolygonHelper.getPolygon(x, y, width, height);
        setWidth(width);
        setHeight(height);
        this.component = component;
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {
        PolygonHelper.setDimensions(bounds, getWidth() + component.getLayout().width + 10, getHeight());

        if (hovering()) {
            currentTexture = (checked ? HOVERED_TICKED_CHECKBOX_TEXTURE : HOVERED_CHECKBOX_TEXTURE);
        } else {
            currentTexture = (checked ? TICKED_CHECKBOX_TEXTURE : CHECKBOX_TEXTURE);
        }
    }

    @Override
    public void draw() {
        GameManager.getBatch().draw(currentTexture, getX(), getY(), getWidth(), getHeight());
        component.drawCenteredInContainer(GameManager.getBatch(), getX() + getWidth() + 10, getY(), getWidth() + component.getLayout().width + 50, getHeight(), false, true);
    }

    @Override
    public boolean click(Vector2 localPosition) {
        toggle();
        return true;
    }

    public void toggle() {
        triggerEvent(ActionEvent.VALUE_CHANGED);
        currentTexture = ! checked ? TICKED_CHECKBOX_TEXTURE : CHECKBOX_TEXTURE;
        checked = ! checked;
    }

    @Override
    public void dispose() {
        // Dispose of textures
        CHECKBOX_TEXTURE.dispose();
        TICKED_CHECKBOX_TEXTURE.dispose();
        HOVERED_CHECKBOX_TEXTURE.dispose();
        HOVERED_TICKED_CHECKBOX_TEXTURE.dispose();
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
