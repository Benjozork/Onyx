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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.benjozork.onyx.entity.Entity;
import me.benjozork.onyx.entity.EntityPlayer;
import me.benjozork.onyx.internal.GameManager;
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

     private List<Entity> registeredEntities = new ArrayList<Entity>();
     private List<Entity> collidingWithPlayer = new ArrayList<Entity>();
     private List<Entity> toRemove = new ArrayList<Entity>();

     private Sprite background;
     private Sprite lifeIcon;

     private boolean isFading;
     private float deltaRed;
     private float deltaGreen;
     private float deltaBlue;
     private float maxFadeTime = 1f /2, fadeStep;
     private int fadeIndex;
     private Color[] fadeColors = {Color.BLUE, Color.RED, Color.GREEN};

     @Override
     public void show() {
          EntityPlayer player = new EntityPlayer(Utils.getCenterPos(78), 20);
          registerEntity(player);
          this.player = player;
          player.setSpeed(0f);

          background = new Sprite(new Texture("hud/background_base.png"));
          background.setPosition(0, 0);
          background.setColor(currentColor);
          background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

          lifeIcon = new Sprite(new Texture("hud/ship_silouhette.png"));
          lifeIcon.setScale(0.4f, 0.4f);
     }

     @Override
     public void render(float delta) {
          OrthographicCamera worldCam = GameManager.getWorldCamera();
          worldCam.position.x = player.getX() + 38;
          worldCam.position.y = player.getY() + 55;
          worldCam.update();

          if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
               isFading = true;
          }

          // Crossfade
          if (isFading) {
               float totalFadeDelta;aa

               deltaRed = fadeColors[fadeIndex].r - currentColor.r;
               deltaGreen = fadeColors[fadeIndex].g - currentColor.g;
               deltaBlue = fadeColors[fadeIndex].b - currentColor.b;

               fadeStep = maxFadeTime / Utils.delta();

               currentColor.r += (deltaRed / fadeStep);
               currentColor.g += (deltaGreen / fadeStep);
               currentColor.b += (deltaBlue / fadeStep);

               System.out.println(fadeIndex + " (" + (fadeColors[fadeIndex].g - currentColor.g ) * 255f + ")");

               float deltaRed2, deltaGreen2 ,deltaBlue2;
               // Calculate total delta
               if (deltaRed < 0) {
                    deltaRed2 = deltaRed + deltaRed * 2;
               } else {
                    deltaRed2 = deltaRed;
               }

               if (deltaGreen < 0) {
                    deltaGreen2 = deltaGreen + deltaGreen * 2;
               } else {
                    deltaGreen2 = deltaGreen;
               }


               if (deltaBlue < 0) {
                    deltaBlue2 = deltaBlue + deltaBlue * 2;
               } else {
                    deltaBlue2 = deltaBlue;
               }

               totalFadeDelta = deltaRed2 + deltaGreen2 + deltaBlue2;
               totalFadeDelta *= 255f;

               if (totalFadeDelta < 4 && totalFadeDelta > -4) {
                    totalFadeDelta = 5f;
                    fadeIndex += 1;
                    if (fadeIndex + 1 == fadeColors.length) fadeIndex = 0;
                    deltaRed = fadeColors[fadeIndex].r - currentColor.r;
                    deltaGreen = fadeColors[fadeIndex].g - currentColor.g;
                    deltaBlue = fadeColors[fadeIndex].b - currentColor.b;
               }
          }

          if (delta > maxFrameTime) {
               maxFrameTime = delta;
          }

          SpriteBatch batch = GameManager.getBatch();
          OrthographicCamera guiCam = GameManager.getGuiCamera();
          guiCam.update();

          // Draw background
          background.setColor(currentColor);
          background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
          batch.disableBlending();
          batch.setProjectionMatrix(guiCam.combined);
          batch.begin();
          background.draw(batch);
          batch.end();

          // Draw life icons
          batch.enableBlending();
          batch.setProjectionMatrix(worldCam.combined);
          batch.begin();
          for (int i = 0; i < maxLife; i++) {
               lifeIcon.setColor(currentColor);
               lifeIcon.setPosition(20 + i * (lifeIcon.getTexture().getWidth() * 0.5f), 0);
               lifeIcon.draw(batch);
          }
          batch.end();

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
               player.rotate(-5);
          }
          if (Gdx.input.isKeyPressed(Input.Keys.W)) {
               player.accelerate(10f);
          }
          if (Gdx.input.isKeyPressed(Input.Keys.S)) {
               player.accelerate(-10f);
          }
          if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
               player.fireProjectile("bullet.png");
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
               if (collidingWithPlayer.contains(e) && !player.collidesWith(e.getBounds())) {
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
          debugEnabled = !debugEnabled;
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

     public Color getCurrentColor() {
          return currentColor;
     }
}