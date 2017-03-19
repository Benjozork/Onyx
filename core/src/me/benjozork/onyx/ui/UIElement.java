package me.benjozork.onyx.ui;

import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.object.Drawable;

/**
 * Created by Benjozork on 2017-03-19.
 */
public abstract class UIElement extends Drawable {

     private UIContainer parent;

     private String identifier;

     public UIElement(float x, float y) {
          super(new Vector2(x, y));
     }

     public UIElement(Vector2 position) {
          super (position);
     }

     @Override
     public abstract void init();

     @Override
     public abstract void update();

     @Override
     public abstract void draw();

     public abstract boolean click(Vector2 localPosition);

     public String getIdentifier() {
          return identifier;
     }

     public void setIdentifier(String identifier) {
          this.identifier = identifier;
     }

     public UIContainer getParent() {
          return parent;
     }
}
