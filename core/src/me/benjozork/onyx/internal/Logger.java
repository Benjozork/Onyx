package me.benjozork.onyx.internal;

import com.badlogic.gdx.Gdx;

import me.benjozork.onyx.internal.console.Console;
import me.benjozork.onyx.utils.Utils;

/**
 * @author Benjozork
 */
public class Logger {

    public static void log(Object x) {
        //TODO remove color codes and formatting espace codes from Gdx.app#log string
        Gdx.app.log("[" + Utils.time() + "] ", x.toString());
        Console.println("[" + Utils.time() + "]  " + x.toString());
        // Do file handle....
    }

}
