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

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private BitmapFont font;

    private GlyphLayout layout = new GlyphLayout();

    private String text;

    public UILabel(float x, float y, TextComponent component) {
        super(x, y);
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
        layout.setText(font, text);
        PolygonHelper.setDimensions(bounds, layout.width, layout.height);
    }

    @Override
    public void draw() {
        font.draw(GameManager.getBatch(), text, getX(), getY());
    }

    @Override
    public boolean click(Vector2 localPosition) {
        return false;
    }

    @Override
    public void dispose() {

    }
}
