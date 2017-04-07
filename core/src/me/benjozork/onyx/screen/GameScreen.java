package me.benjozork.onyx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import me.benjozork.onyx.entity.Entity;
import me.benjozork.onyx.entity.EntityEnemy;
import me.benjozork.onyx.entity.EntityPlayer;
import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.specialeffect.crossfade.CrossFadeColorEffect;
import me.benjozork.onyx.specialeffect.crossfade.CrossFadeColorEffectConfiguration;
import me.benjozork.onyx.specialeffect.zoompulse.ZoomPulseEffect;
import me.benjozork.onyx.specialeffect.zoompulse.ZoomPulseEffectConfiguration;
import me.benjozork.onyx.object.TextComponent;
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

    private EntityPlayer player;
    private EntityEnemy enemy;

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

        EntityPlayer player = new EntityPlayer(Utils.getCenterPos(78), 50);
        EntityEnemy enemy = new EntityEnemy(Utils.getCenterPos(50), Gdx.graphics.getHeight() - 100);
        player.setMaxSpeed(1000f);
        enemy.setMaxSpeed(1000f);
        GameScreenManager.registerEntity(player);
        GameScreenManager.registerEntity(enemy);
        this.player = player;
        this.enemy = enemy;
        GameScreenManager.setPlayer(player);

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

        // Update cameras
        //worldCam.position.x = player.getX() + 38;
        //worldCam.position.y = player.getY() + 55;
        worldCam.update();

        guiCam.update();

        // Update DrawState of player
        if (player.isFiring()) {
            player.setState(EntityPlayer.DrawState.FIRING);
        }
        if (player.getSpeed() != 0) {
            player.setState(EntityPlayer.DrawState.MOVING);
            if (player.isFiring()) {
                player.setState(EntityPlayer.DrawState.FIRING_MOVING);
            }
        }
        if (! player.isFiring() && player.getSpeed() == 0f) {
            player.setState(EntityPlayer.DrawState.IDLE);
        }

        // Update input
        if (! Gdx.input.isKeyPressed(Input.Keys.A) && ! Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.setDirection(EntityPlayer.Direction.STRAIGHT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.setDirection(EntityPlayer.Direction.RIGHT);
            player.accelerate(100f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.setDirection(EntityPlayer.Direction.LEFT);
            player.accelerate(100f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.accelerate(100f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.accelerate(-100f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            player.fireProjectile("entity/player/bullet.png");
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

        // Update maxFrametime
        if (delta > maxFrameTime) {
            maxFrameTime = delta;
        }
    }

    public void render(float delta) {

        // Update

        update(delta);

        // Begin batching

        if (! batch.isDrawing()) batch.begin();

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

        // Remove entities that need to be

        GameScreenManager.getEntities().removeAll(GameScreenManager.getEntitiesToRemove());

        batch.end();
        //Collision detection test code
//        Polygon p1 = PolygonHelper.getPolygon((float) (100+Math.random()*400),(float) (100+Math.random()*400),(float) (100+Math.random()*400),(float) (100+Math.random()*400));
//        Polygon p2 = PolygonHelper.getPolygon((float) (100+Math.random()*400),(float) (100+Math.random()*400),(float) (100+Math.random()*400),(float) (100+Math.random()*400));
//        Polygon p1 = new Polygon(new float[]{(float) Math.random()*200f,(float) Math.random()*200f,(float) Math.random()*200f,(float) Math.random()*200f,(float) Math.random()*200f,(float) Math.random()*200f});
//        Polygon p2 = new Polygon(new float[]{(float) Math.random()*200f,(float) Math.random()*200f,(float) Math.random()*200f,(float) Math.random()*200f,(float) Math.random()*200f,(float) Math.random()*200f});
//        GameManager.getShapeRenderer().begin(ShapeRenderer.ShapeType.Line);
//        GameManager.getShapeRenderer().setColor(Color.GREEN);
//        GameManager.getShapeRenderer().polygon(p1.getTransformedVertices());
//        GameManager.getShapeRenderer().setColor(Color.BLUE);
//        GameManager.getShapeRenderer().polygon(p2.getTransformedVertices());
//        PolygonHelper.collidePolygon(p1,p2);
//        GameManager.getShapeRenderer().end();
//        try {
//            Thread.sleep(2500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void resize(int width, int height) {
        worldCam.viewportWidth = width;
        worldCam.viewportHeight = height;
        guiCam.viewportWidth = width;
        guiCam.viewportHeight = height;
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

    public EntityEnemy getEnemy() {
        return enemy;
    }

}