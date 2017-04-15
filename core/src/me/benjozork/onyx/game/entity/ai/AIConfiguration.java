package me.benjozork.onyx.game.entity.ai;

/**
 * Describes an AI behavior
 * @author Benjozork
 */
public class AIConfiguration {

    /**
     * The strategy used by the AI
     */
    public AIStrategy strategy;

    /**
     * The degree of bullet avoidance
     */
    public ProjectileReluctance reluctance;

    /**
     * The source {@link me.benjozork.onyx.game.entity.LivingEntity} of the AI
     */
    public me.benjozork.onyx.game.entity.LivingEntity source;

    /**
     * The target {@link me.benjozork.onyx.game.entity.LivingEntity} of the AI
     */
    public me.benjozork.onyx.game.entity.LivingEntity target;

    /**
     * The {@link AIShootingConfiguration} to use for shooting mechanics
     */
    public AIShootingConfiguration shootingConfig;

    public float factor = 100f;

    /**
     * The different strategies by which an entity follows another entity
     */
    public enum AIStrategy {
        ACCELERATED,
        LINEAR
    }

    public enum ProjectileReluctance {
        NONE,
        LOW,
        MED,
        HIGH,
        GOD
    }

}
