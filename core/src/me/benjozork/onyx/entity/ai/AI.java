package me.benjozork.onyx.entity.ai;

import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import me.benjozork.onyx.entity.LivingEntity;
import me.benjozork.onyx.entity.ProjectileManager;
import me.benjozork.onyx.logger.Log;
import me.benjozork.onyx.utils.Utils;

/**
 * @author Rishi Raj
 */
public class AI {

    private Random random = new Random();

    private Log log;

    private boolean debug = false;

    private AIConfiguration.AIStrategy strategy;
    private AIConfiguration.ProjectileReluctance reluctance;

    private LivingEntity source;
    private LivingEntity target;

    private float factor;

    private Vector2 sourceDir;
    private Vector2 bulletEscapeDir;
    private Vector2 temp;

    /**
     * Creates a basic AI for an entity.
     *
     * @param configuration the {@link AIConfiguration} which drives the AI
     */
    public AI(AIConfiguration configuration) {
        this.source = configuration.source;
        this.target = configuration.target;
        this.strategy = configuration.strategy;
        this.reluctance = configuration.reluctance;
        this.factor = configuration.factor;
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
                log.print("Error: AI strategy %s not supported", strategy);
        }

        if (random.nextBoolean()) {
            source.fireProjectileAt("", target.getX(),  target.getY());
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
