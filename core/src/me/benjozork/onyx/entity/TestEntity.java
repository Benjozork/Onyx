package me.benjozork.onyx.entity;

import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.utils.PolygonHelper;

/**
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
        GameManager.getShapeRenderer().polygon(bounds.getTransformedVertices());
        GameManager.setIsShapeRendering(false);
    }

    @Override
    public void dispose() {

    }

}
