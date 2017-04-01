package me.benjozork.onyx.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.benjozork.onyx.internal.console.Console;

/**
 * Single thread use only.
 */
public class Log {

    private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
    private static final SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

    private static Log latestPrintLog;

    private final String tag;

    private Log(String tag) {
        this.tag = tag;
    }

    public static Log create(String tag) {
		return new Log(tag);
    }

    public void print(String message, Object... args) {
        boolean isNewLog = latestPrintLog == null || latestPrintLog != this;
        if (isNewLog) {
            printNewline();
            printInfo();
        }

        latestPrintLog = this;

        printMessage(message, args);
        printNewline();
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

    private void printNewline() {
	System.out.println();
    }

}
