package me.benjozork.onyx.game.entity.ai;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.game.entity.LivingEntity;
import me.benjozork.onyx.game.entity.ProjectileManager;
import me.benjozork.onyx.logger.Log;
import me.benjozork.onyx.utils.Utils;

/**
 * Manages an AI behavior using an {@link AIConfiguration}, which contains an {@link AIShootingConfiguration}.<br/>
 *
 * Movement mechanics programming by Rishi Raj<br/>
 * Shooting mechanics programming by Benjozork
 *
 * @author Rishi Raj
 * @author Benjozork
 */
public class AI {

    private Log log;

    private boolean debug = false;  // This currently causes A LOT of lag. DO NOT TURN IT ON !

    private AIConfiguration.AIStrategy strategy;
    private AIConfiguration.ProjectileReluctance reluctance;

    private LivingEntity source;
    private LivingEntity target; // Should not be used for AI, only to update untrackedTarget.

    private float minShootStreakDelay;
    private float maxShootStreakDelay;
    private float shootStreakDelay;

    private float minShootStreakTime;
    private float maxShootStreakTime;
    private float shootStreakTime;

    private float shootResetTime, shootResetTimer;

    private float shootStreakTimer;
    private float shootInterval, shootBulletTimer;

    private float minShootImprecision;
    private float maxShootImprecision;
    private float shootImprecision;

    private boolean recalculateShootImprecisionRandomly;

    private float minTargetTrackingDelta;
    private float maxTargetTrackingDelta;
    private float targetTrackingDelta;

    private boolean isFiring = true;

    private float factor;

    private Vector2 sourceDir;
    private Vector2 bulletEscapeDir;
    private Vector2 temp;

    private Vector2 precisionTemp;

    private Vector2 untrackedTarget;

    private double targetAngle;
    private final float ANGLE_DELTA = 100, ANGLE_DELTA_TOLERANCE = 0.1f;

    /**
     * Creates a basic AI for an entity.
     *
     * @param configuration the {@link AIConfiguration} which drives the AI
     */
    public AI(AIConfiguration configuration) {
        this.source = configuration.source;
        this.target = configuration.target;

        this.untrackedTarget = new Vector2(target.getPosition());

        this.log = Log.create("AI-" + source.getClass().getSimpleName() + "-" + String.valueOf(hashCode()));

        if (debug) log.print("Source entity: '%s'", source.getClass().getSimpleName());
        if (debug) log.print("Target entity: '%s'", target.getClass().getSimpleName());

        this.strategy = configuration.strategy;
        if (debug) log.print("Strategy: '%s'", strategy);
        this.reluctance = configuration.reluctance;
        if (debug) log.print("Reluctance: '%s'", reluctance);

        this.minShootStreakDelay = configuration.shootingConfig.minStreakDelay;
        this.maxShootStreakDelay = configuration.shootingConfig.maxStreakDelay;
        this.shootStreakDelay = Utils.randomBetween(minShootStreakDelay, maxShootStreakDelay);
        if (debug) log.print("ShootStreakDelay: '%s'", shootStreakDelay);

        this.minShootStreakTime = configuration.shootingConfig.minStreakTime;
        this.maxShootStreakTime = configuration.shootingConfig.maxStreakTime;
        this.shootStreakTime = Utils.randomBetween(minShootStreakTime, maxShootStreakTime);
        if (debug) log.print("ShootStreakTime: '%s'", shootStreakTime);

        this.shootResetTime = configuration.shootingConfig.resetTime;
        if (debug) log.print("ShootResetTime: '%s'", shootResetTime);
        this.shootInterval = configuration.shootingConfig.shootInterval;
        if (debug) log.print("ShootInterval: '%s'", shootInterval);

        this.minShootImprecision = configuration.shootingConfig.minImprecision;
        this.maxShootImprecision = configuration.shootingConfig.maxImprecision;
        this.shootImprecision = Utils.randomBetween(minShootImprecision, maxShootImprecision);
        if (debug) log.print("ShootImprecision: '%s'", shootImprecision);

        this.recalculateShootImprecisionRandomly = configuration.shootingConfig.recalculateImprecisionRandomly;

        this.minTargetTrackingDelta = configuration.shootingConfig.minTargetTrackingDelta;
        this.maxTargetTrackingDelta = configuration.shootingConfig.maxTargetTrackingDelta;
        this.targetTrackingDelta = Utils.randomBetween(minTargetTrackingDelta, maxTargetTrackingDelta);
        if (debug) log.print("TargetTrackingDelta: '%s'", targetTrackingDelta);

        this.factor = configuration.factor;
    }

