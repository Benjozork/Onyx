package me.benjozork.onyx.internal;

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
        String name = currentScreen.getClass().getName().replace("me.benjozork.onyx.screen.", "");
        log.print("Changed screen to '%s'", name);
        ScreenManager.currentScreen = currentScreen;
    }

}
