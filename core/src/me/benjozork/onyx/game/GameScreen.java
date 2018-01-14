package me.benjozork.onyx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import me.benjozork.onyx.GameManager;
import me.benjozork.onyx.game.entity.Entity;
import me.benjozork.onyx.game.entity.PlayerEntity;
import me.benjozork.onyx.utils.Utils;

/**
 * Implementation of {@link com.badlogic.gdx.Screen} for the main playing screen.<br/>
 * Takes care of rendering the different parts of the playing screen, including the HUD and the game world.
 *
 * @author Benjozork
 */
public class GameScreen implements Screen {

    private GameWorld gameWorld;

    private GameHUD gameHUD;

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

        // Start by drawing the background

        // Next, render the game world, with it's entities

        for (Entity entity : gameWorld.getEntities()) {
            entity.update(Utils.delta());
            entity.update();
        }

        for (Entity entity : gameWorld.getEntities()) {
            entity.draw();
        }

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

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public GameHUD getGameHUD() {
        return gameHUD;
    }

}