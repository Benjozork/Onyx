package me.benjozork.onyx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;

import me.benjozork.onyx.ui.UIButton;
import me.benjozork.onyx.ui.UIScreen;

/**
 * Created by Benjozork on 2017-03-19.
 */
public class MenuScreen implements Screen {

     private UIScreen uiScreen;
     private UIButton button;

     @Override
     public void show() {
          uiScreen = new UIScreen(new Vector2(0, 0));

          button = new UIButton(50, 50, 500, 200, new BitmapFont(), "LIBGDX MASTER RACE", new Runnable() {
               @Override
               public void run() {
                    System.out.println("click");
               }
          });

          uiScreen.init();
          uiScreen.add(button);
     }

     @Override
     public void render(float delta) {
          if (Gdx.input.justTouched()) {
               uiScreen.click(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
          }
          if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
               button.resize(10f, 0f);
          }
          if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
               button.resize(-10f, 0f);
          }
          if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
               button.resize(0f, 10f);
          }
          if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
               button.resize(0f, -10f);
          }

          uiScreen.update();
          uiScreen.draw();
     }

     @Override
     public void resize(int width, int height) {
          uiScreen.resize(width, height);
     }

     @Override
     public void pause() {

     }

     @Override
     public void resume() {

     }

     @Override
     public void hide() {

     }

     @Override
     public void dispose() {
          uiScreen.dispose();
     }

     public UIScreen getUIScreen() {
          return uiScreen;
     }

     public void setUIScreen(UIScreen uiScreen) {
          this.uiScreen = uiScreen;
     }
}
