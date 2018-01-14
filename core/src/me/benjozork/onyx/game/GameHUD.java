package me.benjozork.onyx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.GameManager;
import me.benjozork.onyx.utils.Utils;

/**
 * Manages and renders the game HUD.
 *
 * @author Benjozork
 */
public class GameHUD {

    GameScreen gameScreen;

    public GameHUD(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void update() {

    }

    public void render() {
        ShapeRenderer renderer = GameManager.getRenderer();

        GameManager.setIsShapeRendering(true);

        renderer.setProjectionMatrix(GameManager.getWorldCamera().combined);

        renderer.setColor(Color.BLACK);
        renderer.line(gameScreen.getGameWorld().getPlayer().getPosition(), Utils.unprojectWorld(new Vector2(Gdx.input.getX(), Gdx.input.getY())));
    }

}
