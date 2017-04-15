package me.benjozork.onyx.game.entity.ai;

/**
 * Describes an AI shooting behavior
 * @author Benjozork
 */
public class AIShootingConfiguration {

    /**
     * The minimum time between two shooting streaks, in seconds
     */
    public float minShootStreakDelay;

    /**
     * The maximum time between two shooting streaks, in seconds
     */
    public float maxShootStreakDelay;

    /**
     * The minimum duration of a shooting streak, in seconds
     */
    public float minShootStreakTime;

    /**
     * The maximum duration of a shooting streak, in seconds
     */
    public float maxShootStreakTime;

    /**
     * The interval at which a projectile is fired, in seconds
     */
    public float shootInterval;

    /**
     * The interval for new shooting config values to be generated, in seconds
     */
    public float shootResetTime;

    /**
     * The minimum imprecision with which the projectiles are fired, in pixels
     */
    public float minShootImprecision;

    /**
     * The maximum imprecision with which the projectiles are fired, in pixels
     */
    public float maxShootImprecision;

}
