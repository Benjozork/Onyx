package me.benjozork.onyx.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Benjozork on 2017-03-19.
 */
public class GameManager {

     private static OrthographicCamera camera;

     private static Screen currentScreen;

     private static ShapeRenderer renderer;

     private static SpriteBatch batch;

     public static OrthographicCamera getCamera() {
          return camera;
     }

     public static void setCamera(OrthographicCamera camera) {
          GameManager.camera = camera;
     }

     public static Screen getCurrentScreen() {
          return currentScreen;
     }

     public static void setCurrentScreen(Screen currentScreen) {
          Gdx.app.log("[onyx/gm] ", "Changed screen to " + currentScreen.getClass().getName().replace("me.benjozork.onyx.screen.", ""));
          GameManager.currentScreen = currentScreen;
     }

     public static ShapeRenderer getShapeRenderer() {
          return renderer;
     }

     public static void setRenderer(ShapeRenderer renderer) {
          GameManager.renderer = renderer;
     }

     public static SpriteBatch getBatch() {
          return batch;
     }

     public static void setBatch(SpriteBatch batch) {
          GameManager.batch = batch;
     }
}
