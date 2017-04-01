package me.benjozork.onyx.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.badlogic.gdx.utils.Array;

public class Log {

    static private final String dateFormat = "yyyy-MM-dd HH:mm:ss";
    static private final SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

    private final String tag;

    private boolean batching;
    private Array<Message> batch;

    private Log(String tag) {
	this.tag = tag;
	this.batch = new Array<Message>();
    }

    public static Log create(String tag) {
	return new Log(tag);
    }

    public void print(String message, Object... args) {
	if (batching) {
	    batch.add(new Message(message, args));
	} else {
	    printInfo();
	    printMessage(message, args);
	    printNewline();
	}
    }

    public void begin() {
	if (batching)
	    throw new IllegalStateException("end() must be called before begin()");
	batching = true;
    }

    public void end() {
	if (!batching)
	    throw new IllegalStateException("begin() must be called before end()");
	batching = false;
	dump();
    }

    private void printInfo() {
	String date = sdf.format(new Date());
	System.out.printf("[%s] (%s)\n", tag, date);
    }

    private void printMessage(String message, Object... args) {
	System.out.printf("-> %s\n", String.format(message, args));
    }

    private void printNewline() {
	System.out.println();
    }

    private void dump() {
	printInfo();
	for (Message message : batch) {
	    printMessage(message.message, message.args);
	}
	System.out.println();
	batch.clear();
    }

    private static class Message {
	final String message;
	final Object[] args;

	public Message(String message, Object[] args) {
	    this.message = message;
	    this.args = args;
	}
    }

}
