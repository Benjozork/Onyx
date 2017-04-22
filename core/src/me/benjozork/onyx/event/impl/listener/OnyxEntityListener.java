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
public class OnyxEntityListener implements EventListener {

    Log log = Log.create(this);

    public boolean onEntityKilled(EntityKilledEvent e) {
        if (e.entity instanceof EnemyEntity && e.killer.type == LivingEntity.Type.PLAYER) {
            GameScreenManager.getStaticObjects().add(new PopupText(e.entity, "+100"));
            GameScreenManager.getPlayers().first().addScore(100);
        }
        return true;
    }

}