    /**
     * Updates the parameters of source according to the current location of the target
     * @param delta the delta time
     */
    public void update(float delta) {

        // Find the target angle

        targetAngle = Math.atan2(source.getX() - untrackedTarget.x, source.getY() - untrackedTarget.y);

        // Update rotation

        if (targetAngle - source.getRotation() < ANGLE_DELTA_TOLERANCE || targetAngle - source.getRotation() >  ANGLE_DELTA_TOLERANCE) source.setRotation((float) targetAngle);
        if (source.getRotation() > targetAngle) source.setRotation(source.getRotation() - (ANGLE_DELTA * MathUtils.degreesToRadians) * delta);
        if (source.getRotation() < targetAngle) source.setRotation(source.getRotation() + (ANGLE_DELTA * MathUtils.degreesToRadians) * delta);

        // Update tracking

        if (untrackedTarget.x < target.getBulletImpactTarget().x) untrackedTarget.add(targetTrackingDelta * delta, 0f);
        if (untrackedTarget.x > target.getBulletImpactTarget().x) untrackedTarget.sub(targetTrackingDelta * delta, 0f);
        if (untrackedTarget.y < target.getBulletImpactTarget().y) untrackedTarget.add(0f, targetTrackingDelta * delta);
        if (untrackedTarget.y > target.getBulletImpactTarget().y) untrackedTarget.sub(0f, targetTrackingDelta * delta);

        // Update timers

        shootStreakTimer += delta;
        shootBulletTimer += delta;
        shootResetTimer += delta;

        // Check if we are currently firing

        if (! isFiring) {

            // Check if it is time to start firing

            if (shootStreakTimer > shootStreakDelay) {
                isFiring = true;
                shootStreakTimer = 0f;
            }

        } else {

            // Check if we should still fire

            if (shootStreakTimer < shootStreakTime) {
                if (shootBulletTimer > shootInterval) {

                    // Apply imprecision and fire projectile

                    if (recalculateShootImprecisionRandomly) {
                        this.shootImprecision = Utils.randomBetween(minShootImprecision, maxShootImprecision);
                    }

                    precisionTemp = untrackedTarget.cpy().add(shootImprecision, shootImprecision);

                    source.fireProjectileAt("entity/enemy/bullet.png", precisionTemp.x, precisionTemp.y);
                    shootBulletTimer = 0f;
                }
            } else {

                // We should stop firing

                shootStreakTimer = 0f;
                isFiring = false;

            }
        }

        // Regenerate values if necessary

        if (shootResetTimer > shootResetTime) {
            this.shootStreakDelay = Utils.randomBetween(minShootStreakDelay, maxShootStreakDelay);
            this.shootStreakTime = Utils.randomBetween(minShootStreakTime, maxShootStreakTime);
            this.shootImprecision = Utils.randomBetween(minShootImprecision, maxShootImprecision);
            this.targetTrackingDelta = Utils.randomBetween(minTargetTrackingDelta, maxTargetTrackingDelta);
            shootResetTimer = 0f;
            if (debug) log.print("AI values regenerated");
        }

        bulletEscapeDir = ProjectileManager.bulletEscapeDir(source);
        sourceDir = new Vector2(target.getX() - source.getX(), target.getY() - source.getY());

        sourceDir.nor();
        bulletEscapeDir.nor();

        switch (reluctance) {
            case GOD:
                bulletEscapeDir.scl(2.0f);
                break;
            case HIGH:
                bulletEscapeDir.scl(1.5f);
                break;
            case MED:
                bulletEscapeDir.scl(1f);
                break;
            case LOW:
                bulletEscapeDir.scl(0.5f);
                break;
            case NONE:
                bulletEscapeDir.scl(0f);
                break;
            default:
                log.error("ERROR: Reluctance '%s' not supported", reluctance);
        }

        switch (strategy) {
            case ACCELERATED:
                sourceDir.scl((float)Math.sqrt(Math.pow(source.getX()-target.getX(),2)+Math.pow(source.getY()-target.getY(),2)) * 0.0004f);
                temp = bulletEscapeDir.add(sourceDir);
                temp.nor();
                temp.scl(factor);
                source.setAcceleration(temp);
                source.accelerate(temp);
                if (debug) log.print("acc: " + source.getAcceleration());
                break;
            case LINEAR:
                temp = bulletEscapeDir.add(sourceDir);
                temp.nor();
                temp.scl(factor);
                source.setVelocity(temp);
                //if (debug) log.print("vel: " + source.getVelocity());
                break;
            default:
                log.error("ERROR: AI strategy '%s' not supported", strategy);
        }

    }

    public float getFactor() {
        return factor;
    }

    public void setFactor(float factor) {
        this.factor = factor;
    }

    public void toggleDebug() {
        debug = ! debug;
    }

}
