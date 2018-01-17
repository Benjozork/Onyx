package me.benjozork.onyx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

import me.benjozork.onyx.backend.handlers.RessourceHandler;
import me.benjozork.onyx.game.entity.Entity;
import me.benjozork.onyx.game.entity.PlayerEntity;
import me.benjozork.onyx.utils.Utils;

/**
 * Contains all of the entities and objects of the game world.
 *
 * @author Benjozork
 */
public class GameWorld {

    public static final float WORLD_SIZE_X = 2000;

    public static final float WORLD_SIZE_Y = 1500;

    private PlayerEntity player;

    private Array<Entity> entities;

    public GameWorld() {
        this.entities = new Array<Entity>();
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

        // First, draw the background and level boundaries

        int leftLineX = (int) -(WORLD_SIZE_X / 2);
        int leftLineY1 = (int) -(WORLD_SIZE_Y / 2);
        int leftLineY2 = (int) WORLD_SIZE_Y / 2;

        int rightLineX = (int) WORLD_SIZE_X / 2;
        int rightLineY1 = leftLineY1;
        int rightLineY2 = leftLineY2;

        int topLineX1 = leftLineX;
        int topLineX2 = rightLineX;
        int topLineY = leftLineY2;

        int bottomLineX1 = topLineX1;
        int bottomLineX2 = topLineX2;
        int bottomLineY = leftLineY1;

        ShapeRenderer shapeRenderer = RessourceHandler.getRenderer();

        shapeRenderer.setProjectionMatrix(RessourceHandler.getWorldCamera().combined);

        RessourceHandler.setIsShapeRendering(true);

        shapeRenderer.setColor(Color.RED);

        shapeRenderer.line(leftLineX, leftLineY1, leftLineX, leftLineY2);
        shapeRenderer.line(rightLineX, rightLineY1, rightLineX, rightLineY2);
        shapeRenderer.line(topLineX1, topLineY, topLineX2, topLineY);
        shapeRenderer.line(bottomLineX1, bottomLineY, bottomLineX2, bottomLineY);

        RessourceHandler.setIsShapeRendering(false);

        // Then, draw the entities

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