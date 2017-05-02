package me.benjozork.onyx.event.impl;

import me.benjozork.onyx.event.Event;
import me.benjozork.onyx.game.entity.LivingEntity;

/**
 * @author Benjozork
 */
public class EntityKilledEvent extends Event {

    public LivingEntity killer;

    public LivingEntity entity;

}
