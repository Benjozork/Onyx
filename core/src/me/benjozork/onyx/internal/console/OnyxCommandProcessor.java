package me.benjozork.onyx.internal.console;

import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.screen.GameScreen;
import me.benjozork.onyx.screen.MenuScreen;
import me.benjozork.onyx.utils.Logger;

/**
 * @author Benjozork
 */
public class OnyxCommandProcessor implements CommandProcessor {

    @Override
    public void onCommand(ConsoleCommand c) {
        if (c.getCommand().equals("screen")) {
            if (c.getArgs().length == 0) {
                Logger.log("valid args: game, menu");
            } else {
                if (c.getArgs()[0].equals("game")) {
                    GameManager.setCurrentScreen(new GameScreen());
                } else if (c.getArgs()[0].equals("menu")) {
                    GameManager.setCurrentScreen(new MenuScreen());
                }
            }
        }
    }

}
