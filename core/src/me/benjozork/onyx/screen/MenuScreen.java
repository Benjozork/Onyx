package me.benjozork.onyx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.internal.Utils;
import me.benjozork.onyx.ui.UIButton;
import me.benjozork.onyx.ui.UICheckbox;
import me.benjozork.onyx.ui.UIDropdown;
import me.benjozork.onyx.ui.UIScreen;

/**
 * Created by Benjozork on 2017-03-19.
 */
public class MenuScreen implements Screen {

     private UIScreen uiScreen;
     private UIButton button;
     private UICheckbox checkbox;
     private UIDropdown dropdown;

     private Sprite background;

     @Override
     public void show() {

          background = new Sprite(new Texture("core/assets/hud/background_base.png"));
          background.setPosition(0, 0);
          background.setColor(Color.GRAY);
          background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

          uiScreen = new UIScreen(new Vector2(0, 0));

          Vector2 center = Utils.center(95, 55, 1920, 1080);

          button = new UIButton(960 - (95 / 2), 600 - 55 / 2, 95, 55, new BitmapFont(), "Play", new Runnable() {
               @Override
               public void run() {
                    System.out.println("click");
               }
          });

          checkbox = new UICheckbox(960 - (95 / 2), 600 - (55 / 2) - 40, 32, 32, new BitmapFont(), "Check!");

          dropdown = new UIDropdown(960 - (95 / 2), 600 - (55 / 2) - 100, 305, 55, new BitmapFont(), "Dropdown!");
          dropdown.add("Apple", "Banana", "Orange", "Watermelon");

          uiScreen.init();
          uiScreen.add(button);
          uiScreen.add(checkbox);
          uiScreen.add(dropdown);

     }

     @Override
     public void render(float delta) {

          // Draw background

          background.setColor(Color.GRAY);
          background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
          GameManager.getBatch().disableBlending();
          GameManager.getBatch().begin();
          background.draw(GameManager.getBatch());
          GameManager.getBatch().end();
          GameManager.getBatch().enableBlending();

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
