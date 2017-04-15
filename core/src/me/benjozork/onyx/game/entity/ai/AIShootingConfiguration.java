package me.benjozork.onyx.game.entity.ai;

/**
 * Describes an AI shooting behavior
 * @author Benjozork
 */
public class AIShootingConfiguration {

    /**
     * The minimum time between two shooting streaks, in seconds
     */
    public float minStreakDelay;

    /**
     * The maximum time between two shooting streaks, in seconds
     */
    public float maxStreakDelay;

    /**
     * The minimum duration of a shooting streak, in seconds
     */
    public float minStreakTime;

    /**
     * The maximum duration of a shooting streak, in seconds
     */
    public float maxStreakTime;

    /**
     * The interval at which a projectile is fired, in seconds
     */
    public float shootInterval;

    /**
     * The interval for new shooting config values to be generated, in seconds
     */
    public float resetTime;

    /**
     * The minimum imprecision with which the projectiles are fired, in pixels
     */
    public float minImprecision;

    /**
     * The maximum imprecision with which the projectiles are fired, in pixels
     */
    public float maxImprecision;

    /**
     * Whether the shooting imprecision should be dynamically recalculated for a more dynamic effect.<br/>
     * WARNING: This increases the imprecision and CPU load.
     */
    public boolean recalculateImprecisionRandomly;

    /**
     * The minimum movement made at each frame to track a target.<br/>
     * Basically, the higher the final value, the quicker the AI can track a target.
     */
    public float minTargetTrackingDelta;

    /**
     * The maximum movement made at each frame to track a target.<br/>
     * Basically, the higher the final value, the quicker the AI can track a target.
     */
    public float maxTargetTrackingDelta;

}
