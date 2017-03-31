package me.benjozork.onyx.internal;

import com.badlogic.gdx.Screen;

/**
 * Manages {@link Screen}s and their switching
 * @author Benjozork
 */
public class ScreenManager {

    private static Screen currentScreen;

    public static Screen getCurrentScreen() {
        return currentScreen;
    }

    public static void setCurrentScreen(Screen currentScreen) {
        Logger.log("Changed screen to " + currentScreen.getClass().getName().replace("me.benjozork.onyx.screen.", ""));
        ScreenManager.currentScreen = currentScreen;
    }

}
