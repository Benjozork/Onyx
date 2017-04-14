package me.benjozork.onyx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.entity.EnemyEntity;
import me.benjozork.onyx.entity.Entity;
import me.benjozork.onyx.entity.PlayerEntity;
import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.object.TextComponent;
import me.benjozork.onyx.specialeffect.crossfade.CrossFadeColorEffect;
import me.benjozork.onyx.specialeffect.crossfade.CrossFadeColorEffectConfiguration;
import me.benjozork.onyx.specialeffect.zoompulse.ZoomPulseEffect;
import me.benjozork.onyx.specialeffect.zoompulse.ZoomPulseEffectConfiguration;
import me.benjozork.onyx.utils.PolygonHelper;
import me.benjozork.onyx.utils.Utils;

/**
 * Manages the logic when a level is being played.<br/>
 * Use {@link GameScreenManager} to interact with this {@link Screen}'s contents.
 *
 * @author Benjozork
 */
public class GameScreen implements Screen {

    private final Color INITIAL_BACKGROUND_COLOR = Color.RED;

    private float maxFrameTime;

    private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private TextComponent scoreText;

    private Color backgroundColor = INITIAL_BACKGROUND_COLOR.cpy();

    private PlayerEntity player;
    private EnemyEntity enemy;

    private OrthographicCamera worldCam, guiCam;

    private Sprite background;
    private Sprite lifeIcon;

    private SpriteBatch batch;

    // Crossfading

    private CrossFadeColorEffect crossFadeBackgroundColor;

    // Zooming

    private ZoomPulseEffect zoomPulseCamera;

    @Override
    public void show() {

        // Setup player

        PlayerEntity player = new PlayerEntity(Utils.getCenterPos(78), 50);
        GameScreenManager.setPlayer(player);
        player.setMaxSpeed(600f);
        GameScreenManager.addEntity(player);
        this.player = player;
        this.enemy = enemy;

        // Setup cameras

        worldCam = GameManager.getWorldCamera();
        guiCam = GameManager.getGuiCamera();

        batch = GameManager.getBatch();

        // Setup background

        background = new Sprite(new Texture("hud/background_base.png"));
        background.setPosition(0, 0);
        background.setColor(backgroundColor);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Setup life icons

        lifeIcon = new Sprite(new Texture("hud/ship_silouhette.png"));
        lifeIcon.setScale(0.4f, 0.4f);

        // Setup CrossFadeColorEffect

        CrossFadeColorEffectConfiguration crossFadeConfig = new CrossFadeColorEffectConfiguration();
        crossFadeConfig.cycleColors.addAll(Color.BLUE, Color.RED, Color.GREEN);
        crossFadeConfig.crossFadeTime = .5f;
        crossFadeConfig.crossFadeDeltaTimeStepRequirement = 32f;
        crossFadeConfig.fadeOutDeltaMultiplier = 3f;
        crossFadeBackgroundColor = new CrossFadeColorEffect(crossFadeConfig, backgroundColor);

        // Setup ZoomPulseEffect

        ZoomPulseEffectConfiguration zoomPulseConfig = new ZoomPulseEffectConfiguration();
        zoomPulseConfig.maxZoomTime = 1f;
        zoomPulseConfig.targetZoom = 0.5f;
        zoomPulseCamera = new ZoomPulseEffect(zoomPulseConfig, worldCam, guiCam);

        scoreText = new TextComponent(String.valueOf(GameScreenManager.getScore()));

    }

