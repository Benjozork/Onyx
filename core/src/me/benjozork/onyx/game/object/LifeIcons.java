package me.benjozork.onyx.game.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.benjozork.onyx.game.GameScreenManager;

/**
 * @author Benjozork
 */
public class LifeIcons {

    private static final Sprite LIFE_ICON = new Sprite(new Texture("hud/lifeicon.png"));

    static {
        LIFE_ICON.setScale(0.5f, 0.5f);
    }

    public static void draw(SpriteBatch batch, Color color, float x, float y, float xoffset) {
        for (int i = 0; i < GameScreenManager.getPlayers().first().getLives(); i ++) {
            LIFE_ICON.setColor(color);
            LIFE_ICON.setPosition(x + i * (LIFE_ICON.getTexture().getWidth() * xoffset), y);
            LIFE_ICON.draw(batch);
        }
    }

}
