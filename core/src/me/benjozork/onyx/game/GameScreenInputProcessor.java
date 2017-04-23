package me.benjozork.onyx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.KeymapLoader;
import me.benjozork.onyx.OnyxInputProcessor;
import me.benjozork.onyx.game.entity.PlayerEntity;
import me.benjozork.onyx.game.weapon.impl.SimpleCannon;
import me.benjozork.onyx.utils.Utils;

/**
 * @author Benjozork
 */
public class GameScreenInputProcessor extends OnyxInputProcessor {

    private float bulletTimer = 0f;
    private final float MAX_BULLET_TIME = 0.1f;

    @Override
    public void processInput() {
        bulletTimer += Utils.delta();

        if (GameScreenManager.exists()) { // GameScreen input here

            if (! isKeyDown(KeymapLoader.getKeyCode("player_movement_left")) && ! isKeyDown(KeymapLoader.getKeyCode("player_movement_right"))) {
                GameScreenManager.getLocalPlayerEntity().setDirection(PlayerEntity.Direction.STRAIGHT);
            }
            if (isKeyDown(KeymapLoader.getKeyCode("player_movement_right"))) {
                GameScreenManager.getLocalPlayerEntity().setDirection(PlayerEntity.Direction.RIGHT);
                GameScreenManager.getLocalPlayerEntity().accelerate(new Vector2(1000f, 0f));
            }
            if (isKeyDown(KeymapLoader.getKeyCode("player_movement_left"))) {
                GameScreenManager.getLocalPlayerEntity().setDirection(PlayerEntity.Direction.LEFT);
                GameScreenManager.getLocalPlayerEntity().accelerate(new Vector2(- 1000f, 0f));
            }
            if (isKeyDown(KeymapLoader.getKeyCode("player_fire_primary")) && bulletTimer > MAX_BULLET_TIME) {
                bulletTimer = 0f;
                Vector2 mouse = Utils.unprojectWorld(Gdx.input.getX(), Gdx.input.getY());
                GameScreenManager.getLocalPlayerEntity().fireWeapon(SimpleCannon.class, mouse.x, mouse.y);
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
