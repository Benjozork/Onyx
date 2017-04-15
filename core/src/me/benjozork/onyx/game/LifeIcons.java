package me.benjozork.onyx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Benjozork
 */
public class LifeIcons {

    private static final Sprite LIFE_ICON = new Sprite(new Texture("hud/lifeicon.png"));

    private static int maxLives;

    private static int lives;

    static {
        LIFE_ICON.setScale(0.5f, 0.5f);
    }

    public static void draw(SpriteBatch batch, Color color, float x, float y, float xoffset) {
        for (int i = 0; i < lives; i ++) {
            LIFE_ICON.setColor(color);
            LIFE_ICON.setPosition(x + i * (LIFE_ICON.getTexture().getWidth() * xoffset), y);
            LIFE_ICON.draw(batch);
        }
    }

    public static int getLives() {
        return lives;
    }

    public static void setLives(int v) {
        lives = v;
    }

    public static int getMaxLives() {
        return maxLives;
    }

    public static void setMaxLives(int maxLives) {
        LifeIcons.maxLives = maxLives;
    }

    public static void removeLife() {
        lives--;
    }

    public static void addLife() {
        lives++;
    }

}
