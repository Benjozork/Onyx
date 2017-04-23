package me.benjozork.onyx.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import me.benjozork.onyx.GameManager;
import me.benjozork.onyx.PolygonLoader;
import me.benjozork.onyx.game.GameScreenManager;
import me.benjozork.onyx.game.entity.ai.AI;
import me.benjozork.onyx.game.entity.ai.AIConfiguration;
import me.benjozork.onyx.game.entity.ai.AIShootingConfiguration;
import me.benjozork.onyx.game.object.HealthBar;
import me.benjozork.onyx.game.weapon.impl.SimpleCannon;
import me.benjozork.onyx.utils.Utils;

/**
 * @author Benjozork
 */
public class EnemyEntity extends LivingEntity {

    // Enemy textures

    private final Texture ENEMY_TEXTURE = new Texture("entity/enemy/texture_0.png");
    private final Texture FIRING_ENEMY_TEXTURE = new Texture("entity/enemy/texture_1.png");
    private final Texture MOVING_FIRING_ENEMY_TEXTURE = new Texture("entity/enemy/texture_3.png");
    private final Texture MOVING_ENEMY_TEXTURE = new Texture("entity/enemy/texture_2.png");

    private Sprite currentTexture = new Sprite(ENEMY_TEXTURE);

    private DrawState state = DrawState.IDLE;

    private float spriteRotation;

    private AI ai;

    private HealthBar healthBar = new HealthBar(this, 100f, 10f, 100f);

    private boolean debug = true;

    private boolean isFiring = false;

    public EnemyEntity(float x, float y) {
        super(x, y);

        /* TEMPORARY */

        addWeapon(new SimpleCannon(this));

        /* END TEMPORARY */
    }

    @Override
    public void init() {

        // Initialize hitbox

        bounds = PolygonLoader.getPolygon("Enemy", ENEMY_TEXTURE.getWidth(), ENEMY_TEXTURE.getHeight());

        // Flip texture upside down

        currentTexture.flip(false, true);

        // Setup AI

        AIConfiguration aiConfiguration = new AIConfiguration();
        aiConfiguration.strategy = AIConfiguration.AIStrategy.LINEAR;
        aiConfiguration.reluctance = AIConfiguration.ProjectileReluctance.MED;
        aiConfiguration.source = this;

        // For now, the AI's target is set to the first player, change it as required later
        aiConfiguration.target = GameScreenManager.getLocalPlayerEntity();

        aiConfiguration.factor = 100f;
        AIShootingConfiguration shootingConfiguration = new AIShootingConfiguration();
        shootingConfiguration.minStreakDelay = 1.5f;
        shootingConfiguration.maxStreakDelay = 3f;
        shootingConfiguration.minStreakTime = 1.5f;
        shootingConfiguration.maxStreakTime = 3f;
        shootingConfiguration.resetTime = 5f;
        shootingConfiguration.shootInterval = 0.1f;
        shootingConfiguration.minImprecision = -30f;
        shootingConfiguration.maxImprecision = 30f;
        shootingConfiguration.recalculateImprecisionRandomly = true;
        shootingConfiguration.minTargetTrackingDelta = 900f;
        shootingConfiguration.maxTargetTrackingDelta = 900f;
        aiConfiguration.shootingConfig = shootingConfiguration;

        ai = new AI(aiConfiguration);
        type = Type.ENEMY;

        setBulletShootOrigin(ENEMY_TEXTURE.getWidth() / 2, ENEMY_TEXTURE.getHeight() / 2);

        // Init health bar

        GameScreenManager.getStaticObjects().add(healthBar);

    }

    @Override
    public void update() {
        super.update();

        ai.update(Utils.delta());

        if (velocity.len() > 10f) {
            setState(DrawState.MOVING);
            if (isFiring()) setState(DrawState.FIRING_MOVING);
        }
        else if (isFiring()) setState(DrawState.FIRING);
        else setState(DrawState.IDLE);

        // Update texture

        if (state == DrawState.IDLE) {
            currentTexture.setTexture(ENEMY_TEXTURE);
        } else if (state == DrawState.MOVING) {
            currentTexture.setTexture(MOVING_ENEMY_TEXTURE);
        } else if (state == DrawState.FIRING) {
            currentTexture.setTexture(FIRING_ENEMY_TEXTURE);
        } else if (state == DrawState.FIRING_MOVING) {
            currentTexture.setTexture(MOVING_FIRING_ENEMY_TEXTURE);
        }

        currentTexture.setPosition(getX(), getY());
        currentTexture.setRotation(- getRotation() * MathUtils.radiansToDegrees);

        // Rotate bounds

        bounds.setRotation(- getRotation() * MathUtils.radiansToDegrees);

    }

    @Override
    public void draw() {
        SpriteBatch batch = GameManager.getBatch();
        currentTexture.draw(batch);
    }

    @Override
    public void dispose() {
        GameScreenManager.removeEntity(this);
        GameScreenManager.getStaticObjects().removeValue(this.healthBar, true);
    }

    /**
     * The current DrawState
     * @return the state
     */
    public DrawState getState() {
        return state;
    }

    /**
     * Sets the current DrawState
     * @param v the state to be used
     */
    public void setState(DrawState v) {
        this.state = v;
    }

    @Override
    public float getTextureWidth() {
        return ENEMY_TEXTURE.getWidth();
    }

    @Override
    public float getTextureHeight() {
        return ENEMY_TEXTURE.getHeight();
    }

    public AI getAI() {
        return ai;
    }

    public enum DrawState {
        IDLE,
        FIRING,
        MOVING,
        FIRING_MOVING,
    }

}
