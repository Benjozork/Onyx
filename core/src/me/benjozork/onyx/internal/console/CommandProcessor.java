package me.benjozork.onyx.internal.console;

/**
 * Interface that processes {@link ConsoleCommand} sent to the {@link Console}.<br/>
 * WARNING: All commands that are processed in {@link CommandProcessor#onCommand(ConsoleCommand)} will not take effect<br/>
 * unless those are added to an {@link CommandProcessor} subclass instance, which then has to be registered<br/>
 * using {@link Console#registerCommands(CommandProcessor, Array)}.
 *
 * @author Benjozork
 */
public interface CommandProcessor {

    /**
     * Called when a {@link ConsoleCommand} assigned to this CommandProcessor is received
     * @param c the {@link ConsoleCommand}}
     */
    boolean onCommand(ConsoleCommand c);

}
