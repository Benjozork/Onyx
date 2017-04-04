package me.benjozork.onyx.screen;

import java.util.ArrayList;
import java.util.List;

import me.benjozork.onyx.entity.Entity;
import me.benjozork.onyx.entity.EntityPlayer;
import me.benjozork.onyx.internal.ScreenManager;

/**
 * Allows to interact with a current {@link GameScreen} and it's properties
 * @author Benjozork
 */
public class GameScreenManager {

    private static EntityPlayer player;
    private static List<Entity> registeredEntities = new ArrayList<Entity>();
    private static List<Entity> toRemove;

    private static int score = 0;
    private static int highScore = 0;

    private static int maxLives = 3;
    private static int lifeCount = maxLives;

    public static EntityPlayer getPlayer() {
        check();
        return player;
    }

    public static List<Entity> getEntities() {
        check();
        return registeredEntities;
    }

    public static void registerEntity(Entity e) {
        check();
        addEntity(e);
        e.init();
    }

    private static void addEntity(Entity e) {
        if (! registeredEntities.contains(e)) registeredEntities.add(e);
    }

    public static void removeEntity(Entity e) {
        check();
        toRemove.add(e);
    }

    public static List<Entity> getEntitiesToRemove() {
        return toRemove;
    }

    public static int getScore() {
        check();
        return score;
    }

    public static void setScore(int score) {
        check();
        score = score;
        if (score > highScore) highScore = score;
    }

    public static void addScore(int v) {
        check();
        score += v;
        if (score > highScore) highScore = score;
    }

    public int getHighScore() {
        check();
        return highScore;
    }

    public static void setHighScore(int highScore) {
        check();
        highScore = highScore;
    }

    public static int getLives() {
        return lifeCount;
    }

    public static void setLives(int i) {
        lifeCount = i;
    }

    public static int getMaxLives() {
        return maxLives;
    }

    public static void setMaxLives(int i) {
        maxLives = i;
    }

    /**
     * Checks whether a {@link GameScreen} is currently being played
     */
    public static boolean exists() {
        return ScreenManager.getCurrentScreen() instanceof GameScreen;
    }

    private static void check() {
        if (! exists()) throw new IllegalStateException("current screen must be GameScreen");
    }


}
