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
import me.benjozork.onyx.ui.container.UITabPane;
import me.benjozork.onyx.ui.object.Stretch;
import me.benjozork.onyx.utils.Utils;

/**
 * @author Benjozork
 */
public class TestUIScreen implements Screen {

    private UIScreen screen;
    private UITabPane selectionPane;

    @Override
    public void show() {
        OnyxInputProcessor.setCurrentProcessor(new GameScreenInputProcessor());

        screen = new UIScreen();

        selectionPane = new UITabPane(50, 50, 200, 200, screen);

        UIPane selection1 = new UIPane(0, 0, 0, 0, selectionPane);
        UIButton button1 = new UIButton(0, 0, 100, 40, new TextComponent("Test1"), selection1);
        selection1.add(button1);
        button1.stretchTo(Stretch.RIGHT, Stretch.TOP);

        UIPane selection2 = new UIPane(0, 0, 0, 0, selectionPane);
        UIButton button2 = new UIButton(0, 0, 100, 40, new TextComponent("Test2"), selection2);
        selection2.add(button2);
        button2.stretchTo(Stretch.RIGHT, Stretch.TOP);

        UIPane selection3 = new UIPane(0, 0, 0, 0, selectionPane);
        UIButton button3 = new UIButton(0, 0, 100, 40, new TextComponent("Test3"), selection3);
        selection3.add(button3);
        button3.stretchTo(Stretch.RIGHT, Stretch.TOP);

        selectionPane.addSelection(new TextComponent("test1"), selection1);
        selectionPane.addSelection(new TextComponent("test2"), selection2);
        selectionPane.addSelection(new TextComponent("test3"), selection3);

        screen.add(selectionPane);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            selectionPane.setWidth(selectionPane.getWidth() - 100f * Utils.delta());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            selectionPane.setHeight(selectionPane.getHeight() + 100f * Utils.delta());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            selectionPane.setWidth(selectionPane.getWidth() + 100f * Utils.delta());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            selectionPane.setHeight(selectionPane.getHeight() - 100f * Utils.delta());
        }
        screen.update();
        screen.draw();
    }

    @Override
    public void resize(int width, int height) {
        screen.setDimensions(width, height);
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