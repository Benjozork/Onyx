package me.benjozork.onyx.game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.benjozork.onyx.config.Configs;
import me.benjozork.onyx.config.ProjectConfig;
import me.benjozork.onyx.game.GameScreenManager;
import me.benjozork.onyx.object.TextComponent;

/**
 * @author Benjozork
 */
public class LifeIndicator {

    private static final Sprite LIFE_ICON = new Sprite(new Texture("hud/lifeicon.png"));

    public static TextComponent component = new TextComponent("", Configs.loadCached(ProjectConfig.class).default_font);

    static {
        LIFE_ICON.setScale(0.5f, 0.5f);
        component.getParameter().size = 30;
        component.update();
    }

    public static void draw(SpriteBatch batch, float x, float y) {
        component.setText("x " + GameScreenManager.getLocalPlayer().getLives());
        if (GameScreenManager.getLocalPlayer().getLives() == 0) {
            if (component.getParameter().color != Color.RED) {
                component.getParameter().color = Color.RED;
                component.update();
            }
        } else {
            if (component.getParameter().color != Color.WHITE) {
                component.getParameter().color = Color.WHITE;
                component.update();
            }
        }
        component.draw(batch, x + LIFE_ICON.getWidth(), y + component.getLayout().height * 3.5f);

        LIFE_ICON.setPosition(x, y);
        LIFE_ICON.draw(batch);
    }

}
