package me.benjozork.onyx.game;

import com.badlogic.gdx.utils.Array;

import me.benjozork.onyx.game.entity.Entity;
import me.benjozork.onyx.game.entity.PlayerEntity;
import me.benjozork.onyx.utils.Utils;

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

    public void update() {

        for (Entity entity : this.getEntities()) {
            // This first call invokes the update method of the Drawable class.
            // This is necessary in order for the automatic internal movement mechanics to work.
            entity.update(Utils.delta());
            entity.update();
        }

    }

    public void draw() {

        for (Entity entity : this.getEntities()) {
            entity.draw();
        }

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