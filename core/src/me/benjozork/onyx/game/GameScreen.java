package me.benjozork.onyx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Array;

import me.benjozork.onyx.GameManager;
import me.benjozork.onyx.OnyxGame;
import me.benjozork.onyx.OnyxInputProcessor;
import me.benjozork.onyx.game.entity.Entity;
import me.benjozork.onyx.game.entity.PlayerEntity;
import me.benjozork.onyx.game.object.LifeIcons;
import me.benjozork.onyx.object.StaticDrawable;
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
 * @see GameScreenManager
 *
 * @author Benjozork
 */
public class GameScreen implements Screen {

    private final Color INITIAL_BACKGROUND_COLOR = Color.RED;

    private float maxFrameTime;

    private TextComponent scoreText;

    private Color backgroundColor = INITIAL_BACKGROUND_COLOR.cpy();

    private Array<Player> players;

    private OrthographicCamera worldCam, guiCam;

    private Sprite background;

    private SpriteBatch batch;

    // Crossfading

    private CrossFadeColorEffect crossFadeBackgroundColor;

    // Zooming

    private ZoomPulseEffect zoomPulseCamera;

    @Override
    public void show() {

        // Setup input processing

        OnyxInputProcessor.setCurrentProcessor(new GameScreenInputProcessor());

        // Setup player

        players = new Array<Player>();

        Player player = new Player(3, new PlayerEntity(Utils.getCenterPos(78), 50));

        player.getPlayerEntity().setMaxSpeed(600f);

        players.add(player);

        for (Player p : players) {
            GameScreenManager.addEntity(p.getPlayerEntity());
        }

        GameScreenManager.setPlayers(players);

        // Setup cameras

        worldCam = GameManager.getWorldCamera();
        guiCam = GameManager.getGuiCamera();

        batch = GameManager.getBatch();

        // Setup background

        background = new Sprite(new Texture("hud/background_base.png"));
        background.setPosition(0, 0);
        background.setColor(backgroundColor);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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

        scoreText = new TextComponent(String.valueOf(GameScreenManager.getPlayers().first().getScore()), OnyxGame.projectConfig.default_font);
        scoreText.getParameter().color = Color.WHITE;
        scoreText.getParameter().borderColor = Color.BLACK;
        scoreText.getParameter().size = 30;
        scoreText.update();
        scoreText.getFont().getData().markupEnabled = true;

    }

    public void update(float delta) {

        // Update DrawState of player

        for (Player p : players) {
            PlayerEntity playerEntity = p.getPlayerEntity();
            if (playerEntity.isFiring()) {
                playerEntity.setState(PlayerEntity.DrawState.FIRING);
            }
            if (playerEntity.getVelocity().len() != 0) {
                playerEntity.setState(PlayerEntity.DrawState.MOVING);
                if (playerEntity.isFiring()) {
                    playerEntity.setState(PlayerEntity.DrawState.FIRING_MOVING);
                }
            }
            if (!playerEntity.isFiring() && playerEntity.getVelocity().len() == 0f) {
                playerEntity.setState(PlayerEntity.DrawState.IDLE);
            }
        }

        // Update crossfade

        crossFadeBackgroundColor.update();

        // Update zoom pulse

        zoomPulseCamera.update();

        scoreText.setText(String.valueOf(GameScreenManager.getPlayers().first().getScore() + " / [#CCCCCC]" + GameScreenManager.getPlayers().first().getHighScore()));

        if (GameScreenManager.getEnemies().size == 0) GameScreenManager.generateRandomEnemyWave(1, 3, 0, 1920, 500, 1200);

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
        LifeIcons.draw(batch, backgroundColor, 10, 0, 0.6f);

        // Draw score text

        scoreText.draw(batch, Gdx.graphics.getWidth() - scoreText.getLayout().width - 20, Gdx.graphics.getHeight() - scoreText.getLayout().height - 10);

        batch.setProjectionMatrix(worldCam.combined);

        // Update then draw entities

        for (Entity e : GameScreenManager.getEntities()) {
            e.update(delta); // This call updates the Drawable class internally
        }

        for (StaticDrawable sd : GameScreenManager.getStaticObjects()) {
            sd.update(delta);
        }

        for (Entity e : GameScreenManager.getEntities()) {
            e.update();
        }

        for (StaticDrawable sd : GameScreenManager.getStaticObjects()) {
            sd.update();
        }

        for (Entity e : GameScreenManager.getEntities()) {
            e.draw();
        }

        for (StaticDrawable sd : GameScreenManager.getStaticObjects()) {
            sd.draw();
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