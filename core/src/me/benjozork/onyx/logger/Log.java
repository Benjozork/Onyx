package me.benjozork.onyx.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.benjozork.onyx.console.Console;

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

    private void printNewline() {
        System.out.println();
    }

    private void printInfo() {
        String date = sdf.format(new Date());
        System.out.printf("[%s] (%s)\n", tag, date);
        Console.println(String.format("\n[%s] (%s)", tag, date));
    }

    private void printMessage(String message, Object... args) {
        System.out.printf("-> %s\n", String.format(message, args));
        Console.println("-> " + String.format(message, args));
    }

}
