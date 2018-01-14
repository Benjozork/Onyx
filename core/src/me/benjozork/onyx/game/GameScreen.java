package me.benjozork.onyx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import me.benjozork.onyx.GameManager;
import me.benjozork.onyx.game.entity.PlayerEntity;

/**
 * Implementation of {@link com.badlogic.gdx.Screen} for the main playing screen.<br/>
 * Takes care of rendering the different parts of the playing screen, including the HUD and the game world.
 *
 * @author Benjozork
 */
public class GameScreen implements Screen {

    private static GameWorld gameWorld;

    private static GameHUD gameHUD;

    @Override
    public void show() {

        // Create the GameWorld

        gameWorld = new GameWorld(this);

        // Create the Player Object.

        //TODO: Temporary initial player pos. !
        PlayerEntity playerEntity = new PlayerEntity(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

        gameWorld.setPlayer(playerEntity);

        gameWorld.addEntity(playerEntity);

        // Create the GameHUD

        gameHUD = new GameHUD(this);

    }

    @Override
    public void render(float delta) {

        // First, render the game world

        gameWorld.update();
        gameWorld.draw();

        // Finally, render the HUD and UI objects, such as HealthBars and PopupTexts

        gameHUD.update();
        gameHUD.render();

    }

    @Override
    public void resize(int width, int height) {
        GameManager.getWorldCamera().setToOrtho(false, width, height);
        GameManager.getGuiCamera().setToOrtho(false, width, height);
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

        gameWorld.dispose();

    }

    public static GameWorld getGameWorld() {
        return gameWorld;
    }

    public static GameHUD getGameHUD() {
        return gameHUD;
    }

}