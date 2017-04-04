package me.benjozork.onyx.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.internal.PolygonHelper;
import me.benjozork.onyx.utils.TextComponent;

/**
 * @author Benjozork
 */
public class UISlider extends UIElement {

    // Slider textures
    private final Texture SLIDER_TEXTURE = new Texture("ui/slider/slider_bar_0.png");
    private final Texture HOVERED_SLIDER_TEXTURE = new Texture("ui/slider/slider_bar_1.png");
    private final Texture SLIDER_CONTROL_TEXTURE = new Texture("ui/slider/slider_control_0.png");
    private final Texture HOVERED_SLIDER_CONTROL_TEXTURE = new Texture("ui/slider/slider_control_1.png");

    private Texture currentSliderTexture = SLIDER_TEXTURE;
    private Texture currentSliderControlTexture = SLIDER_CONTROL_TEXTURE;

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private BitmapFont font;
    private GlyphLayout layout = new GlyphLayout();
    private String text = new String();

    public UISlider(float x, float y, float width, float height, TextComponent component) {
        super(x, y);
        setWidth(width);
        setHeight(height);
        this.text = component.getText();
        this.generator = new FreeTypeFontGenerator(Gdx.files.internal(component.getFontPath()));
        this.parameter = component.getParameter();
    }

    @Override
    public void init() {
        bounds = PolygonHelper.getPolygon(getX(), getY(), getWidth(), getHeight());
        font = generator.generateFont(parameter);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {

    }

    @Override
    public boolean click(Vector2 localPosition) {
        return false;
    }

    @Override
    public void dispose() {
        SLIDER_TEXTURE.dispose();
        HOVERED_SLIDER_TEXTURE.dispose();
        SLIDER_CONTROL_TEXTURE.dispose();
        HOVERED_SLIDER_CONTROL_TEXTURE.dispose();
    }
}
