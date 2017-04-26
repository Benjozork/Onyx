package me.benjozork.onyx.game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.benjozork.onyx.OnyxGame;
import me.benjozork.onyx.game.GameScreenManager;
import me.benjozork.onyx.object.TextComponent;

/**
 * @author Benjozork
 */
public class ScoreIndicator {

    public static final TextComponent component = new TextComponent(String.valueOf(GameScreenManager.getPlayers().first().getScore()), OnyxGame.projectConfig.default_font);;

    static {
        component.getParameter().color = Color.WHITE;
        component.getParameter().borderColor = Color.BLACK;
        component.getParameter().size = 30;
        component.update();
        component.getFont().getData().markupEnabled = true;
    }

    public static void update() {
        component.setText(String.valueOf(GameScreenManager.getPlayers().first().getScore() + " / [#CCCCCC]" + GameScreenManager.getPlayers().first().getHighScore()));
    }

    public static void draw(SpriteBatch batch, float x, float y) {
        component.draw(batch, x, y);

    }

}
