package me.benjozork.onyx.game;

import com.badlogic.gdx.Screen;
import me.benjozork.onyx.backend.handlers.RessourceHandler;
import me.benjozork.onyx.game.entity.EnemyEntity;
import me.benjozork.onyx.game.entity.PlayerEntity;

import java.util.Random;

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

        gameWorld = new GameWorld();

        // Create the Player Object.

        //TODO: Temporary initial player pos. !
        PlayerEntity playerEntity = new PlayerEntity(0, 0 - GameWorld.WORLD_SIZE_Y / 3);

        gameWorld.setPlayer(playerEntity);

        gameWorld.addEntity(playerEntity);

        // Add enemies

        //TODO: Temporary enemy mechanics !

        Random random = new Random();


        int min = 2;
        int max = 4;

        int xmin = (int) (0 - GameWorld.WORLD_SIZE_X / 2);
        int xmax = (int) (0 + GameWorld.WORLD_SIZE_X / 2);

        int ymin = 0;
        int ymax = (int) (0 + GameWorld.WORLD_SIZE_Y / 2);

        int count = random.nextInt(max - min + 1) + min;

        for (int i = 0; i < count; i++) {
            int posx = random.nextInt(xmax - xmin + 1) + xmin;
            int posy = random.nextInt(ymax - ymin + 1) + ymin;
            gameWorld.addEntity(new EnemyEntity(posx, posy));
        }

        //log.print("Generated %s enemies in range [x: %s, y: %s] and [x: %s, y: %s]", count, xmin, ymin, xmax, ymax);

        // Create the GameHUD

        gameHUD = new GameHUD(this);

    }

    @Override
    public void render(float delta) {

        // First, render the game world

        gameWorld.draw();
        gameWorld.update();

        // Finally, render the HUD and UI objects, such as HealthBars and PopupTexts

        gameHUD.update();
        gameHUD.render();

    }

    @Override
    public void resize(int width, int height) {
        RessourceHandler.getWorldCamera().setToOrtho(false, width, height);
        RessourceHandler.getGuiCamera().setToOrtho(false, width, height);
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