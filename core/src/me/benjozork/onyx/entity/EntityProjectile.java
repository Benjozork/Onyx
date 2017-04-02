package me.benjozork.onyx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.internal.PolygonHelper;
import me.benjozork.onyx.internal.ScreenManager;
import me.benjozork.onyx.screen.GameScreen;
import me.benjozork.onyx.utils.Utils;

/**
 * @author Benjozork
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
        // initialize hitbox
        bounds = PolygonHelper.getPolygon(getX(), getY(), 10, 10);

        // get mouse point, unproject it, and set velocity accordingly
        Vector2 mouse = Utils.unprojectWorld(Gdx.input.getX(), Gdx.input.getY());
        velocity.set(mouse.sub(getX(), getY()));
    }

    @Override
    public void update() {
        GameScreen gameScreen  = (GameScreen) ScreenManager.getCurrentScreen();
        if (this.collidesWith(gameScreen.getEnemy().getBounds())) {
            gameScreen.getEnemy().damage(10f);
            this.dispose();
        }
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

        batch.draw(texture, getX(), getY(), 10, 10);
    }

    @Override
    public void dispose() {
        texture.dispose();
        ((GameScreen) ScreenManager.getCurrentScreen()).removeEntity(this);
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