package me.benjozork.onyx.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import me.benjozork.onyx.internal.GameConfiguration;
import me.benjozork.onyx.GameManager;
import me.benjozork.onyx.object.Location;

/**
 * Created by Benjozork on 2017-03-04.
 */
public class EntityEnemy extends LivingEntity {

    ShapeRenderer renderer;

    public EntityEnemy(int x, int y) {
        super(new Location(x, y));
    }

    @Override
    public void init() {
        // Get the ShapeRenderer
        renderer = GameManager.getShapeRenderer();
        // Initialize hitbox
        bounds = new Rectangle(getX(), getY(), 50, 50);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {
        // Render box
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(GameConfiguration.ENEMY_DEFAULT_COLOR);
        renderer.rect(getX(), getY(), 50, 50);
        renderer.end();
    }

}
