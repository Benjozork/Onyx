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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.benjozork.onyx.entity.Entity;
import me.benjozork.onyx.entity.EntityPlayer;
import me.benjozork.onyx.internal.Utils;

/**
 * Created by Benjozork on 2017-03-19.
 */
public class GameScreen implements Screen {

     private static final Color INITIAL_DRAW_COLOR = Color.GREEN;

     private int score = 0, highScore = 0;
     private int lifeCount = 0, maxLife = 3;
     private float maxFrameTime;

     private boolean debugEnabled = false;

     private BitmapFont font = new BitmapFont();

     private Color currentColor = INITIAL_DRAW_COLOR;

     private EntityPlayer player;

     private OrthographicCamera camera;

     private List<Entity> registeredEntities = new ArrayList<Entity>();
     private List<Entity> collidingWithPlayer = new ArrayList<Entity>();
     private List<Entity> toRemove = new ArrayList<Entity>();

     private ShapeRenderer shapeRenderer = new ShapeRenderer();

     private SpriteBatch batch = new SpriteBatch();
     private SpriteBatch hudBatch = new SpriteBatch();

     private Sprite background;
     private Sprite lifeIcon;

     @Override
     public void show() {
          EntityPlayer player = new EntityPlayer(Utils.getCenterPos(78), 20);
          registerEntity(player);
          this.player = player;
          player.setSpeed(0f);

          background = new Sprite(new Texture("android/assets/hud/background_base.png"));
          background.setPosition(0, 0);
          background.setColor(currentColor);
          background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

          lifeIcon = new Sprite(new Texture("android/assets/hud/ship_silouhette.png"));
          lifeIcon.setScale(0.4f, 0.4f);

          batch.setProjectionMatrix(camera.combined);
          Matrix4 matrix = new Matrix4();
          matrix.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
          hudBatch.setProjectionMatrix(matrix);
          camera.update();
     }

     @Override
     public void render(float delta) {

          camera.position.x = player.getX() + 38;
          camera.position.y = player.getY() + 55;
          camera.update();

          if (delta > maxFrameTime) {
               maxFrameTime = delta;
          }

          // Draw background

          background.setColor(currentColor);
          background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
          hudBatch.disableBlending();
          hudBatch.begin();
          background.draw(hudBatch);
          hudBatch.end();

          // Draw life icons

          hudBatch.enableBlending();
          for (int i = 0; i < maxLife; i++) {
               lifeIcon.setColor(currentColor);
               lifeIcon.setPosition(20 + i * (lifeIcon.getTexture().getWidth() * 0.5f), 0);
               hudBatch.begin();
               lifeIcon.draw(hudBatch);
               hudBatch.end();
          }

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

          // Get player/enemy and modify player, depending on current inputs
          if (Gdx.input.isKeyPressed(Input.Keys.D)) {
               player.rotate(5);
          }
          if (Gdx.input.isKeyPressed(Input.Keys.A)) {
               player.rotate(- 5);
          }
          if (Gdx.input.isKeyPressed(Input.Keys.W)) {
               player.accelerate(10f);
          }
          if (Gdx.input.isKeyPressed(Input.Keys.S)) {
               player.accelerate(- 10f);
          }
          if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
               player.fireProjectile("android/assets/bullet.png");
          }
          if (Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
               toggleDebug();
          }

          // Manage collidingWithPlayer list
          for (Iterator<Entity> it = registeredEntities.iterator(); it.hasNext(); ) {
               Entity e = it.next();

               if (e instanceof EntityPlayer) break;

               if (player.collidesWith(e.getBounds())) {
                    collidingWithPlayer.add(e);
               }
               if (collidingWithPlayer.contains(e) && ! player.collidesWith(e.getBounds())) {
                    collidingWithPlayer.remove(e);
               }

               if (collidingWithPlayer.contains(e) && collidingWithPlayer.size() > 1) {
                    collidingWithPlayer.remove(e);
               }
          }

          //batch.draw(image, x, y);
          Gdx.graphics.setTitle("Onyx 0.0.1 | " + Gdx.graphics.getFramesPerSecond() + " fps, " + registeredEntities.size() + " entities");

          // Update then draw entities and apply deltas

          for (Entity e : registeredEntities) {
               e.update(delta);
          }

          for (Entity e : registeredEntities) {
               e.update();
          }

          for (Entity e : registeredEntities) {
               e.draw();
          }

          registeredEntities.removeAll(toRemove);

          StringBuilder sb = new StringBuilder();
          for (Entity e : collidingWithPlayer) {
               sb.append(e.getClass().getName().replace("me.benjozork.onyx.entity.", ""));
               sb.append(", ");
          }

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

     }

     public void toggleDebug() {
          debugEnabled = ! debugEnabled;
     }

     public List<Entity> getRegisteredEntities() {
          return registeredEntities;
     }

     public void registerEntity(Entity e) {
          registeredEntities.add(e);
          e.init();
     }

     public void removeEntity(Entity e) {
          toRemove.add(e);
     }

     public ShapeRenderer getShapeRenderer() {
          return shapeRenderer;
     }

     public void setShapeRenderer(ShapeRenderer shapeRenderer) {
          shapeRenderer = shapeRenderer;
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

     public SpriteBatch getBatch() {
          return batch;
     }

     public void setBatch(SpriteBatch batch) {
          this.batch = batch;
     }

     public OrthographicCamera getCamera() {
          return camera;
     }

     public void setCamera(OrthographicCamera camera) {
          this.camera = camera;
     }

     public EntityPlayer getPlayer() {
          return player;
     }

     public Color getCurrentColor() {
          return currentColor;
     }
}
