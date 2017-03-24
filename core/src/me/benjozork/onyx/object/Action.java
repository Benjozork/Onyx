package me.benjozork.onyx.object;

/**
 * Created by Benjozork on 2017-03-20.
 */
public class Action {

     private Runnable action;
     private String identifier;

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
}
