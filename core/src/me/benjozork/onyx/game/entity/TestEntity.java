package me.benjozork.onyx.game.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import me.benjozork.onyx.GameManager;
import me.benjozork.onyx.utils.PolygonHelper;

/**
 * For testing purposes only.
 * @author Benjozork
 */
public class TestEntity extends Entity {

    public TestEntity(float x, float y) {
        super(x, y);
    }

    @Override
    public void init() {
        bounds = PolygonHelper.getPolygon(getX(), getY(), 50, 50);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {
        GameManager.setIsShapeRendering(true);
        GameManager.getRenderer().set(ShapeRenderer.ShapeType.Filled);
        GameManager.getRenderer().polygon(bounds.getTransformedVertices());
        GameManager.setIsShapeRendering(false);
    }

    @Override
    public void dispose() {

    }

}
