package me.benjozork.onyx.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.internal.PolygonHelper;
import me.benjozork.onyx.utils.Utils;

/**
 * Created by Benjozork on 2017-03-04.
 */
public class EntityEnemy extends LivingEntity {

    ShapeRenderer renderer;

    public EntityEnemy(float x, float y) {
        super(new Vector2(x, y));
    }

    @Override
    public void init() {
        // Get the ShapeRenderer
        renderer = GameManager.getShapeRenderer();
        // Initialize hitbox
        bounds = PolygonHelper.getPolygon(getX(),getY(),50,50);
    }

    @Override
    public void update() {
        // The simplest AI ever written
        if (getX() - GameManager.getPlayer().getX() < 400f * Utils.delta() && getX() - GameManager.getPlayer().getX() > -400f * Utils.delta()) position.x = GameManager.getPlayer().getX();
        if (getX() < GameManager.getPlayer().getX()) position.x += 400f * Utils.delta();
        else if (getX() > GameManager.getPlayer().getX()) position.x -= 400f * Utils.delta();
    }

    @Override
    public void draw() {
        GameManager.setIsRendering(false);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.WHITE);
        renderer.rect(getX(), getY(), 50, 50);
        renderer.end();
        GameManager.setIsRendering(true);
    }

    @Override
    public void dispose() {

    }

}
