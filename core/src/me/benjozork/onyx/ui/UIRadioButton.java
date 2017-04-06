package me.benjozork.onyx.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.internal.PolygonHelper;
import me.benjozork.onyx.ui.object.ActionEvent;
import me.benjozork.onyx.utils.TextComponent;

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
        component.updateLayout();
        PolygonHelper.setDimensions(bounds, getWidth() + component.getLayout().width + 10, getHeight());

        currentTexture = selected ? (hovering() ? HOVERED_TICKED_RADIOBUTTON_TEXTURE : TICKED_RADIOBUTTON_TEXTURE) : (hovering() ? HOVERED_RADIOBUTTON_TEXTURE : RADIOBUTTON_TEXTURE);

          /*if (hovering()) {
               currentTexture = (checked ? HOVERED_TICKED_CHECKBOX_TEXTURE : HOVERED_CHECKBOX_TEXTURE);
          } else {
               currentTexture = (checked ? TICKED_CHECKBOX_TEXTURE : CHECKBOX_TEXTURE);
          }*/
    }

    @Override
    public void draw() {
        component.updateLayout();

        GameManager.getBatch().draw(currentTexture, getX(), getY(), getWidth(), getHeight());
        component.getFont().draw(GameManager.getBatch(), component.getText(), (getX() + getWidth() + 50) - component.getLayout().width / 2, (getY() + getHeight() / 2) + component.getLayout().height / 2);
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
