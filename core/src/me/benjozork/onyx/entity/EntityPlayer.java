package me.benjozork.onyx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import me.benjozork.onyx.GameManager;
import me.benjozork.onyx.internal.GameUtils;
import me.benjozork.onyx.object.Location;

/**
 * Created by Benjozork on 2017-03-04.
 */
public class EntityPlayer extends LivingEntity {

    ShapeRenderer renderer;
    Sprite img;

    public EntityPlayer(float x, float y) {
        super(new Location(x, y));
    }

    @Override
    public void init() {
        // Get the ShapeRenderer
        renderer = GameManager.getShapeRenderer();
        // Initialize hitbox
        bounds = new Rectangle(getX(), getY(), 50, 50);

        img = new Sprite(new Texture("android/assets/ship.png"), 0, 0, 76, 110);
    }

    @Override
    public void update() {
        if (angle > 360) {
            angle = 120 * GameUtils.getDelta();
        } else if (angle < -360) {
            angle = -120 * GameUtils.getDelta();
        }

        // Check for out of bounds
        if ((getX()  + 50 > Gdx.graphics.getWidth())) {
            setX(Gdx.graphics.getWidth() - 51);
            setSpeed(-10f);
        } else if ((getX()) < 0) {
            setX(1);
            setSpeed(10f);
        } else if ((getY() + 50) > Gdx.graphics.getHeight()) {
            setY(Gdx.graphics.getHeight() - 51);
            setSpeed(-10f);
        } else if ((getY()) < 0) {
            setY(1);
            setSpeed(10f);
        }

        img.setPosition(getX(), getY());
        img.setRotation((float) -angle *  MathUtils.radiansToDegrees);

    }

    @Override
    public void draw() {
        // Render box
        /*renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.WHITE);
        renderer.rect(getX(), getY(), 25, 25, 50, 50, 1f, 1f, (float) -angle * MathUtils.radiansToDegrees);
        renderer.end();*/
        GameManager.getBatch().begin();
        img.draw(GameManager.getBatch());
        GameManager.getBatch().end();
    }

    @Override
    public void move(float vx, float vy) {
        if ((getX() + vx * GameUtils.getDelta()) + 50 > Gdx.graphics.getWidth()
            || (getY() + vy * GameUtils.getDelta()) + 50 > Gdx.graphics.getHeight()
            || (getX() + vx * GameUtils.getDelta()) < 0
            || (getY() + vy * GameUtils.getDelta()) < 0) {
            return;
        }
        super.move(vx, vy);
    }
}
