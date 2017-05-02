package me.benjozork.onyx.game.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.GameManager;
import me.benjozork.onyx.config.Configs;
import me.benjozork.onyx.config.ProjectConfig;
import me.benjozork.onyx.game.GameScreenManager;
import me.benjozork.onyx.game.entity.LivingEntity;
import me.benjozork.onyx.object.StaticDrawable;
import me.benjozork.onyx.object.TextComponent;
import me.benjozork.onyx.utils.CenteredDrawer;
import me.benjozork.onyx.utils.PolygonHelper;
import me.benjozork.onyx.utils.Utils;

/**
 * @author Benjozork
 */
@SuppressWarnings ("WeakerAccess")
public class PopupText extends StaticDrawable {

    private static final ProjectConfig projectConfig = Configs.loadCached(ProjectConfig.class);

    private Vector2 position = new Vector2();
    private Vector2 initialPosition = new Vector2();
    private Vector2 size = new Vector2();

    private static TextComponent component;

    private final BitmapFont font;

    // Public attributes

    public int initialFontSize = 35;
    public Color initialFontColor = Color.WHITE;

    public float scaleDelta = 6f;
    public float transparencyDelta = 550f;

    public float transparencyDeltaTolerance = 0.05f;

    // End public attributes

    public PopupText(LivingEntity parent, String text) {
        super(0, 0);

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = initialFontSize;
        parameter.color = initialFontColor;
        this.component = new TextComponent(text, projectConfig.default_font, parameter);

        Vector2 pos = CenteredDrawer.get(CenteredDrawer.CenteredDrawingType.CENTERED_AT_POINT, parent.getX() + parent.getTextureWidth() /2, parent.getY() + parent.getTextureHeight() / 2, component.getLayout().width, component.getLayout().height);
        bounds = PolygonHelper.getPolygon(pos.x, pos.y, 0, 0);
        this.initialPosition.set(pos.x, pos.y);

        this.font = component.getFont();
        this.size.set(component.getLayout().width, component.getLayout().height);
    }

    @Override
    public void init() {

    }

    public void update() {
        //size.x += 2000f * Utils.delta(); // NTS: size.x in font.draw(...) is only wrap width !
        font.getData().scaleX += scaleDelta * Utils.delta();
        font.getData().scaleY += scaleDelta * Utils.delta();
        if (font.getColor().a > transparencyDeltaTolerance) {
            font.getColor().a -= transparencyDelta / 255f * Utils.delta();
        } else {
            dispose();
        }

        size.x = component.getLayout().width * font.getData().scaleX;
        size.y = component.getLayout().height * font.getData().scaleY;

        CenteredDrawer.switchToBitmap();
        position = CenteredDrawer.get(CenteredDrawer.CenteredDrawingType.CENTERED_AT_POINT, initialPosition.x, initialPosition.y, size.x, size.y);
        CenteredDrawer.switchToPixel();
    }

    public void draw() {
        font.draw(GameManager.getBatch(), component.getText(), position.x, position.y);
    }

    @Override
    public void dispose() {
        component.dispose();
        GameScreenManager.getStaticObjects().removeValue(this, true);
    }

}