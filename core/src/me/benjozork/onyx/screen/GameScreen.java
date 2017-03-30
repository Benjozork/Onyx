package me.benjozork.onyx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

import me.benjozork.onyx.OnyxGame;
import me.benjozork.onyx.entity.Entity;
import me.benjozork.onyx.entity.EntityEnemy;
import me.benjozork.onyx.entity.EntityPlayer;
import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.internal.console.Console;
import me.benjozork.onyx.specialeffect.crossfade.CrossFadeColorEffect;
import me.benjozork.onyx.specialeffect.crossfade.CrossFadeColorEffectConfiguration;
import me.benjozork.onyx.utils.Logger;
import me.benjozork.onyx.utils.Utils;

/**
 * Created by Benjozork on 2017-03-19.
 */
public class GameScreen implements Screen {

    private final Color INITIAL_BACKGROUND_COLOR = Color.RED;

    private int score = 0, highScore = 0;
    private int lifeCount = 0, maxLife = 3;
    private float maxFrameTime;

    private boolean debugEnabled = false;

    private BitmapFont font = new BitmapFont();

    private Color backgroundColor = INITIAL_BACKGROUND_COLOR.cpy();

    private EntityPlayer player;

    private List<Entity> registeredEntities = new ArrayList<Entity>();
    private List<Entity> collidingWithPlayer = new ArrayList<Entity>();
    private List<Entity> toRemove = new ArrayList<Entity>();

    private OrthographicCamera worldCam, guiCam;

    private Sprite background;
    private Sprite lifeIcon;

    private SpriteBatch batch;

    // Crossfading
    private CrossFadeColorEffect crossFadeBackgroundColor;

    // Camera zoom pulse
    private boolean isZooming, zoomBack;
    private float deltaZoom;
    private float maxZoomTime = 0.1f / 3, zoomStep;
    private float targetZoom = 0.8f;

    @Override
    public void show() {
        // Setup player
        EntityPlayer player = new EntityPlayer(Utils.getCenterPos(78), 50);
        EntityEnemy enemy = new EntityEnemy(Utils.getCenterPos(50), Gdx.graphics.getHeight() - 100);
        player.setMaxSpeed(1000f);
        registerEntity(player);
        registerEntity(enemy);
        this.player = player;
        GameManager.setPlayer(player);

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
        crossFadeBackgroundColor = new CrossFadeColorEffect(backgroundColor, crossFadeConfig);
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
        if (!player.isFiring() && player.getSpeed() == 0f) {
            player.setState(EntityPlayer.DrawState.IDLE);
        }

        // Update input
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.setDirection(EntityPlayer.Direction.RIGHT);
            player.accelerate(100f);
        } else {
            player.setDirection(EntityPlayer.Direction.STRAIGHT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.setDirection(EntityPlayer.Direction.LEFT);
            player.accelerate(100f);
        } else if (! Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.setDirection(EntityPlayer.Direction.STRAIGHT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.accelerate(100f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.accelerate(-100f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            player.fireProjectile("bullet.png");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (crossFadeBackgroundColor.isActive()) crossFadeBackgroundColor.pause();
            else crossFadeBackgroundColor.resume();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ALT_LEFT)) {
            isZooming = true;
        }

        // Update crossfade
        crossFadeBackgroundColor.update();

        // Update zoom
        if (isZooming) {
            if (zoomBack) {
                deltaZoom = -(targetZoom - worldCam.zoom);
            } else {
                deltaZoom = targetZoom - worldCam.zoom;
            }

            zoomStep = maxZoomTime / Utils.delta();

            worldCam.zoom += (deltaZoom / zoomStep);
            guiCam.zoom += (deltaZoom / zoomStep); // Make it possible to only zoom background. Sprite#scale() ?

            if (deltaZoom > -0.05f) {
                zoomBack = true;
            }
            if (worldCam.zoom > 1f || guiCam.zoom > 1f) {
                worldCam.zoom = 1f;
                guiCam.zoom = 1f;
                zoomBack = false;
                isZooming = false;
            }
        }

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
        for (int i = 0; i < maxLife; i++) {
            lifeIcon.setColor(backgroundColor);
            lifeIcon.setPosition(20 + i * (lifeIcon.getTexture().getWidth() * 0.5f), 0);
            lifeIcon.draw(batch);
        }

       batch.setProjectionMatrix(worldCam.combined);

        // Set title
        Gdx.graphics.setTitle("Onyx " + OnyxGame.VERSION + " | " + Gdx.graphics.getFramesPerSecond() + " fps, " + registeredEntities.size() + " entities");

        // Update then draw entities
        for (Entity e : registeredEntities) {
            e.update(delta); // This call updates the Drawable class internally
        }

        for (Entity e : registeredEntities) {
            e.update();
        }

        for (Entity e : registeredEntities) {
            e.draw();
        }

        // Remove entities that need to be
        registeredEntities.removeAll(toRemove);

        batch.end();
    }


    @Override
    public void resize(int width, int height) {

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
        for (Entity e : registeredEntities) {
            e.dispose();
        }
    }

    public void toggleDebug() {
        debugEnabled = !debugEnabled;
        if (debugEnabled) Logger.log("Debug mode  [#00FF00]enabled");
        else Logger.log("Debug mode  [#FF0000]disabled");
    }

    public void registerEntity(Entity e) {
        registeredEntities.add(e);
        e.init();
    }

    public List<Entity> getRegisteredEntities() {
        return registeredEntities;
    }

    public void removeEntity(Entity e) {
        toRemove.add(e);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        if (this.score > highScore) highScore = score;
    }

    public void addScore(int v) {
        score += v;
        if (score > highScore) highScore = score;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public EntityPlayer getPlayer() {
        return player;
    }
}
