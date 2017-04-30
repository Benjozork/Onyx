package me.benjozork.onyx.ui.container;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import me.benjozork.onyx.ui.UIElement;
import me.benjozork.onyx.utils.Utils;

/**
 * An {@link UIContainer} that does not have a position nor a width and height.<br/>
 * This is needed for any {@link Screen} that displays an UI.
 */
public class UIScreen extends UIContainer {

    public UIScreen() {
        super(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), null);
    }

    @Override
    public void update() {
        if (getWidth() != Gdx.graphics.getWidth() || getHeight() != Gdx.graphics.getHeight()) setDimensions(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (Gdx.input.justTouched()) {
            click();
        }

        for (UIElement element : getElements()) {
            element.update(Utils.delta());
            element.update();
        }

        for (UIContainer container : getChildren()) {
            container.update();
        }
    }

}
