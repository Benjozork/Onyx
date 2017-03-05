package me.benjozork.onyx.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import me.benjozork.onyx.entity.Entity;
import me.benjozork.onyx.entity.EntityEnemy;
import me.benjozork.onyx.entity.EntityPlayer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Benjozork on 2017-03-03.
 */
public class GameManager {

    private static ShapeRenderer shapeRenderer;
    private static SpriteBatch batch = new SpriteBatch();
    static BitmapFont font = new BitmapFont();

    private static EntityPlayer player;
    private static EntityEnemy enemy;

    private static List<Entity> registeredEntities = new ArrayList<Entity>();
    private static List<Entity> collidingWithPlayer = new ArrayList<Entity>();

    private static int score = 0, highScore = 0;

    public static void initGame() {
        player = (EntityPlayer) registeredEntities.get(0);
        enemy = (EntityEnemy) registeredEntities.get(1);
    }

    public static void tickGame() {

        StringBuilder sb = new StringBuilder();
        for (Entity e : collidingWithPlayer) {
            sb.append(e.getClass().getName().replace("me.benjozork.onyx.entity.", ""));
            sb.append(", ");
        }

        batch.begin();

        font.getData().markupEnabled = true;
        font.draw(batch, "[#FFFF00]fps:[] " + Gdx.graphics.getFramesPerSecond() + "   [#FFFF00]loc:[] " + player.getPos().toString(), 0, Gdx.graphics.getHeight());
        font.draw(batch, "[#FFFF00]entities:[] " + registeredEntities.size() + "   [#FFFF00]ver:[] " + "0.0.1",0, Gdx.graphics.getHeight() - 20);
        font.draw(batch, "[#FFFF00]player_colision:[] " + sb.toString(), 0, Gdx.graphics.getHeight() - 40);

        batch.end();

        // Get player/enemy and modify player, depending on current inputs
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.move(500, 0);
        } if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.move(-500, 0);
        } if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.move(0, 500);
        } if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.move(0, -500);
        } if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            player.fireProjectile("android/assets/bullet.jpg", 0, 500, 0.3f);
        }

        for (Iterator<Entity> it = registeredEntities.iterator(); it.hasNext();) {
            Entity e = it.next();

            if (player.collidesWith(e)) {
                collidingWithPlayer.add(e);
            } if (collidingWithPlayer.contains(e) && !player.collidesWith(e)) {
                collidingWithPlayer.remove(e);
            }

            if (collidingWithPlayer.contains(e) && collidingWithPlayer.size() > 1) {
                collidingWithPlayer.remove(e);
            }
        }

        //batch.draw(image, x, y);
        Gdx.graphics.setTitle("Onyx 0.0.1 | " + Gdx.graphics.getFramesPerSecond() + " fps, " + registeredEntities.size() + " entities");

        // Draw entities and apply deltas
        for (Entity e : registeredEntities) {
            e.setX(e.getX() + e.getDx());
            e.setY(e.getY() + e.getDy());
            e.draw();
        }

        for (Iterator<Entity> it = registeredEntities.iterator(); it.hasNext();) {
            Entity e = it.next();

            if (e.getX() > Gdx.graphics.getWidth()) it.remove();
        }
    }

    public static void setBatch(SpriteBatch batch) {
        GameManager.batch = batch;
    }

    public static void setShapeRenderer(ShapeRenderer shapeRenderer) {
        GameManager.shapeRenderer = shapeRenderer;
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
}
