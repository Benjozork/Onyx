package me.benjozork.onyx.game;

import com.badlogic.gdx.utils.Array;

import me.benjozork.onyx.game.entity.Entity;
import me.benjozork.onyx.game.entity.PlayerEntity;

/**
 * Contains all of the entities of the game world.
 *
 * @author Benjozork
 */
public class GameWorld {

    private PlayerEntity player;

    private Array<Entity> entities;

    private GameScreen gameScreen;

    public GameWorld(GameScreen gameScreen) {
        this.entities = new Array<Entity>();
        this.gameScreen = gameScreen;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayerEntity player) {
        this.player = player;
    }

    public Array<Entity> getEntities() {
        return entities;
    }

    public void addEntity(Entity e) {
        this.entities.add(e);
    }

    public void removeEntity(Entity e) {
        this.entities.removeValue(e, true);
    }

    public void dispose() {
        for (Entity entity : getEntities()) {
            entity.dispose();
        }
    }

}