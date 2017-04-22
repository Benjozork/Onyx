package me.benjozork.onyx.event.impl.listener;

import me.benjozork.onyx.event.EventListener;
import me.benjozork.onyx.event.impl.EntityKilledEvent;
import me.benjozork.onyx.game.GameScreenManager;
import me.benjozork.onyx.game.entity.EnemyEntity;
import me.benjozork.onyx.game.entity.LivingEntity;
import me.benjozork.onyx.game.object.PopupText;
import me.benjozork.onyx.logger.Log;

/**
 * @author Benjozork
 */
public class OnyxEnemyListener implements EventListener {

    Log log = Log.create(this);

    public boolean onEvent(EntityKilledEvent e) {
        if (e.entity instanceof EnemyEntity && e.killer.type == LivingEntity.Type.PLAYER) {
            GameScreenManager.getStaticObjects().add(new PopupText(e.entity, String.valueOf(+100)));
        }
        return true;
    }

}
