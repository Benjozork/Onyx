package me.benjozork.onyx.console.impl;

import com.badlogic.gdx.Gdx;

import me.benjozork.onyx.ScreenManager;
import me.benjozork.onyx.console.CommandProcessor;
import me.benjozork.onyx.console.Console;
import me.benjozork.onyx.console.ConsoleCommand;
import me.benjozork.onyx.game.GameScreen;
import me.benjozork.onyx.logger.Log;
import me.benjozork.onyx.object.Drawable;
import me.benjozork.onyx.screen.MenuScreen;
import me.benjozork.onyx.utils.PolygonHelper;

/**
 * The default {@link CommandProcessor} for Onyx
 * WARNING: All commands that are processed in {@link OnyxCommandProcessor#onCommand(ConsoleCommand)} will not take effect<br/>
 * unless those are added to the {@link OnyxCommandProcessor} instance found in {@link Console}.
 *
 * @author Benjozork
 */
public class OnyxCommandProcessor implements CommandProcessor {

    private Log log = Log.create(this);

    @Override
    public boolean onCommand(ConsoleCommand c) {

        if (c.getCommand().equals("screen")) { // "screen" command

            if (c.getArgs().length == 0) {
                log.warn("Need one argument: [game, menu]");
                return false;
            } else {
                if (c.getArgs()[0].equals("game")) {
                    ScreenManager.setCurrentScreen(new GameScreen());
                    return true;
                } else if (c.getArgs()[0].equals("menu")) {
                    ScreenManager.setCurrentScreen(new MenuScreen());
                    return true;
                } else {
                    log.warn("Invalid argument '%s'", c.getArgs()[0]);
                    return false;
                }
            }

        }

        else if (c.getCommand().equals("debug")) { // "debug" command

            if (c.getArgs().length == 0) {
                log.print("Need one argument: [ui, ai, projectile, polygon, hitbox]");
                return false;
            } else {
                if (c.getArgs()[0].equals("ui")) {
                    //@TODO
                } else if (c.getArgs()[0].equals("ai")) {
                    //@TODO
                } else if (c.getArgs()[0].equals("projectile")) {
                    //@TODO
                } else if (c.getArgs()[0].equals("polygon")) {
                    PolygonHelper.toggleDebug();
                    return true;
                } else if (c.getArgs()[0].equals("debug")) {
                    Drawable.toggleDebug();
                    return true;
                } else {
                    log.print("Invalid argument '%s'", c.getArgs()[0]);
                    return false;
                }
            }

        }

        else if (c.getCommand().equals("exit")) { // "exit" command
            log.print("Game exited");
            Gdx.app.exit();
        }

        else if (c.getCommand().equals("echo")) { // "echo" command
            if (c.getArgs().length == 0) {
                Console.println("echo!");
                System.out.println("echo!");
                return true;
            } else {
                StringBuilder textBuilder = new StringBuilder();
                for (String s : c.getArgs()) textBuilder.append(" ").append(s);
                String text = textBuilder.toString();
                Console.println(text);
                System.out.println(text);
                return false;
            }
        }

        return false;

    }

}