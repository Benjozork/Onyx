package me.benjozork.onyx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import me.benjozork.onyx.entity.EntityEnemy;
import me.benjozork.onyx.entity.EntityPlayer;
import me.benjozork.onyx.internal.GameUtils;

/**
 * Created by Benjozork on 2017-03-03.
 */
public class GameManager {

     static BitmapFont font = new BitmapFont();

     private static ShapeRenderer shapeRenderer;

     private static SpriteBatch batch = new SpriteBatch();
     private static SpriteBatch hudBatch = new SpriteBatch();

     private static Sprite background;
     private static Sprite lifeIcon;

     private static OrthographicCamera camera;

     private static EntityPlayer player;

     private static List<Entity> registeredEntities = new ArrayList<Entity>();
     private static List<Entity> collidingWithPlayer = new ArrayList<Entity>();
     private static List<Entity> toRemove = new ArrayList<Entity>();

     private static int score = 0, highScore = 0;
     private static int lifeCount = 0, maxLife = 3;
     private static float maxFrameTime;

     private static boolean debugEnabled = false;

     public static void initGame() {
          player = (EntityPlayer) registeredEntities.get(0);
          player.setSpeed(0f);

          background = new Sprite(new Texture("core/assets/hud/background_base.png"));
          background.setPosition(0, 0);
          background.setColor(0f, 0.9f, 0f, 1f);
          background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

          lifeIcon = new Sprite(new Texture("core/assets/hud/ship_silouhette.png"));
          lifeIcon.setScale(0.4f, 0.4f);
     }

     public static void tickGame() {

          if (GameUtils.getDelta() > maxFrameTime) {
               maxFrameTime = GameUtils.getDelta();
          }

          // Draw background

          background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
          hudBatch.disableBlending();
          hudBatch.begin();
          background.draw(hudBatch);
          hudBatch.end();

          // Draw life icons

          hudBatch.enableBlending();
          for (int i = 0; i < maxLife; i ++) {
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
          if (!player.isFiring() && player.getSpeed() == 0f) {
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
               player.fireProjectile("core/assets/bullet.png");
          } if (Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
               toggleDebug();
          }

          camera.position.x = player.getX() + 38;
          camera.position.y = player.getY() + 55;
          camera.update();

          batch.setProjectionMatrix(camera.combined);
          Matrix4 matrix = new Matrix4();
          matrix.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
          hudBatch.setProjectionMatrix(matrix);

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
               e.update(GameUtils.getDelta());
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

          hudBatch.enableBlending();
          hudBatch.begin();

          if (debugEnabled) {
               font.getData().markupEnabled = true;
               font.draw(hudBatch, "[#FFFF00]fps:[] " + Gdx.graphics.getFramesPerSecond() + "  [#FFFF00]maxFrameTime:[] " + maxFrameTime + "   [#FFFF00]loc:[] " + player.getPosition().toString(), 0, Gdx.graphics.getHeight());
               font.draw(hudBatch, "[#FFFF00]entities:[] " + registeredEntities.size() + "   [#FFFF00]ver:[] " + "0.0.1", 0, Gdx.graphics.getHeight() - 20);
               font.draw(hudBatch, "[#FFFF00]player_colision:[] " + sb.toString(), 0, Gdx.graphics.getHeight() - 40);
          }

          hudBatch.end();
     }

     public static List<Entity> getRegisteredEntities() {
          return registeredEntities;
     }

     public static void registerEntity(Entity e) {
          registeredEntities.add(e);
          e.init();
     }

     public static void removeEntity(Entity e) {
          toRemove.add(e);
     }

     public static ShapeRenderer getShapeRenderer() {
          return shapeRenderer;
     }

     public static void setShapeRenderer(ShapeRenderer shapeRenderer) {
          GameManager.shapeRenderer = shapeRenderer;
     }

     public static int getScore() {
          return score;
     }

     public static void setScore(int score) {
          GameManager.score = score;
          if (GameManager.score > highScore) highScore = GameManager.score;
     }

     public static void addScore(int v) {
          GameManager.score += v;
          if (GameManager.score > highScore) highScore = GameManager.score;
     }

     public static int getHighScore() {
          return highScore;
     }

     public static void setHighScore(int highScore) {
          GameManager.highScore = highScore;
     }

     public static SpriteBatch getBatch() {
          return batch;
     }

     public static void setBatch(SpriteBatch batch) {
          GameManager.batch = batch;
     }

     public static OrthographicCamera getCamera() {
          return camera;
     }

     public static void setCamera(OrthographicCamera camera) {
          GameManager.camera = camera;
     }

     public static void toggleDebug() {
          debugEnabled = ! debugEnabled;
     }

     public static EntityPlayer getPlayer() {
          return player;
     }
}
