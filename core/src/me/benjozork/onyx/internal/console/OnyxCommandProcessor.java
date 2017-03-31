package me.benjozork.onyx.internal.console;

import me.benjozork.onyx.internal.ScreenManager;
import me.benjozork.onyx.screen.GameScreen;
import me.benjozork.onyx.screen.MenuScreen;
import me.benjozork.onyx.internal.Logger;

/**
 * The default {@link CommandProcessor} fpr Onyx
 * @author Benjozork
 */
public class OnyxCommandProcessor implements CommandProcessor {

    @Override
    public void onCommand(ConsoleCommand c) {
        if (c.getCommand().equals("screen")) {
            if (c.getArgs().length == 0) {
                Logger.log("Need one argument: [game, menu]");
            } else {
                if (c.getArgs()[0].equals("game")) {
                    ScreenManager.setCurrentScreen(new GameScreen());
                } else if (c.getArgs()[0].equals("menu")) {
                    ScreenManager.setCurrentScreen(new MenuScreen());
                } else {
                    Logger.log("Invalid argument");
                }
            }
        }
    }

}
