package me.benjozork.onyx.screen;

import java.util.ArrayList;
import java.util.List;

import me.benjozork.onyx.entity.EnemyEntity;
import me.benjozork.onyx.entity.Entity;
import me.benjozork.onyx.entity.LivingEntity;
import me.benjozork.onyx.entity.PlayerEntity;
import me.benjozork.onyx.entity.ProjectileEntity;
import me.benjozork.onyx.entity.ProjectileManager;
import me.benjozork.onyx.internal.ScreenManager;

/**
 * Allows to interact with a {@link GameScreen} and it's properties.<br/>
 * All of the methods of this class except {@link GameScreenManager#exists()} and<br/>
 * {@link GameScreenManager#dispose()}throw an {@link IllegalStateException}<br/>
 * if {@link ScreenManager#getCurrentScreen()} DOES NOT return {@link GameScreen}.
 *
 * @author Benjozork
 */
public class GameScreenManager {

    private static PlayerEntity player;
    private static EnemyEntity enemy;

    private static List<Entity> registeredEntities = new ArrayList<Entity>();
    private static List<Entity> toRemove = new ArrayList<Entity>();

    private static int score = 0;
    private static int highScore = 0;

    private static int maxLives = 3;
    private static int lifeCount = maxLives;

    static {
        ProjectileManager.init();
    }

    /**
     * Returns the {@link PlayerEntity} instance used by {@link GameScreen}
     * @return the player instance
     */
    public static PlayerEntity getPlayer() {
        check();
        return player;
    }

    /**
     * Sets the {@link PlayerEntity} instance to be used by {@link GameScreen}
     * @param p the player instance
     */
    public static void setPlayer(PlayerEntity p) {
        player = p;
    }

    public static LivingEntity getEnemy() {
        return enemy;
    }

    public static void setEnemy(EnemyEntity e) {
        enemy = e;
    }


    /**
     * Returns a {@link List} of entities that are present on the entity stack
     * @return the entities present on the entity stack
     */
    public static List<Entity> getEntities() {
        check();
        return registeredEntities;
    }

    /**
     * Adds an {@link Entity} to the entity stack and calls {@link Entity#init()} on said entity
     * @param e the entity to be added
     */
    public static void registerEntity(Entity e) {
        check();
        addEntity(e);
        e.init();
    }

    private static void addEntity(Entity e) {
        check();
        if (! registeredEntities.contains(e)){
            registeredEntities.add(e);
            if(e instanceof ProjectileEntity)
                ProjectileManager.addProjectile((ProjectileEntity)e);
        }
    }

    /**
     * Adds a given {@link Entity} to the list of entities that need to be removed from the entity stack
     * @param e the entity to be removed
     */
    public static void removeEntity(Entity e) {
        check();
        if (e instanceof EnemyEntity)  // Remove enemy
            enemy = null;
        if (e instanceof PlayerEntity) // Remove player
            player = null;
        toRemove.add(e);
        if( e instanceof ProjectileEntity) // Remove projectile
            ProjectileManager.removeProjectile((ProjectileEntity) e);
    }

    /**
     * Returns a {@link List} of entities that have been called to be removed from the entity stack
     * @return the list of entities to be removed
     */
    public static List<Entity> getEntitiesToRemove() {
        return toRemove;
    }

    public static int getScore() {
        check();
        return score;
    }

    public static void setScore(int v) {
        check();
        score = v;
        updateHighScore();
    }

    public static void addScore(int v) {
        check();
        score += v;
        updateHighScore();
    }


    public int getHighScore() {
        check();
        return highScore;
    }

    public static void setHighScore(int highScore) {
        check();
        highScore = highScore;
    }

    private static void updateHighScore() {
        if (score > highScore) highScore = score;
    }

    public static int getLives() {
        check();
        return lifeCount;
    }

    public static void setLives(int i) {
        check();
        lifeCount = i;
    }

    public static int getMaxLives() {
        check();
        return maxLives;
    }

    public static void setMaxLives(int i) {
        check();
        maxLives = i;
    }

    /**
     * Checks whether a {@link GameScreen} is currently being played
     */
    public static boolean exists() {
        return ScreenManager.getCurrentScreen() instanceof GameScreen;
    }

    /**
     * Calls {@link GameScreenManager#exists()} and throws an exception if it returns <code>false</code>
     */
    private static void check() {
        if (! exists()) throw new IllegalStateException("current screen must be GameScreen");
    }

    /**
     * Flushes the object cache when {@link GameScreen} is disposed of
     */
    public static void dispose() {
        player = null;

        for (Entity e : GameScreenManager.getEntities()) {
            e.dispose();
        }

        registeredEntities = new ArrayList<Entity>();
        toRemove = new ArrayList<Entity>();

        score = 0;
        highScore = 0;

        maxLives = 0;
        lifeCount = 0;
    }

}
