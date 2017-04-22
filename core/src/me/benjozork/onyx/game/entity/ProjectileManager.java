package me.benjozork.onyx.game.entity;

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
                if (pr.source.type != src.type) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns the direction for escaping the nearest {@link ProjectileEntity} for a given {@link LivingEntity}
     * @param src the source entity as to which the nearest bullet's direction is set
     * @return The velocity of the nearest Bullet
     */
    public static Vector2 bulletEscapeDir(LivingEntity src) {
        float dx, dy, dis, least , vx, vy;
        Vector2 currentEscape = new Vector2(0, 0);
        least = 0;
        for (ProjectileEntity pr :
                projectiles) {
            dx = pr.getX() - src.getX();
            dy = pr.getY() - src.getY();
            dis = dx * dx + dy * dy;
            if (dis < least || least == 0) {
                least = dis;
                vx = pr.getVelocity().x;
                vy = pr.getVelocity().y;

                //I know this looks atrocious, but trust me its fine ;)

                if(Math.sqrt( Math.pow(dx - vy, 2) + Math.pow(dy + vx, 2) )
                        < Math.sqrt( Math.pow(dx + vy, 2) + Math.pow(dy - vx, 2) ))
                    currentEscape.set(-vy, vx);
                else
                    currentEscape.set(vy, -vx);
            }
        }
        return currentEscape.nor();
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
