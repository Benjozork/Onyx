package me.benjozork.onyx.screen;

import com.badlogic.gdx.Screen;

import me.benjozork.onyx.OnyxInputProcessor;
import me.benjozork.onyx.game.GameScreenInputProcessor;
import me.benjozork.onyx.object.TextComponent;
import me.benjozork.onyx.ui.UIButton;
import me.benjozork.onyx.ui.container.UIPane;
import me.benjozork.onyx.ui.container.UIScreen;
import me.benjozork.onyx.ui.container.UITabPane;
import me.benjozork.onyx.ui.object.Anchor;

/**
 * @author Benjozork
 */
public class TestUIScreen implements Screen {

    private UIScreen screen;

    @Override
    public void show() {
        OnyxInputProcessor.setCurrentProcessor(new GameScreenInputProcessor());

        screen = new UIScreen();

        UITabPane selectionPane = new UITabPane(50, 50, 200, 200, screen);

        UIPane selection1 = new UIPane(0, 0, 0, 0, selectionPane);

        UIButton button1 = new UIButton(0, 0, 120, 40, new TextComponent("Test1"), selection1);
        UIButton button2 = new UIButton(0, 0, 120, 40, new TextComponent("Test2"), selection1);
        button2.snapTo(button1, Anchor.TOP_RIGHT);

        selection1.add(button1);
        selection1.add(button2);

        selectionPane.addSelection(new TextComponent("test1"), selection1);

        screen.add(selectionPane);
    }

    @Override
    public void render(float delta) {
        screen.update();
        screen.draw();
    }

    @Override
    public void resize(int width, int height) {
        screen.resize(width, height);
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
        screen.dispose();
    }

}