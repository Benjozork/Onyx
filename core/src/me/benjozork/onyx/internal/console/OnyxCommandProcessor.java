package me.benjozork.onyx.internal.console;

import me.benjozork.onyx.internal.ScreenManager;
import me.benjozork.onyx.logger.Log;
import me.benjozork.onyx.screen.GameScreen;
import me.benjozork.onyx.screen.MenuScreen;

/**
 * The default {@link CommandProcessor} for Onyx
 * WARNING: All commands that are processed in {@link OnyxCommandProcessor#onCommand(ConsoleCommand)} will not take effect<br/>
 * unless those are added to the {@link OnyxCommandProcessor} instance found in {@link Console}.
 *
 * @author Benjozork
 */
public class OnyxCommandProcessor implements CommandProcessor {

    private static Log log = Log.create("OnyxCommandProcessor");

    @Override
    public boolean onCommand(ConsoleCommand c) {
        if (c.getCommand().equals("screen")) {
            if (c.getArgs().length == 0) {
                log.print("Need one argument: [game, menu]");
                return false;
            } else {
                if (c.getArgs()[0].equals("game")) {
                    ScreenManager.setCurrentScreen(new GameScreen());
                    return true;
                } else if (c.getArgs()[0].equals("menu")) {
                    ScreenManager.setCurrentScreen(new MenuScreen());
                    return true;
                } else {
                    log.print("Invalid argument '%s'", c.getArgs()[1]);
                    return false;
                }
            }
        }
        return false;
    }

}