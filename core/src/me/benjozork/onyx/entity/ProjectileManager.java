package me.benjozork.onyx.entity;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import me.benjozork.onyx.logger.Log;
import me.benjozork.onyx.utils.PolygonHelper;

/**
 * @author Rishi Raj
 */
public class ProjectileManager {

    private static Log log = Log.create("ProjectileManager");
    private static boolean debug = false;

    private static List<ProjectileEntity> projectiles;

    public static void init() {
        projectiles = new ArrayList<ProjectileEntity>();
    }

    /**
     * Returns true whether a {@link LivingEntity} collides with projectiles thrown<br/>
     * by {@link LivingEntity} objects of a different subclass
     * @param src the {@link LivingEntity} used to perform the check
     */
    public static boolean collides(LivingEntity src) {
        for (ProjectileEntity pr : projectiles) {
            if (PolygonHelper.collidePolygon(pr.getBounds(), src.getBounds())) {
                if (pr.source != src.type) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns the direction for the nearest bullet for a given {@link LivingEntity}
     * @param src the source entity as to which the nearest bullet's direction is set
     */
    public static Vector2 nearestBulletDirection(LivingEntity src) {
        float dx, dy, dis, least;
        Vector2 returnVector = new Vector2(0, 0);
        least = 0;
        for (ProjectileEntity pr :
                projectiles) {
            dx = pr.getX() - src.getX();
            dy = pr.getY() - src.getY();
            dis = dx * dx + dy * dy;
            if (dis < least || least == 0) {
                least = dis;
                returnVector.set(dx, dy);
            }
        }
        return returnVector;
    }

    public static void addProjectile(ProjectileEntity projectile) {
        if (debug) log.print("Added projectile");
        projectiles.add(projectile);
    }

    public static boolean removeProjectile(ProjectileEntity projectile) {
        return projectiles.remove(projectile);
    }

    public static void toggleDebug() {
        debug = ! debug;
    }

}
