package me.benjozork.onyx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.game.GameScreenManager;
import me.benjozork.onyx.utils.PolygonHelper;
import me.benjozork.onyx.utils.Utils;

/**
 * @author Benjozork
 */
public class ProjectileEntity extends Entity {

    private Vector2 target;

    private SpriteBatch batch;

    private float damage = 0;

    private String texturePath = "entity/player/bullet.png";
    private Texture texture = new Texture(texturePath);

    public LivingEntity.Type source;

    public ProjectileEntity(float x, float y, float targetx, float targety) {
        super(x, y);
        this.target = new Vector2(targetx, targety);
    }

    @Override
    public void init() {
        batch = GameManager.getBatch();

        // Initialize hitbox

        bounds = PolygonHelper.getPolygon(getX(), getY(), 10, 10);

        // Set velocity accordingly to target

        velocity.set(target.sub(getX(), getY()));
    }

    @Override
    public void update() {
        if (getX() < 0 || getX() > Gdx.graphics.getWidth() || getY() < 0 || getY() > Gdx.graphics.getHeight()) {
            dispose();
        }
        if (GameScreenManager.getEnemies().size == 0) return;
        for (EnemyEntity enemy : GameScreenManager.getEnemies()) {
            if (this.collidesWith(enemy.getBounds()) && enemy.type != source) {
                enemy.damage(10f);
                this.dispose();
            }
        }
    }

    @Override
    public void draw() {
        batch.draw(texture, bounds.getX(), bounds.getY(), 10, 10);
    }

    @Override
    public void dispose() {
        texture.dispose();
        GameScreenManager.removeEntity(this);
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