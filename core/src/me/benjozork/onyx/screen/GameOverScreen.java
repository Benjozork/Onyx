package me.benjozork.onyx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.object.TextComponent;
import me.benjozork.onyx.ui.UILabel;
import me.benjozork.onyx.ui.container.UIPane;
import me.benjozork.onyx.ui.container.UIScreen;
import me.benjozork.onyx.ui.object.Anchor;
import me.benjozork.onyx.utils.CenteredDrawer;

/**
 * @author Benjozork
 */
public class GameOverScreen implements Screen {

    UIScreen screen;
    UIPane pane;

    @Override
    public void show() {
        screen = new UIScreen();

        float w = Gdx.graphics.getWidth() / 3, h = Gdx.graphics.getHeight() / 3;

        Vector2 centerPos = CenteredDrawer.getContained(CenteredDrawer.CenteredDrawingType.CENTERED_IN_CONTAINER, 0, 0, w, h, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        pane = new UIPane(centerPos.x, centerPos.y, w, h, screen);

        pane.add(new UILabel(0, 0, new TextComponent("0"), pane), Anchor.RIGHT);
    }

    @Override
    public void render(float delta) {
        screen.update();
        screen.draw();
    }
;
    @Override
    public void resize(int width, int height) { // fixme: pane not resizing / repositionning correctly
        float w = width / 3, h = height / 3;
        Vector2 centerPos = CenteredDrawer.getContained(CenteredDrawer.CenteredDrawingType.CENTERED_IN_CONTAINER, 0, 0, w, h, width, height);
        pane.setDimensions(w, h);
        pane.setPosition(centerPos.x, centerPos.y);
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
