package me.benjozork.onyx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.GameManager;

/**
 * Created by Benjozork on 2017-03-04.
 */

public class EntityProjectile extends Entity {

    private SpriteBatch batch;

    private float maxTimer;
    private float bulletTimer;
    private float damage = 0;

    private String texturePath = new String();
    private Texture texture;

    public EntityProjectile(float x, float y) {
        super(new Vector2(x, y));
    }

    @Override
    public void init() {
        batch = GameManager.getBatch();
        // Initialize hitbox
        setBounds(new Rectangle(getX(), getY(), 15, 25));
        Gdx.app.log("t", getBounds().toString());

        angle = Math.atan2(Gdx.input.getX() - getX(), Gdx.graphics.getHeight() - (Gdx.input.getY() - getY()));
}

    @Override
    public void update() {

    }

    @Override
    public void draw() {
        // For every entity registered, check if it is an LivingEntity and apply damage, then destroy entity
           /* for (Entity e : GameManager.getRegisteredEntities()) {
                if (this.collidesWith(e)) {
                    if (e instanceof LivingEntity) {
                        ((LivingEntity) e).damage(damage);
                    }
                    //this.dispose();
                }
            }*/

            batch.begin();
            batch.draw(texture, getX(), getY());
            batch.end();
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
