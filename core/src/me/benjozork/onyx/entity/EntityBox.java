package me.benjozork.onyx.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.internal.GameConfiguration;
import me.benjozork.onyx.internal.GameManager;

/**
 * Created by Benjozork on 2017-03-03.
 */
public class EntityBox extends Entity {

    ShapeRenderer renderer;

    private int width = 0, height = 0;
    private Color color = GameConfiguration.DEFAULT_DRAW_COLOR;

    public EntityBox(int x, int y) {
        super(new Vector2(x, y));
    }

    @Override
    public void init() {
        // Get the ShapeRenderer
        renderer = GameManager.getShapeRenderer();
        // Initialize hitbox
        float vals[] = {getX(), getY(), getX() + 50, getY() + 50};
        bounds = new Polygon(vals);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {
        // Render box
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(color);
        renderer.rect(getX(), getY(), width, height);
        renderer.end();
    }

    @Override
    public void dispose() {
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
