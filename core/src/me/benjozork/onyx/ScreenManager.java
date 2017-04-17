package me.benjozork.onyx;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;

import me.benjozork.onyx.game.GameScreen;
import me.benjozork.onyx.logger.Log;

/**
 * Manages {@link Screen}s and their switching
 * @author Benjozork
 */
public class ScreenManager {

    private static final Log log = Log.create("ScreenManager");

    private static Screen currentScreen;

    public static Screen getCurrentScreen() {
        return currentScreen;
    }

    public static void setCurrentScreen(Screen currentScreen) {

        // Because GameScreen requires special disposing, we don't dispose it here.

        if (ScreenManager.currentScreen != null && ! (ScreenManager.currentScreen instanceof GameScreen)) ScreenManager.currentScreen.dispose();
        String name = currentScreen.getClass().getSimpleName();
        log.print("Changed screen to '%s'", name);
        ScreenManager.currentScreen = currentScreen;
    }

}
