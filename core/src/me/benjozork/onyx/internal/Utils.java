package me.benjozork.onyx.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;


/**
 * Created by Benjozork on 2017-03-04.
 */
public class Utils {

     public static float delta() {
          return Gdx.graphics.getDeltaTime();
     }

     public static float getCenterPos(int w) {
          return (Gdx.graphics.getWidth() / 2) + w / 2;
     }

     public static Vector2 center(float w, float h, float cw, float ch) {
          return new Vector2(cw - w / 2, ch - h / 2);
     }

     public static Vector2 unproject(Vector2 vec) {
          Vector3 vec3;
          vec3 =  GameManager.getCamera().unproject(new Vector3(vec.x, vec.y, 0));
          return new Vector2(vec3.x, vec3.y);
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