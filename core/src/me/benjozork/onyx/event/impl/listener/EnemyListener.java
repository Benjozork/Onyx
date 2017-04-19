package me.benjozork.onyx.event.impl.listener;

import me.benjozork.onyx.event.EventProcessor;
import me.benjozork.onyx.event.impl.EntityDeathEvent;
import me.benjozork.onyx.logger.Log;

/**
 * @author Benjozork
 */
public class EnemyListener implements EventProcessor {

    Log log = Log.create("EnemyListener");

    public boolean onEvent(EntityDeathEvent e) {
        log.print("'%s' died!", e.entity.getClass().getSimpleName());
        return true;
    }

}
