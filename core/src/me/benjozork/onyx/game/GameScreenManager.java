package me.benjozork.onyx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import java.util.List;
import java.util.Random;

import me.benjozork.onyx.ScreenManager;
import me.benjozork.onyx.game.entity.EnemyEntity;
import me.benjozork.onyx.game.entity.Entity;
import me.benjozork.onyx.game.entity.LivingEntity;
import me.benjozork.onyx.game.entity.PlayerEntity;
import me.benjozork.onyx.game.entity.ProjectileManager;
import me.benjozork.onyx.logger.Log;
import me.benjozork.onyx.object.StaticDrawable;
import me.benjozork.onyx.screen.GameOverScreen;

/**
 * Allows to interact with a {@link GameScreen} and it's properties.<br/>
 * All of the methods of this class except {@link GameScreenManager#exists()} and<br/>
 * {@link GameScreenManager#dispose()}throw an {@link IllegalStateException}<br/>
 * if {@link ScreenManager#getCurrentScreen()} DOES NOT return {@link GameScreen}.
 *
 * @see GameScreen
 *
 * @author Benjozork
 */
public class GameScreenManager {

    private static Array<Player> players;

    private static Array<EnemyEntity> enemies = new Array<EnemyEntity>();
    private static Array<EnemyEntity> enemiesToRemove = new Array<EnemyEntity>();

    private static Array<Entity> entities = new Array<Entity>();
    private static Array<Entity> entitiesToRemove = new Array<Entity>();

    private static Array<StaticDrawable> staticObjects = new Array<StaticDrawable>();

    private static Log log = Log.create("GameScreenManager");

    private static boolean checking = true;

    static {
        ProjectileManager.init();
    }

    /**
     * Returns the {@link Array<Player>} instance used by {@link GameScreen}
     * @returns the player array
     */
    public static Array<Player> getPlayers() {
        check();
        return players;
    }


    /**
     * Sets the {@link Array<Player>} instance used by {@link GameScreen}
     * @param players the player array
     */
    public static void setPlayers(Array<Player> players) {
        check();
        //// TODO: 22-04-2017 Add check for data 
        GameScreenManager.players = players;
    }

    public static Player getLocalPlayer() {
        return getPlayers().first();
    }

    public static PlayerEntity getLocalPlayerEntity() {
        return getPlayers().first().getEntity();
    }
    
    /**
     * Removes {@link Entity} objects that have been marked for removal
     */
    public static void flushEntities() {
        check();
        entities.removeAll(entitiesToRemove,false);
        enemies.removeAll(enemiesToRemove, false);
    }

    /**
     * Returns a {@link List} of entities that are present on the entity stack
     * @return the entities present on the entity stack
     */
    public static Array<Entity> getEntities() {
        check();
        return entities;
    }

    /**
     * Returns a {@link List} of enemies that are present on the enemy stack
     * @return the entities present on the entity stack
     */
    public static Array<EnemyEntity> getEnemies() {
        check();
        return enemies;
    }

    /**
     * Adds an {@link Entity} to the entity stack and calls {@link Entity#init()} on said entity
     * @param e the entity o be added
     */
    public static void addEntity(Entity e) {
        check();
        if (e instanceof EnemyEntity) {
            putEnemy((EnemyEntity) e);
        }
        putEntity(e);
        e.init();
    }

    private static void putEntity(Entity e) {
        check();
        if (! entities.contains(e, false)){
            entities.add(e);
        }
    }

    private static void putEnemy(EnemyEntity e) {
        check();
        if (! entities.contains(e, true)){
            enemies.add(e);
        }
    }


    /**
     * Adds a given {@link Entity} to the list of entities that need to be removed from the entity stack
     * @param e the entity to be removed
     */
    public static void removeEntity(Entity e) {
        check();
        if (e instanceof EnemyEntity) // Remove enemy
            enemiesToRemove.add((EnemyEntity) e);
        entitiesToRemove.add(e);
    }

    /**
     * @return an {@link Array} of {@link StaticDrawable} objects.
     */
    public static Array<StaticDrawable> getStaticObjects() {
        return staticObjects;
    }

    public static void generateRandomEnemyWave(int min, int max, int xmin, int xmax, int ymin, int ymax) {
        check();
        Random random = new Random();
        int count = random.nextInt(max - min + 1) + min;
        for (int i = 0; i < count; i++) {
            int posx = random.nextInt(xmax - xmin + 1) + xmin;
            int posy = random.nextInt(ymax - ymin + 1) + ymin;
            addEntity(new EnemyEntity(posx, posy));
        }
        log.print("Generated %s enemies in range [x: %s, y: %s] and [x: %s, y: %s]", count, xmin, ymin, xmax, ymax);
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
        if (! exists() && checking) throw new IllegalStateException("current screen must be GameScreen");
    }

    /**
     * Call this method when a {@link LivingEntity} dies
     * @param livingEntity the entity that died
     */
    public static void die(LivingEntity livingEntity) {
        if (livingEntity instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) livingEntity;
            if (playerEntity.getPlayer().getLives() > 0){
                playerEntity.getPlayer().removeLife();
                PlayerEntity newPlayerEntity = new PlayerEntity(playerEntity.getX(), playerEntity.getY());
                newPlayerEntity.setMaxSpeed(600f);
                Player newPlayer = new Player(playerEntity.getPlayer().getLives(), newPlayerEntity);
                newPlayer.setScore(playerEntity.getPlayer().getScore());
                players.set(0, newPlayer);

                playerEntity.dispose();

                // Clear entities

                for (Array.ArrayIterator<Entity> iter = new Array.ArrayIterator<Entity>(entities); iter.hasNext();) {
                    Entity entity = iter.next();
                    iter.remove();
                    entity.dispose();
                }

                 addEntity(newPlayerEntity);

                 generateRandomEnemyWave(1, 3, 0, Gdx.graphics.getWidth(), 500, Gdx.graphics.getHeight());

            }
            else {
                playerEntity.dispose();
                setIsDisposing(true);
            }
        } else if (livingEntity instanceof EnemyEntity) {
            livingEntity.dispose();
            removeEntity(livingEntity);
        }
    }

    public static boolean isDisposing() {
        return !checking;
    }

    public static void setIsDisposing(boolean v) {
        checking = !v ;
    }

    /**
     * Flushes the object cache when {@link GameScreen} is disposed of
     */
    public static void dispose() {
        players = new Array<Player>();

        for (Entity e : GameScreenManager.getEntities()) {
            e.dispose();
        }

        entities = new Array<Entity>();
        entitiesToRemove = new Array<Entity>();

        staticObjects = new Array<StaticDrawable>();

        setIsDisposing(false);
        ScreenManager.setCurrentScreen(new GameOverScreen());
    }

}
