package me.benjozork.onyx.internal.console;

import me.benjozork.onyx.internal.ScreenManager;
import me.benjozork.onyx.logger.Log;
import me.benjozork.onyx.screen.GameScreen;
import me.benjozork.onyx.screen.MenuScreen;

/**
 * The default {@link CommandProcessor} for Onyx
 * @author Benjozork
 */
public class OnyxCommandProcessor implements CommandProcessor {

    private static Log log = Log.create("OnyxCommandProcessor");

    @Override
    public void onCommand(ConsoleCommand c) {
        if (c.getCommand().equals("screen")) {
            if (c.getArgs().length == 0) {
                log.print("Need one argument: [game, menu]");
            } else {
                if (c.getArgs()[0].equals("game")) {
                    ScreenManager.setCurrentScreen(new GameScreen());
                } else if (c.getArgs()[0].equals("menu")) {
                    ScreenManager.setCurrentScreen(new MenuScreen());
                } else {
                    log.print("Invalid argument '%s'", c.getArgs()[1]);
                }
            }
        }
    }

}
