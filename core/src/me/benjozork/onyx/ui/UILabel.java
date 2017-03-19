package me.benjozork.onyx.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.internal.GameManager;

/**
 * Created by Benjozork on 2017-03-19.
 */
public class UILabel extends UIElement {

     private BitmapFont font;

     private String text;

     public UILabel(Vector2 position, BitmapFont font,  String text) {
          super(position);
          this.text = text;
          this.font = font;
     }

     @Override
     public void init() {

     }

     @Override
     public void update() {

     }

     @Override
     public void draw() {
          GameManager.getBatch().begin();
          font.draw(GameManager.getBatch(), text, getX(), getY());
          GameManager.getBatch().end();
     }

     @Override
     public void dispose() {

     }

     @Override
     public boolean click(Vector2 localPosition) {
          return false;
     }
}
