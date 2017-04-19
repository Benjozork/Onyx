package me.benjozork.onyx.event.impl;

import me.benjozork.onyx.event.Event;
import me.benjozork.onyx.game.entity.DeathCause;
import me.benjozork.onyx.game.entity.Entity;

/**
 * @author Benjozork
 */
public class EntityDeathEvent extends Event {

    public Entity entity;

    public DeathCause cause;

}
