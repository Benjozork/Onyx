package me.benjozork.onyx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

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
     private static OrthographicCamera camera;
     private static EntityPlayer player;
     private static EntityEnemy enemy;

     private static List<Entity> registeredEntities = new ArrayList<Entity>();
     private static List<Entity> collidingWithPlayer = new ArrayList<Entity>();
     private static List<Entity> toRemove = new ArrayList<Entity>();

     private static int score = 0, highScore = 0;

     public static void initGame() {
          player = (EntityPlayer) registeredEntities.get(0);
          player.setSpeed(100f);
          enemy = (EntityEnemy) registeredEntities.get(1);
     }

     public static void tickGame() {

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
               player.fireProjectile("android/assets/bullet.jpg", Gdx.graphics.getWidth() - Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), 3000f, 0.3f);
          }

          camera.position.x = player.getX() + 38;
          camera.position.y = player.getY() + 55;
          camera.update();
          batch.setProjectionMatrix(camera.combined);
          shapeRenderer.setProjectionMatrix(camera.combined);

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

          StringBuilder sb = new StringBuilder();
          for (Entity e : collidingWithPlayer) {
               sb.append(e.getClass().getName().replace("me.benjozork.onyx.entity.", ""));
               sb.append(", ");
          }

          hudBatch.begin();

          font.getData().markupEnabled = true;
          font.draw(hudBatch, "[#FFFF00]fps:[] " + Gdx.graphics.getFramesPerSecond() + "   [#FFFF00]loc:[] " + player.getPosition().toString(), 0, Gdx.graphics.getHeight());
          font.draw(hudBatch, "[#FFFF00]entities:[] " + registeredEntities.size() + "   [#FFFF00]ver:[] " + "0.0.1", 0, Gdx.graphics.getHeight() - 20);
          font.draw(hudBatch, "[#FFFF00]player_colision:[] " + sb.toString(), 0, Gdx.graphics.getHeight() - 40);

          hudBatch.end();
     }

     public static List<Entity> getRegisteredEntities() {
          return registeredEntities;
     }

     public static void registerEntity(Entity e) {
          registeredEntities.add(e);
          e.init();
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
}
