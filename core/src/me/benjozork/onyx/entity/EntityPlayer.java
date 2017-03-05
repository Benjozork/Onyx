package me.benjozork.onyx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.internal.GameUtils;
import me.benjozork.onyx.object.Location;

/**
 * Created by Benjozork on 2017-03-04.
 */
public class EntityPlayer extends EntityLiving {

    ShapeRenderer renderer;

    public EntityPlayer(float x, float y) {
        super(new Location(x, y));
    }

    @Override
    public void init() {
        // Get the ShapeRenderer
        renderer = GameManager.getShapeRenderer();
        // Initialize hitbox
        boundingRectangle = new Rectangle(loc.getX(), loc.getY(), 50, 50);
        Gdx.app.log("t", boundingRectangle.toString());
    }

    @Override
    public void draw() {
        // Render box
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.WHITE);
        renderer.rect(loc.getX(), loc.getY(), 50, 50);
        renderer.end();
    }

    @Override
    public void move(float vx, float vy) {
        if ((loc.getX() + vx * GameUtils.getDelta()) + 50 > Gdx.graphics.getWidth()
            || (loc.getY() + vy * GameUtils.getDelta()) + 50 > Gdx.graphics.getHeight()
            || (loc.getX() + vx * GameUtils.getDelta()) < 0
            || (loc.getY() + vy * GameUtils.getDelta()) < 0) {
            return;
        }
        this.loc.setX(loc.getX() + vx * GameUtils.getDelta());
        this.boundingRectangle.setX(boundingRectangle.getX() + vx);
        this.loc.setY(loc.getY() + vy * GameUtils.getDelta());
        this.boundingRectangle.setY(boundingRectangle.getY() + vy);
    }
}
