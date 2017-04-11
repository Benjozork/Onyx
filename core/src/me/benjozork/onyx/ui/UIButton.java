package me.benjozork.onyx.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.utils.PolygonHelper;
import me.benjozork.onyx.object.TextComponent;
import me.benjozork.onyx.utils.Utils;

/**
 * @author Benjozork
 */
public class UIButton extends UIElement {

    // Button textures

    private final Texture BUTTON_TEXTURE = new Texture("ui/button/button_0.png");
    private final NinePatch BUTTON = new NinePatch(BUTTON_TEXTURE, 6, 6, 6, 6);
    private final Texture HOVERED_BUTTON_TEXTURE = new Texture("ui/button/button_1.png");
    private final NinePatch HOVERED_BUTTON = new NinePatch(HOVERED_BUTTON_TEXTURE, 6, 6, 6, 6);
    private final Texture CLICKED_BUTTON_TEXTURE = new Texture("ui/button/button_2.png");
    private final NinePatch CLICKED_BUTTON = new NinePatch(CLICKED_BUTTON_TEXTURE, 6, 6, 6, 6);

    private TextComponent component;

    private NinePatch currentPatch = BUTTON;

    private float colorTimer;
    private float maxColorTimer = 0.1f;

    public UIButton(float x, float y, float width, float height, TextComponent component) {
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

        PolygonHelper.setDimensions(bounds, getWidth(), getHeight());

        if (colorTimer >= 0) {
            if (colorTimer <= maxColorTimer) {
                colorTimer += Utils.delta();
            } else {
                currentPatch = BUTTON;
                colorTimer = - 1;
            }
        } else {
            currentPatch = hovering() ? HOVERED_BUTTON : BUTTON;
        }

    }

    @Override
    public void draw() {
        currentPatch.draw(GameManager.getBatch(), getX(), getY(), getWidth(), getHeight());
        component.drawCenteredInContainer(GameManager.getBatch(), getX(), getY(), getWidth(), getHeight());
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

}
