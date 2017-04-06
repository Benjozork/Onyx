package me.benjozork.onyx.internal.console;

/**
 * Processes commands sent to console
 * @author Benjozork
 */
public interface CommandProcessor {

    /**
     * Called when a {@link ConsoleCommand} assigned to this CommandProcessor is received
     * @param c the {@link ConsoleCommand}}
     */
    void onCommand(ConsoleCommand c);

}
