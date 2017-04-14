package me.benjozork.onyx.entity.ai;

/**
 * @author Benjozork
 */
public class AIShootingConfiguration {

    /**
     * The minimum time between two shooting streaks
     */
    public float minShootStreakDelay;

    /**
     * The maximum time between two shooting streaks
     */
    public float maxShootStreakDelay;

    /**
     * The minimum duration of a shooting streak
     */
    public float minShootStreakTime;

    /**
     * The maximum duration of a shooting streak
     */
    public float maxShootStreakTime;

    /**
     * The interval at which a projectile is fired
     */
    public float shootInterval;

    /**
     * The interval for new shooting config values to be generated
     */
    public float shootingConfigValueLifetime;

    /**
     * The minimum precision with which the projectiles are fired
     */
    public float minShootImprecision;

    /**
     * The maximum precision with which the projectiles are fired
     */
    public float maxShootImprecision;

}
