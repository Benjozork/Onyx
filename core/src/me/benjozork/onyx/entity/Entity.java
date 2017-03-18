package me.benjozork.onyx.entity;

import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.GameManager;
import me.benjozork.onyx.object.Drawable;

/**
 * Created by Benjozork on 2017-03-03.
 */
public abstract class Entity extends Drawable {

     public Entity(Vector2 position) {
          super(position);
     }

     public abstract void init();

     public abstract void update();

     public abstract void draw();

     public void dispose() {
          GameManager.getRegisteredEntities().remove(this);
     }

}