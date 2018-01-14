package me.benjozork.onyx;

import com.badlogic.gdx.Screen;

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

        // GameScreen may require special disposal in the future. TODO

        String name = currentScreen.getClass().getSimpleName();
        log.print("Changed screen to '%s'", name);
        ScreenManager.currentScreen = currentScreen;
    }

}
