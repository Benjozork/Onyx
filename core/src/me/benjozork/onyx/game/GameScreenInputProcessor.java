package me.benjozork.onyx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.KeymapLoader;
import me.benjozork.onyx.OnyxInputProcessor;
import me.benjozork.onyx.game.entity.PlayerEntity;
import me.benjozork.onyx.utils.Utils;

/**
 * @author Benjozork
 */
public class GameScreenInputProcessor extends OnyxInputProcessor {

    @Override
    public void processInput() {
        if (GameScreenManager.exists()) { // GameScreen input here

            if (! isKeyDown(KeymapLoader.getKeyCode("player_movement_left")) && ! isKeyDown(KeymapLoader.getKeyCode("player_movement_right"))) {
                GameScreenManager.getPlayers().first().getPlayerEntity().setDirection(PlayerEntity.Direction.STRAIGHT);
            }
            if (isKeyDown(KeymapLoader.getKeyCode("player_movement_right"))) {
                GameScreenManager.getPlayers().first().getPlayerEntity().setDirection(PlayerEntity.Direction.RIGHT);
                GameScreenManager.getPlayers().first().getPlayerEntity().accelerate(new Vector2(1000f, 0f));
            }
            if (isKeyDown(KeymapLoader.getKeyCode("player_movement_left"))) {
                GameScreenManager.getPlayers().first().getPlayerEntity().setDirection(PlayerEntity.Direction.LEFT);
                GameScreenManager.getPlayers().first().getPlayerEntity().accelerate(new Vector2(- 1000f, 0f));
            }
            if (isKeyDown(KeymapLoader.getKeyCode("player_fire_primary"))) {
                Vector2 mouse = Utils.unprojectWorld(Gdx.input.getX(), Gdx.input.getY());
                GameScreenManager.getPlayers().first().getPlayerEntity().fireProjectileAt("entity/player/bullet.png", mouse.x, mouse.y);
            }

        }
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
