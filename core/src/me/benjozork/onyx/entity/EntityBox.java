package me.benjozork.onyx.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.badlogic.gdx.math.Rectangle;
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
        super(x, y);
    }

    @Override
    public void init() {
        // Get the ShapeRenderer
        renderer = GameManager.getShapeRenderer();
        // Initialize hitbox
        boundingRectangle = new Rectangle(loc.getX(), loc.getY(), 50, 50);
    }

    @Override
    public void draw() {
        // Render box
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(color);
        renderer.rect(loc.getX(), loc.getY(), width, height);
        renderer.end();
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