    public void update(float delta) {

        // Update DrawState of player

        if (player.isFiring()) {
            player.setState(PlayerEntity.DrawState.FIRING);
        }
        if (player.getVelocity().len() != 0) {
            player.setState(PlayerEntity.DrawState.MOVING);
            if (player.isFiring()) {
                player.setState(PlayerEntity.DrawState.FIRING_MOVING);
            }
        }
        if (! player.isFiring() && player.getVelocity().len() == 0f) {
            player.setState(PlayerEntity.DrawState.IDLE);
        }

        // Update input

        if (! Gdx.input.isKeyPressed(Input.Keys.A) && ! Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.setDirection(PlayerEntity.Direction.STRAIGHT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.setDirection(PlayerEntity.Direction.RIGHT);
            player.accelerate(1000f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.setDirection(PlayerEntity.Direction.LEFT);
            player.accelerate(1000f);
        }
        /*if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.accelerate(100f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.accelerate(-100f);
        }*/
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            Vector2 mouse = Utils.unprojectWorld(Gdx.input.getX(), Gdx.input.getY());
            player.fireProjectileAt("entity/player/bullet.png", mouse.x, mouse.y);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (crossFadeBackgroundColor.isActive()) crossFadeBackgroundColor.pause();
            else crossFadeBackgroundColor.resume();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ALT_LEFT)) {
            zoomPulseCamera.toggle();
        }

        // Update crossfade

        crossFadeBackgroundColor.update();

        // Update zoom pulse

        zoomPulseCamera.update();

        scoreText.setText(String.valueOf(GameScreenManager.getScore()));

        if (GameScreenManager.getEnemies().size == 0) GameScreenManager.generateRandomEnemyWave(5, 15, 0, 1920, 500, 1200);

        // Update maxFrametime
        if (delta > maxFrameTime) {
            maxFrameTime = delta;
        }
    }

    public void render(float delta) {

        if (GameScreenManager.isDisposing()) {
            GameScreenManager.dispose();
            return;
        }

        // Update

        update(delta);

        // Draw background

        background.setColor(backgroundColor);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.disableBlending();
        batch.setProjectionMatrix(guiCam.combined);
        background.draw(batch);

        // Draw life icons

        batch.enableBlending();
        for (int i = 0; i < GameScreenManager.getMaxLives(); i++) {
            lifeIcon.setColor(backgroundColor);
            lifeIcon.setPosition(20 + i * (lifeIcon.getTexture().getWidth() * 0.5f), 0);
            lifeIcon.draw(batch);
        }

        // Draw score text

        scoreText.draw(batch, Gdx.graphics.getWidth() - 20, 20);

        batch.setProjectionMatrix(worldCam.combined);

        // Update then draw entities

        for (Entity e : GameScreenManager.getEntities()) {
            e.update(delta); // This call updates the Drawable class internally
        }

        for (Entity e : GameScreenManager.getEntities()) {
            e.update();
        }

        for (Entity e : GameScreenManager.getEntities()) {
            e.draw();
        }

		 /*
         Note for all: Whenever collision seems to stop working,
         instead of thinking the system is broken, check for some
         subtle errors in your code, but just to be sure, you may
         uncomment the next line
         */

        //collisionCheck();


        // Remove entities that need to be

        GameScreenManager.flushEntities();

        batch.end();
        GameManager.getRenderer().end();

    }

    @Override
    public void resize(int width, int height) {
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        GameScreenManager.dispose();
        scoreText.dispose();
    }

    public EnemyEntity getEnemy() {
        return enemy;
    }

    private void collisionCheck() {
        //Collision detection test code
        Polygon p1 = PolygonHelper.getPolygon((float) (100+Math.random()*400),(float) (100+Math.random()*400),(float) (100+Math.random()*400),(float) (100+Math.random()*400));
        Polygon p2 = PolygonHelper.getPolygon((float) (100+Math.random()*400),(float) (100+Math.random()*400),(float) (100+Math.random()*400),(float) (100+Math.random()*400));
//        Polygon p1 = new Polygon(new float[]{(float) Math.random()*200f,(float) Math.random()*200f,(float) Math.random()*200f,(float) Math.random()*200f,(float) Math.random()*200f,(float) Math.random()*200f});
//        Polygon p2 = new Polygon(new float[]{(float) Math.random()*200f,(float) Math.random()*200f,(float) Math.random()*200f,(float) Math.random()*200f,(float) Math.random()*200f,(float) Math.random()*200f});
        GameManager.getRenderer().setColor(Color.GREEN);
        GameManager.getRenderer().polygon(p1.getTransformedVertices());
        GameManager.getRenderer().setColor(Color.BLUE);
        GameManager.getRenderer().polygon(p2.getTransformedVertices());
        System.out.println(PolygonHelper.collidePolygon(p1,p2));
    }

}