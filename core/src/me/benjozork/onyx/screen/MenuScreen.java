package me.benjozork.onyx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.internal.ScreenManager;
import me.benjozork.onyx.ui.UIButton;
import me.benjozork.onyx.ui.UICheckbox;
import me.benjozork.onyx.ui.UIDropdown;
import me.benjozork.onyx.ui.UIRadioButton;
import me.benjozork.onyx.ui.UIRadioButtonGroup;
import me.benjozork.onyx.ui.UIScreen;
import me.benjozork.onyx.ui.object.ActionEvent;
import me.benjozork.onyx.object.TextComponent;

/**
 * Manages the logic when the game's various menus are being navigated through
 * @author Benjozork
 */
public class MenuScreen implements Screen {

    private UIScreen uiScreen;
    private UIButton button;
    private UICheckbox checkbox;
    private UIDropdown dropdown;
    private UIRadioButtonGroup radioButtonGroup;

    private Sprite background;

    private String currentUIFont = "ui/cc_red_alert_inet.ttf";
    private FreeTypeFontGenerator.FreeTypeFontParameter currentParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

    @Override
    public void show() {

        background = new Sprite(new Texture("hud/background_base.png"));
        background.setPosition(0, 0);
        background.setColor(Color.GRAY);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        uiScreen = new UIScreen(new Vector2(0, 0));

        // Init parameter

        currentParameter.size = 35;
        currentParameter.color = Color.WHITE;

        // Add button

        button = new UIButton(960 - (95 / 2), 600 - 55 / 2, 95, 55, new TextComponent("Play!", currentUIFont, currentParameter));
        button.addAction("action", new Runnable() {
            @Override
            public void run() {
                ScreenManager.setCurrentScreen(new GameScreen());
            }
        }, ActionEvent.CLICKED);

        // Add checkbox

        checkbox = new UICheckbox(960 - (95 / 2), 600 - (55 / 2) - 40, 32, 32, new TextComponent("Check!", currentUIFont, currentParameter));

        // Add dropdown

        dropdown = new UIDropdown(960 - (95 / 2), 600 - (55 / 2) - 100, 305, 55, new TextComponent("Dropdown!", currentUIFont, currentParameter));
        dropdown.add("Apple", "Banana", "Orange", "Watermelon", "Raspberry");

        // Add radio buttons

        TextComponent radioComponent = new TextComponent("Radio!", currentUIFont, currentParameter);

        radioButtonGroup = new UIRadioButtonGroup();
        radioButtonGroup.addAction("action", new Runnable() {
            @Override
            public void run() {
                System.out.println("value changed");
            }
        }, ActionEvent.VALUE_CHANGED);

        radioButtonGroup.addButton(new UIRadioButton(200, 200, 26, 26, radioComponent));
        radioButtonGroup.addButton(new UIRadioButton(200, 240, 26, 26, radioComponent));
        radioButtonGroup.addButton(new UIRadioButton(200, 280, 26, 26, radioComponent));

        // Init screen

        uiScreen.init();
        uiScreen.add(button);
        uiScreen.add(checkbox);
        for (UIRadioButton button : radioButtonGroup.getButtons()) {
            uiScreen.add(button);
        }
        uiScreen.add(radioButtonGroup);
        uiScreen.add(dropdown);

    }

    @Override
    public void render(float delta) {

        // Begin batching

        GameManager.setIsRendering(true);

        // Draw background

        background.setColor(Color.GRAY);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        GameManager.getBatch().disableBlending();
        background.draw(GameManager.getBatch());
        GameManager.getBatch().enableBlending();

        if (Gdx.input.justTouched()) {
            uiScreen.click(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            button.resize(10f, 0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            button.resize(- 10f, 0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            button.resize(0f, 10f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            button.resize(0f, - 10f);
        }

        uiScreen.update();
        uiScreen.draw();
        GameManager.getBatch().end();

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
