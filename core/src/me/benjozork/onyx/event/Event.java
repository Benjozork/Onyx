package me.benjozork.onyx.event;

import com.badlogic.gdx.utils.TimeUtils;

/**
 * @author Benjozork
 */
public class Event {

    public long timestamp;

    public boolean cancelled = false;

    public Event() {
        timestamp = TimeUtils.nanoTime();
    }

}