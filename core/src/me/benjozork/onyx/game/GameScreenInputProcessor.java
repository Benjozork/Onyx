package me.benjozork.onyx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.game.entity.PlayerEntity;
import me.benjozork.onyx.internal.OnyxInputProcessor;
import me.benjozork.onyx.utils.Utils;

/**
 * @author Benjozork
 */
public class GameScreenInputProcessor extends OnyxInputProcessor {

    @Override
    public void processInput() {
        if (GameScreenManager.exists()) { // GameScreen input here

            if (! isKeyDown(Input.Keys.A) && ! isKeyDown(Input.Keys.D)) {
                GameScreenManager.getPlayer().setDirection(PlayerEntity.Direction.STRAIGHT);
            }
            if (isKeyDown(Input.Keys.D)) {
                GameScreenManager.getPlayer().setDirection(PlayerEntity.Direction.RIGHT);
                GameScreenManager.getPlayer().accelerate(new Vector2(1000f, 0f));
            }
            if (isKeyDown(Input.Keys.A)) {
                GameScreenManager.getPlayer().setDirection(PlayerEntity.Direction.LEFT);
                GameScreenManager.getPlayer().accelerate(new Vector2(- 1000f, 0f));
            }
            if (isKeyDown(Input.Keys.SPACE)) {
                Vector2 mouse = Utils.unprojectWorld(Gdx.input.getX(), Gdx.input.getY());
                GameScreenManager.getPlayer().fireProjectileAt("entity/player/bullet.png", mouse.x, mouse.y);
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
