package me.benjozork.onyx.utils;

import com.badlogic.gdx.Gdx;

import me.benjozork.onyx.internal.console.Console;

/**
 * @author Benjozork
 */
public class Logger {

    public static void log(Object x) {
        Gdx.app.log("[" + Utils.time() + "]  ", x.toString());
        Console.println("[" + Utils.time() + "]  " + x.toString());
        // Do file handle....
    }

}
