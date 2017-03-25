package me.benjozork.onyx.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;


/**
 * Created by Benjozork on 2017-03-04.
 */
public class Utils {

     /**
      * Cached instances used for calculations or returning results.
      * Note: This potentially leads to bugs if the same temporary instance is used
      *       multiple times throughout a method without care. If more cached instances
      *       are needed make sure to create a new one here.
      */
     private static final Vector2 v2 = new Vector2();
     private static final Vector3 v3 = new Vector3();

     public static float delta() {
          return Gdx.graphics.getDeltaTime();
     }

     public static float getCenterPos(int w) {
          return (Gdx.graphics.getWidth() / 2) + w / 2;
     }

     public static Vector2 center(float w, float h, float cw, float ch) {
          return new Vector2(cw - w / 2, ch - h / 2);
     }

     /**
      * Translate a point from screen coordinates to world coordinates.<br>
      * This method returns an internally cached vector instance, do not store this instance!
      * @param vec the position of the point
      * @return the point position in world coordinates.
      */
     public static Vector2 unprojectWorld(Vector2 vec) {
          return unprojectWorld(vec.x, vec.y);
     }

     /**
      * Translate a point from screen coordinates to world coordinates.<br>
      * This method returns an internally cached vector instance, do not store this instance!
      * @param x the x position of the point
      * @param y the y position of the point
      * @return the point position in world coordinates.
      */
     public static Vector2 unprojectWorld(float x, float y) {
          v3.set(x, y, 0);
          OrthographicCamera camera = GameManager.getWorldCamera();
          camera.unproject(v3);
          v2.set(v3.x, v3.y);
          return v2;
     }

     /**
      * Translate a point from screen coordinates to world coordinates.<br>
      * This method returns an internally cached vector instance, do not store this instance!
      * @param vec the position of the point
      * @return the point position in world coordinates.
      */
     public static Vector2 unprojectGui(Vector2 vec) {
          return unprojectGui(vec.x, vec.y);
     }

     /**
      * Translate a point from screen coordinates to gui coordinates.<br>
      * This method returns an internally cached vector instance, do not store this instance!
      * @param x the x position of the point
      * @param y the y position of the point
      * @return the point position in world coordinates.
      */
     public static Vector2 unprojectGui(float x, float y) {
          v3.set(x, y, 0);
          OrthographicCamera camera = GameManager.getGuiCamera();
          camera.unproject(v3);
          v2.set(v3.x, v3.y);
          return v2;
     }

     public static Color rgb(int r, int g, int b) {
          float rf = r / 255.0f;
          float gf = g / 255.0f;
          float bf = b / 255.0f;
          return new Color(rf, gf, bf, 1);
     }

     public static Color rgba(int r, int g, int b, int a) {
          float rf = r / 255.0f;
          float gf = g / 255.0f;
          float bf = b / 255.0f;
          float af = a / 255.0f;
          return new Color(rf, gf, bf, af);
     }

     public String table(String[] strings) {
          StringBuilder ret = new StringBuilder(new String());
          for (int i = 0; i < strings.length; i++) {
               if (i < strings.length - 1) ret.append(strings[i] + ", ");
               else ret.append(strings[i] + ".");
          }
          return ret.toString();
     }

     public Array<String> array(String... strings) {
          Array<String> ret = new Array<String>();
          for (String s : strings) {
               ret.add(s);
          }
          return ret;
     }
}