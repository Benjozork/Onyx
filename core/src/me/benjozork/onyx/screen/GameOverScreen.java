package me.benjozork.onyx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.GameManager;
import me.benjozork.onyx.ScreenManager;
import me.benjozork.onyx.game.GameScreen;
import me.benjozork.onyx.object.TextComponent;
import me.benjozork.onyx.ui.UIButton;
import me.benjozork.onyx.ui.UIScreen;
import me.benjozork.onyx.ui.object.ActionEvent;
import me.benjozork.onyx.utils.CenteredDrawer;

/**
 * @author Benjozork
 */
public class GameOverScreen implements Screen {

    private UIScreen uiScreen;

    private TextComponent gameOverText;

    @Override
    public void show() {

        // Init the UIScreen

        uiScreen = new UIScreen(0, 0);

        // Create a FTFParameter for the button

        FreeTypeFontGenerator.FreeTypeFontParameter buttonReplayParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        buttonReplayParameter.size = 35;
        buttonReplayParameter.color = Color.WHITE;

        // Create a TextComponent for the button

        TextComponent buttonReplayText = new TextComponent (
                "Replay ?",
                "ui/cc_red_alert_inet.ttf",
                buttonReplayParameter
        );

        // Center the button's position

        Vector2 buttonReplayCenterPos = CenteredDrawer.getContained (
                CenteredDrawer.CenteredDrawingType.CENTERED_HORIZONTALLY_IN_CONTAINER,
                0,
                0,
                buttonReplayText.getLayout().width + 250,
                buttonReplayText.getLayout().height + 50,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight()
        );

        // Create the button and add an action to it

        UIButton buttonReplay = new UIButton(buttonReplayCenterPos.x, buttonReplayCenterPos.y - 100, buttonReplayText.getLayout().width + 250, buttonReplayText.getLayout().height + 50, buttonReplayText);
        buttonReplay.addAction("replay", new Runnable() {
            @Override
            public void run() {
                ScreenManager.setCurrentScreen(new GameScreen());
            }
        }, ActionEvent.CLICKED);

        // Add the button to the UIScreen

        uiScreen.add(buttonReplay);

        // Create a FTFParameter for the game over text

        FreeTypeFontGenerator.FreeTypeFontParameter gameOverParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        gameOverParameter.size = 35;
        gameOverParameter.color = Color.BLACK;

        // Create a TextComponenet for the game over text

        gameOverText = new TextComponent (
                "Game Over !",
                "ui/cc_red_alert_inet.ttf",
                gameOverParameter
        );
    }

    public void update() {
        uiScreen.update();
    }

    @Override
    public void render(float delta) {
        update();

        uiScreen.draw();

        gameOverText.drawCenteredInContainer(GameManager.getBatch(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
