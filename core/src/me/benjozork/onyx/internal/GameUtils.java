package me.benjozork.onyx.internal;

import com.badlogic.gdx.Gdx;

/**
 * Created by Benjozork on 2017-03-04.
 */
public class GameUtils {

     public static float getDelta() {
          return Gdx.graphics.getDeltaTime();
     }

     public static float getCenterPos(int w) {
          return (Gdx.graphics.getWidth() / 2) + w / 2;
     }

}
