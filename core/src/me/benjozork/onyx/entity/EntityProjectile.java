package me.benjozork.onyx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.internal.GameManager;

/**
 * Created by Benjozork on 2017-03-04.
 */
public class EntityProjectile extends Entity {

    private SpriteBatch batch;

    private int velocityX = 0, velocityY = 0;
    private float damage = 0;
    private String texturePath = new String();
    private Texture texture;

    public EntityProjectile(float x, float y) {
        super(x, y);
    }

    @Override
    public void init() {
        batch = GameManager.getBatch();
        // Initialize hitbox
        boundingRectangle = new Rectangle(loc.getX(), loc.getY(), 15, 25);
        Gdx.app.log("t", boundingRectangle.toString());
    }

    @Override
    public void draw() {
        // For every entity registered, check if it is an EntityLiving and apply damage, then destroy entity
           /* for (Entity e : GameManager.getRegisteredEntities()) {
                if (this.collidesWith(e)) {
                    if (e instanceof EntityLiving) {
                        ((EntityLiving) e).damage(damage);
                    }
                    //this.dispose();
                }
            }*/

            move(velocityX, velocityY);

            batch.begin();
            batch.draw(texture, loc.getX(), loc.getY());
            batch.end();
        }

    public Vector2 getVelocity() {
        return new Vector2(velocityX, velocityY);
    }

    public void setVelocity(int vx, int vy) {
        this.velocityX = vx;
        this.velocityY = vy;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public void setTexturePath(String texturePath) {
        this.texturePath = texturePath;
        this.texture = new Texture(texturePath);
    }
}
