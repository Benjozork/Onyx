package me.benjozork.onyx.event.impl.listener;

import me.benjozork.onyx.event.EventProcessor;
import me.benjozork.onyx.event.impl.EntityDeathEvent;
import me.benjozork.onyx.game.GameScreenManager;
import me.benjozork.onyx.game.entity.EnemyEntity;
import me.benjozork.onyx.game.object.PopupScoreText;
import me.benjozork.onyx.logger.Log;

/**
 * @author Benjozork
 */
public class OnyxEnemyListener implements EventProcessor {

    Log log = Log.create("EnemyListener");

    public boolean onEvent(EntityDeathEvent e) {
        if (e.entity instanceof EnemyEntity) GameScreenManager.getStaticObjects().add(new PopupScoreText(e.entity, 100));
        return true;
    }

}
