package me.benjozork.onyx.logger;

import com.badlogic.gdx.graphics.Color;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.benjozork.onyx.console.Console;
import me.benjozork.onyx.utils.Utils;

/**
 * Single thread use only.
 * @author angelickite
 */
public class Log {

    private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
    private static final SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

    private static Log latestPrintLog;

    private final String tag;

    private Log(String tag) {
        this.tag = tag;
    }

    /**
     * Creates a new logger with the supplied tag
     * @param tag the tag to be used
     * @return the logger
     */
    public static Log create(String tag) {
        return new Log(tag);
    }

    /**
     * Prints a formatted string
     * @param message the message to be printed
     * @param args the objects to format the message with
     */
    public void print(String message, Object... args) {
        boolean isNewLog = latestPrintLog == null || latestPrintLog != this;
        if (isNewLog) {
            printNewline();
            printInfo();
        }

        latestPrintLog = this;

        printMessage(message, args);
    }

    /**
     * Prints a formatted error message
     * @param message the message to be printed
     * @param args the objects to format the message with
     */
    public void error(String message, Object... args) {
        print(Utils.ERROR, message, args);
    }

    /**
     * Prints a formatted warning message
     * @param message the message to be printed
     * @param args the objects to format the message with
     */
    public void warn(String message, Object... args) {
        print(Utils.WARN, message, args);
    }

    /**
     * Prints a formatted fatal message
     * @param message the message to be printed
     * @param args the objects to format the message with
     */
    public void fatal(String message, Object... args) {
        print(Utils.FATAL, message, args);
    }

    /**
     * Prints a formatted debug message
     * @param message the message to be printed
     * @param args the objects to format the message with
     */
    public void debug(String message, Object... args) {
        print(Utils.DEBUG, message, args);
    }

    /**
     * Prints a formatted string
     * @param message the message to be printed
     * @param args the objects to format the message with
     */
    public void print(Color color, String message, Object... args) {
        boolean isNewLog = latestPrintLog == null || latestPrintLog != this;
        if (isNewLog) {
            printNewline();
            printInfo();
        }

        latestPrintLog = this;

        Color prevColor = Console.getColor();
        Console.color(color);
        printMessage(message, args);
        Console.color(prevColor);
    }

    private void printNewline() {
        System.out.println();
    }

    private void printInfo() {
        String date = sdf.format(new Date());
        System.out.printf("[%s] (%s)\n", tag, date);
        Console.printf("\n[%s] (%s)", tag, date);
    }

    private void printMessage(String message, Object... args) {
        System.out.printf("-> %s\n", String.format(message, args));
        Console.print("-> " + String.format(message, args));
    }

}
