package me.benjozork.onyx.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.object.TextComponent;
import me.benjozork.onyx.ui.object.ActionEvent;
import me.benjozork.onyx.utils.PolygonHelper;

/**
 * @author Benjozork
 */
public class UIRadioButton extends UIElement {

    // RadioButton textures
    private final Texture RADIOBUTTON_TEXTURE = new Texture("ui/radiobutton/radiobutton_0.png");
    private final Texture TICKED_RADIOBUTTON_TEXTURE = new Texture("ui/radiobutton/radiobutton_2.png");
    private final Texture HOVERED_RADIOBUTTON_TEXTURE = new Texture("ui/radiobutton/radiobutton_1.png");
    private final Texture HOVERED_TICKED_RADIOBUTTON_TEXTURE = new Texture("ui/radiobutton/radiobutton_3.png");

    private Texture currentTexture = RADIOBUTTON_TEXTURE;

    private TextComponent component;

    private UIRadioButtonGroup group;

    private boolean selected = false;

    public UIRadioButton(float x, float y, float width, float height, TextComponent component) {
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
        currentTexture = selected ? (hovering() ? HOVERED_TICKED_RADIOBUTTON_TEXTURE : TICKED_RADIOBUTTON_TEXTURE) : (hovering() ? HOVERED_RADIOBUTTON_TEXTURE : RADIOBUTTON_TEXTURE);
    }

    @Override
    public void draw() {
        GameManager.getBatch().draw(currentTexture, getX(), getY(), getWidth(), getHeight());
        component.drawCenteredInContainer(GameManager.getBatch(), getX() + getWidth() + 10, getY(), getWidth() + component.getLayout().width + 50, getHeight(), false, true);
    }

    @Override
    public boolean click(Vector2 localPosition) {
        group.select(this);
        return true;
    }

    @Override
    public void dispose() {
        RADIOBUTTON_TEXTURE.dispose();
        TICKED_RADIOBUTTON_TEXTURE.dispose();
        HOVERED_RADIOBUTTON_TEXTURE.dispose();
        HOVERED_TICKED_RADIOBUTTON_TEXTURE.dispose();
    }

    /**
     * Sets whether the UIRadioButton is selected
     * @param b whether it is selected
     */
    public void set(boolean b) {
        triggerEvent(ActionEvent.VALUE_CHANGED);
        this.selected = b;
    }

    /**
     * Sets which group the UIRadioButton belongs to
     * @param group the group
     */
    public void setGroup(UIRadioButtonGroup group) {
        this.group = group;
    }
}
