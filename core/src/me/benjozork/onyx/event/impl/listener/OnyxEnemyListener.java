package me.benjozork.onyx.event.impl.listener;

import me.benjozork.onyx.event.EventListener;
import me.benjozork.onyx.event.impl.EntityDeathEvent;
import me.benjozork.onyx.game.GameScreenManager;
import me.benjozork.onyx.game.entity.DeathCause;
import me.benjozork.onyx.game.entity.EnemyEntity;
import me.benjozork.onyx.game.object.PopupText;
import me.benjozork.onyx.logger.Log;

/**
 * @author Benjozork
 */
public class OnyxEnemyListener implements EventListener {

    Log log = Log.create("EnemyListener");

    public boolean onEvent(EntityDeathEvent e) {
        if (e.entity instanceof EnemyEntity && e.cause == DeathCause.KILLED) {
            GameScreenManager.getStaticObjects().add(new PopupText(e.entity, String.valueOf(+100)));
            GameScreenManager.addScore(100);
        }
        return true;
    }

}
