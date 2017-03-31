package me.benjozork.onyx.internal.console;

/**
 * @author Benjozork
 */
public class ConsoleCommand {

    public ConsoleCommand(String command, String... args) {
        this.command = command;
        this.args = args;
    }

    private String command;
    private String args[];

    public String getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }

}
