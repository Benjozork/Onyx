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
import me.benjozork.onyx.ui.object.TextComponent;

/**
 * Created by Benjozork on 2017-03-19.
 */
public class UICheckbox extends UIElement {

    // Checkbox textures
    private final Texture CHECKBOX_TEXTURE = new Texture("ui/checkbox/checkbox_0.png");
    private final Texture TICKED_CHECKBOX_TEXTURE = new Texture("ui/checkbox/checkbox_2.png");
    private final Texture HOVERED_CHECKBOX_TEXTURE = new Texture("ui/checkbox/checkbox_1.png");
    private final Texture HOVERED_TICKED_CHECKBOX_TEXTURE = new Texture("ui/checkbox/checkbox_3.png");

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private BitmapFont font;
    private GlyphLayout layout = new GlyphLayout();
    private String text = new String();
    private Texture currentTexture = CHECKBOX_TEXTURE;

    private Vector2 dimension = new Vector2();

    private boolean checked = false;

    private float colorTimer;
    private float maxColorTimer = 0.1f;

    public UICheckbox(float x, float y, float width, float height, TextComponent component) {
        super(x, y);
        bounds= PolygonHelper.getPolygon(x,y,width,height);
        setWidth(width);
        setHeight(height);
        this.text = component.getText();
        this.generator = new FreeTypeFontGenerator(Gdx.files.internal(component.getFontPath()));
        this.parameter = component.getParameter();
    }

    @Override
    public void init() {
        font = generator.generateFont(parameter);
    }

    @Override
    public void update() {
        layout.setText(font, text);
//        bounds.width = getWidth() + layout.width + 10;
//        bounds.height = getHeight();
        PolygonHelper.setDimensions(bounds, getWidth() + layout.width + 10, getHeight());


        if (hovering()) {
            currentTexture = (checked ? HOVERED_TICKED_CHECKBOX_TEXTURE : HOVERED_CHECKBOX_TEXTURE);
        } else {
            currentTexture = (checked ? TICKED_CHECKBOX_TEXTURE : CHECKBOX_TEXTURE);
        }
    }

    @Override
    public void draw() {
          /*renderer.begin(ShapeRenderer.ShapeType.Filled);
          renderer.setColor(outerColor);
          renderer.rect(getX(), getY(), dimension.x, dimension.y);
          renderer.setColor(innerColor);
          renderer.rect(getX() + 5, getY() + 5, getWidth() - 10, getHeight() - 10);
          renderer.end();*/

        layout.setText(font, text);

        GameManager.getBatch().draw(currentTexture, getX(), getY(), getWidth(), getHeight());
        font.draw(GameManager.getBatch(), text, (getX() + getWidth() + 50) - layout.width / 2, (getY() + getHeight() / 2) + layout.height / 2);
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

    public String getText() {
        return text;
    }

    public void setText(String v) {
        text = v;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
