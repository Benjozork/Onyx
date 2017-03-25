package me.benjozork.onyx.ui;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import me.benjozork.onyx.ui.object.Action;
import me.benjozork.onyx.ui.object.ActionEvent;

/**
 * Created by Benjozork on 2017-03-24.
 */
public class UIRadioButtonGroup extends UIElement {

     private Array<UIRadioButton> buttons = new Array<UIRadioButton>();
     private int selected;

     public UIRadioButtonGroup() {
          super(0, 0);
     }


     public int getSelected() {
          return selected;
     }

     public void setSelected(int selected) {
          this.selected = selected;
     }

     public Array<UIRadioButton> getButtons() {
          return buttons;
     }

     public void addButton(UIRadioButton b) {
          b.setGroup(this);
          buttons.add(b);
     }

     public void select(UIRadioButton b) {
          triggerEvent(ActionEvent.VALUE_CHANGED);
          selected = buttons.indexOf(b, true);
          b.set(true);
          if (selected == -1) {
               selected = 0;
               throw new IllegalArgumentException();
          }
          for (UIRadioButton button : buttons) {
               if (buttons.indexOf(button, true) != selected) {
                    button.set(false);
               }
          }
     }

     @Override
     public void init() {
          setBounds(new Rectangle(0, 0, 0, 0));
          selected = buttons.size - 1;
     }

     @Override
     public void update() {
          for (UIRadioButton button : buttons) {
               if (selected == buttons.indexOf(button, true)) {
                    button.set(true);
               }
          }
     }

     @Override
     public void draw() {

     }

     @Override
     public void dispose() {

     }

     @Override
     public boolean click(Vector2 localPosition) {
          return false;
     }
}