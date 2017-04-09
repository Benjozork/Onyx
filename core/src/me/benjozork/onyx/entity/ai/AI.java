package me.benjozork.onyx.entity.ai;

import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.entity.LivingEntity;
import me.benjozork.onyx.entity.ProjectileManager;
import me.benjozork.onyx.logger.Log;
import me.benjozork.onyx.utils.Utils;

/**
 * @author Rishi Raj
 */
public class AI {

    private Log log;
    private boolean debug = true;

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

    private LivingEntity source, target;
    private float factor = 100f;
    private AIStrategy strategy;

    private Vector2 sourceDir;
    private Vector2 bulletEscapeDir;
    private Vector2 temp;

    private ProjectileReluctance reluctance;

    /**
     * Creates a basic AI for an entity.
     *
     * @param source   The polygon entity to which AI is to be applied
     * @param target   The polygon entity which the source entity will follow
     * @param strategy The strategy by which the source follows the target
     */
    public AI(LivingEntity source, LivingEntity target, AIStrategy strategy, ProjectileReluctance reluctance) {
        this.source = source;
        this.target = target;
        this.strategy = strategy;
        this.reluctance = reluctance;
        this.log = Log.create("AI-" + source.getClass().getSimpleName() + "-" + String.valueOf(hashCode()));
    }

    public AI(LivingEntity source, LivingEntity target, AIStrategy strategy, ProjectileReluctance reluctance, float factor) {
        this(source, target, strategy, reluctance);
        this.factor = factor;
        this.log = Log.create("AI-" + source.getClass().getSimpleName() + "-" + String.valueOf(hashCode()));
    }

    /**
     * Updates the parameters of source according to the current location of the target
     * @param delta the delta time
     */
    public void update(float delta) {
        bulletEscapeDir = ProjectileManager.nearestBulletDirection(source);
        sourceDir = new Vector2(target.getX() - source.getX(), target.getY() - source.getY());

        sourceDir.nor();
        bulletEscapeDir.nor();

        Utils.toPerpendicularVector(bulletEscapeDir);

        switch (reluctance) {
            case GOD:
                bulletEscapeDir.scl(1);
                break;
            case HIGH:
                bulletEscapeDir.scl(0.7f);
                break;
            case MED:
                bulletEscapeDir.scl(0.5f);
                break;
            case LOW:
                bulletEscapeDir.scl(0.3f);
                break;
            case NONE:
                bulletEscapeDir.scl(0);
                break;
            default:
                log.print("Error: Reluctance %s not supported", reluctance);
        }

        switch (strategy) {
            case ACCELERATED:
                temp = bulletEscapeDir.add(sourceDir);
                temp.scl(factor);
                source.setAcceleration(temp);
                if (debug) log.print("acc: " + source.getAcceleration());
                break;
            case LINEAR:
                temp = bulletEscapeDir.add(sourceDir);
                temp.scl(factor);
                source.setVelocity(temp);
                if (debug) log.print("vel: " + source.getVelocity());
                break;
            default:
                if (debug) log.print("Error: AI strategy %s not supported", strategy);
        }

    }

    public float getFactor() {
        return factor;
    }

    public void setFactor(float factor) {
        this.factor = factor;
    }
}
