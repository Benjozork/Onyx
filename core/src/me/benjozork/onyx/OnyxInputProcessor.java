package me.benjozork.onyx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ArrayMap;

/**
 * @author Benjozork
 */
public abstract class OnyxInputProcessor implements InputProcessor {

    private static OnyxInputProcessor currentProcessor;

    private static Vector2 mouse = new Vector2();

    private static ArrayMap<Integer, Boolean> keyStatus = new ArrayMap<Integer, Boolean>();

    public abstract void processInput();

    public static boolean isKeyDown(int keycode) {
        if (keyStatus.get(keycode) == null) return false;
        else return keyStatus.get(keycode);
    }

    @Override
    public boolean keyDown(int keycode) {
        keyStatus.put(keycode, true);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        keyStatus.put(keycode, false);
        return true;
    }


    public abstract boolean keyTyped(char character);

    public abstract boolean touchDown(int screenX, int screenY, int pointer, int button);

    public abstract boolean touchUp(int screenX, int screenY, int pointer, int button);

    public abstract boolean touchDragged(int screenX, int screenY, int pointer);

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mouse.set(screenX, screenY);
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }


    public static OnyxInputProcessor getCurrentProcessor() {
        return currentProcessor;
    }

    public static void setCurrentProcessor(OnyxInputProcessor currentProcessor) {
        OnyxInputProcessor.currentProcessor = currentProcessor;
        Gdx.input.setInputProcessor(currentProcessor);
    }

}
