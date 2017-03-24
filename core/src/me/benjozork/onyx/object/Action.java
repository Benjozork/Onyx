package me.benjozork.onyx.object;


import me.benjozork.onyx.ui.UIElement;

/**
 * Created by Benjozork on 2017-03-20.
 */
public class Action {

     public enum ActionEvent {
          CLICKED,
          HOVERED,
          VALUE_CHANGED,
          DISABLED,
          ENABLED
     }

     private ActionEvent event;
     private Runnable action;
     private String identifier;

     private UIElement parent;

     public Action(UIElement parent, String identifier, Runnable action, ActionEvent event) {
          this.parent = parent;
          this.identifier = identifier;
          this.action = action;
          this.event = event;
     }

     public void run() {
          action.run();
     }

     public Runnable getAction() {
          return action;
     }

     public void setAction(Runnable action) {
          this.action = action;
     }

     public String getIdentifier() {
          return identifier;
     }

     public void setIdentifier(String identifier) {
          this.identifier = identifier;
     }

     public ActionEvent getEvent() {
          return event;
     }

     public void setEvent(ActionEvent event) {
          this.event = event;
     }

     public UIElement getParent() {
          return parent;
     }

     public void setParent(UIElement parent) {
          this.parent = parent;
     }

}
