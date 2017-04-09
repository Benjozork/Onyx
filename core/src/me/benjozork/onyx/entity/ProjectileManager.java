package me.benjozork.onyx.entity;


import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import me.benjozork.onyx.utils.PolygonHelper;


/**
 * @author Rishi Raj
 */
public class ProjectileManager {

    private static List<EntityProjectile> projectiles;

    public static void init()
    {
        projectiles = new ArrayList<EntityProjectile>();
    }

    /**
     * Returns true if living entity collides with projectiles thrown by living entities of different type
     * Like true for player if projectile's type is Enemy
     * @param src
     * @return
     */
    public static boolean collides(LivingEntity src)
    {
        for (EntityProjectile pr:
             projectiles) {
            if(PolygonHelper.collidePolygon(pr.getBounds(),src.getBounds()))
            {
                if(pr.source != src.type)
                {
                    return  true;
                }
            }
        }
        return false;
    }

    /**
     * Get the direction for the nearest bullet for a given Living Entity
     * @param src The source entity for which the nearest Bullet's direction is to be found out
     * @return
     */
    public static Vector2 nearestBulletDirection(LivingEntity src)
    {
        float dx,dy,dis,least;
        Vector2 returnVector = new Vector2(0,0);
        least = 0;
        for (EntityProjectile pr:
                projectiles) {
            dx = pr.getX() - src.getX();
            dy = pr.getY() - src.getY();
            dis = dx*dx + dy*dy;
            if(dis<least || least == 0)
            {
                least =dis;
                returnVector.set(dx,dy);
            }
        }
        return returnVector;
    }

    /**
     * Add a projectile
     * @param projectile Projectile to be added
     */
    public static void addProjectile(EntityProjectile projectile)
    {
        System.out.println("Added projectile");
        projectiles.add(projectile);
    }

    /**
     * Remove a projectile
     * @param projectile Projectile to be removed
     * @return
     */
    public static boolean removeProjectile(EntityProjectile projectile)
    {
        return projectiles.remove(projectile);
    }


}
