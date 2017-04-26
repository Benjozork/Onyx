package me.benjozork.onyx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;

import me.benjozork.onyx.OnyxInputProcessor;
import me.benjozork.onyx.game.GameScreenInputProcessor;
import me.benjozork.onyx.object.TextComponent;
import me.benjozork.onyx.ui.UIButton;
import me.benjozork.onyx.ui.container.UIPane;
import me.benjozork.onyx.ui.container.UIScreen;
import me.benjozork.onyx.ui.container.UISelectionPane;

/**
 * @author Benjozork
 */
public class TestUIScreen implements Screen {

    private UIScreen screen;
    private UISelectionPane selectionPane;
    private UIPane childPane, childPane2;

    @Override
    public void show() {
        OnyxInputProcessor.setCurrentProcessor(new GameScreenInputProcessor());

        screen = new UIScreen();
        selectionPane = new UISelectionPane(50, 50, 200, 200, screen);

        childPane = new UIPane(0, 0, 0, 0, selectionPane);
        new UIButton(10, 10, 100, 35, new TextComponent("Test1"), childPane);
        selectionPane.addSelectorItem(new TextComponent("test1"), childPane);

        childPane2 = new UIPane(0, 0, 0, 0, selectionPane);
        new UIButton(10, 50, 100, 35, new TextComponent("Test2"),  childPane2);
        selectionPane.addSelectorItem(new TextComponent("test2"), childPane2);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) selectionPane.setRelativeY(500);
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