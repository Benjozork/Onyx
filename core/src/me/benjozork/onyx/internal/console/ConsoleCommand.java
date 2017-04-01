package me.benjozork.onyx.internal.console;

/**
 * Describes a command sent to the console
 * @author Benjozork
 */
public class ConsoleCommand {

    private String command;
    private String args[];

    /**
     * @param str the whole string of the command, including identifier and arguments all separated by whitespaces
     */
    public ConsoleCommand(String str) {
        String[] splitBuf = str.split(" ");
        this.command = splitBuf[0];
        if (splitBuf.length <= 1) {
            this.args = new String[]{};
            return;
        }
        System.arraycopy(splitBuf, 1, args, 1, splitBuf.length - 1);
    }

    public String getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }

}
