package me.benjozork.onyx.ui;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import me.benjozork.onyx.object.Action;
import me.benjozork.onyx.object.Drawable;

/**
 * Created by Benjozork on 2017-03-19.
 */
public abstract class UIElement extends Drawable {

     private Array<Action> actions = new Array<Action>();

     private UIContainer parent;

     private String identifier;

     private boolean justHovered = false;

     public UIElement(float x, float y) {
          super(new Vector2(x, y));
     }

     public UIElement(Vector2 position) {
          super (position);
     }

     public void update(float dt) {
          if (hovering()) {
               if (justHovered) return;
               triggerEvent(Action.ActionEvent.HOVERED);
               justHovered = true;
          } else {
               justHovered = false;
          }
     }

     @Override
     public abstract void init();

     @Override
     public abstract void update();

     @Override
     public abstract void draw();

     public boolean clickElement(Vector2 localPosition) {
          click(localPosition);
          triggerEvent(Action.ActionEvent.CLICKED);
          return true;
     }

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

     public Array<Action> getActions() {
          return actions;
     }

     public void addAction(String identifier, Runnable action, Action.ActionEvent event) {
          actions.add(new Action(this, identifier, action, event));
     }

     public void triggerEvent(Action.ActionEvent e) {
          for (Action a : actions) {
               if (a.getEvent() == e) {
                    a.run();
               }
          }
     }
}
