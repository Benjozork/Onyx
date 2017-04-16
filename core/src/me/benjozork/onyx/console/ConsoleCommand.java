package me.benjozork.onyx.console;

import com.badlogic.gdx.utils.Array;

/**
 * Describes a command sent to the {@link Console}
 * @author Benjozork
 */
public class ConsoleCommand {

    private String command;
    private String args[] = new String[]{};

    /**
     * @param str the whole string of the command, including identifier and arguments all separated by whitespaces
     */
    public ConsoleCommand(String str) {
        String[] splitBuf = str.split(" ");
        this.command = splitBuf[0];
        if (splitBuf.length <= 1) {
            this.args = new String[]{};
        } else {
            Array<String> arr = new Array<String>();
            for (String s : splitBuf) {
                if (! s.equals(splitBuf[0])) arr.add(s);
            }
            this.args = arr.toArray(String.class);
        }
    }

    public String getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }

}
