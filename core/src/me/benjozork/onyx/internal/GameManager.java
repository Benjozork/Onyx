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

     /**
      * The camera instance that is used when rendering world objects
      */
     private static OrthographicCamera worldCamera;

     /**
      * The camera instance that is used when rendering gui objects
      */
     private static OrthographicCamera guiCamera;

     private static Screen currentScreen;

     private static ShapeRenderer renderer;

     private static SpriteBatch batch;

     /**
      * The camera instance that is used when rendering world objects
      * @return the world camera
      */
     public static OrthographicCamera getWorldCamera() {
          return worldCamera;
     }

     /**
      * The camera instance that is used when rendering gui objects
      * @return the gui camera
      */
     public static OrthographicCamera getGuiCamera() {
          return guiCamera;
     }

     /**
      * Set the camera instance to be used when rendering world objects
      * @param worldCamera
      */
     public static void setWorldCamera(OrthographicCamera worldCamera) {
          GameManager.worldCamera = worldCamera;
     }

     /**
      * Set the camera instance to be used when rendering gui objects
      * @param guiCamera
      */
     public static void setGuiCamera(OrthographicCamera guiCamera) {
          GameManager.guiCamera = guiCamera;
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
