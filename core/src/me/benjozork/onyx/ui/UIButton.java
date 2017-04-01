package me.benjozork.onyx.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.internal.PolygonHelper;
import me.benjozork.onyx.ui.object.TextComponent;
import me.benjozork.onyx.utils.Utils;

/**
 * Created by Benjozork on 2017-03-19.
 */
public class UIButton extends UIElement {

    // Button textures
    private final Texture BUTTON_TEXTURE = new Texture("ui/button/button_0.png");
    private final NinePatch BUTTON = new NinePatch(BUTTON_TEXTURE, 6, 6, 6, 6);
    private final Texture HOVERED_BUTTON_TEXTURE = new Texture("ui/button/button_1.png");
    private final NinePatch HOVERED_BUTTON = new NinePatch(HOVERED_BUTTON_TEXTURE, 6, 6, 6, 6);
    private final Texture CLICKED_BUTTON_TEXTURE = new Texture("ui/button/button_2.png");
    private final NinePatch CLICKED_BUTTON = new NinePatch(CLICKED_BUTTON_TEXTURE, 6, 6, 6, 6);

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private BitmapFont font;
    private GlyphLayout layout = new GlyphLayout();
    private String text = new String();
    private NinePatch currentPatch = BUTTON;

    private float colorTimer;
    private float maxColorTimer = 0.1f;

    public UIButton(float x, float y, float width, float height, TextComponent component) {
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
        PolygonHelper.setDimensions(bounds, getWidth(), getHeight());

        if (colorTimer >= 0) {
            if (colorTimer <= maxColorTimer) {
                colorTimer += Utils.delta();
            } else {
                currentPatch = BUTTON;
                colorTimer = - 1;
            }
        } else {
            if (hovering()) {
                currentPatch = HOVERED_BUTTON;
            } else {
                currentPatch = BUTTON;
            }
        }
    }

    @Override
    public void draw() {
        layout.setText(font, text);

        currentPatch.draw(GameManager.getBatch(), getX(), getY(), getWidth(), getHeight());
        font.draw(GameManager.getBatch(), text, (getX() + getWidth() / 2) - layout.width / 2, (getY() + getHeight() / 2) + layout.height / 2);
    }

    @Override
    public boolean click(Vector2 localPosition) {
        colorTimer = 0;
        currentPatch = CLICKED_BUTTON;
        return true;
    }

    @Override
    public void dispose() {
        BUTTON_TEXTURE.dispose();
        HOVERED_BUTTON_TEXTURE.dispose();
        CLICKED_BUTTON_TEXTURE.dispose();
    }

    public String getText() {
        return text;
    }

    public void setText(String v) {
        text = v;
    }
}
