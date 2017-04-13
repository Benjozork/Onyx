package me.benjozork.onyx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.object.TextComponent;
import me.benjozork.onyx.ui.UIButton;
import me.benjozork.onyx.ui.UIScreen;
import me.benjozork.onyx.utils.CenteredDrawer;

/**
 * Manages the logic when the game's various menus are being navigated through
 * @author Benjozork
 */
public class MenuScreen implements Screen {

    private UIScreen uiScreen;

    private Sprite background;

    private String currentUIFont = "ui/cc_red_alert_inet.ttf";
    private FreeTypeFontGenerator.FreeTypeFontParameter currentParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

    @Override
    public void show() {

        background = new Sprite(new Texture("hud/background_base.png"));
        background.setPosition(0, 0);
        background.setColor(Color.GRAY);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        uiScreen = new UIScreen(0, 0);

        // Init parameter

        currentParameter.size = 35;
        currentParameter.color = Color.WHITE;

        // "Play" button

        final float BUTTON_PLAY_WIDTH = 400, BUTTON_PLAY_HEIGHT = 65;

        final Vector2 centeredButtonPos = CenteredDrawer.getContained(CenteredDrawer.CenteredDrawingType.CENTERED_HORIZONTALLY_IN_CONTAINER,
                0, 0, BUTTON_PLAY_WIDTH, BUTTON_PLAY_HEIGHT, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        final TextComponent buttonPlayText = new TextComponent("Play", currentUIFont, currentParameter);

        // Add buttons
        UIButton buttonPlay = new UIButton(centeredButtonPos.x, 500, BUTTON_PLAY_WIDTH, BUTTON_PLAY_HEIGHT, buttonPlayText);

        // Init screen

        uiScreen.add(buttonPlay);
        uiScreen.init();

    }

    @Override
    public void render(float delta) {

        // Draw background

        background.setColor(Color.GRAY);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        GameManager.getBatch().disableBlending();
        background.draw(GameManager.getBatch());
        GameManager.getBatch().enableBlending();

        uiScreen.update();
        uiScreen.draw();

    }

    @Override
    public void resize(int width, int height) {
        uiScreen.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        uiScreen.dispose();
    }

    /**
     * The current UIScreen used by the MenuScreen.
     * @return the screen
     */
    public UIScreen getUIScreen() {
        return uiScreen;
    }

    /**
     * Sets the current UIScreen used by the MenuScreen.
     * @param uiScreen the screen to be used
     */
    public void setUIScreen(UIScreen uiScreen) {
        this.uiScreen = uiScreen;
    }
}
