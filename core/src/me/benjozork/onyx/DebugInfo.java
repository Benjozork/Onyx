package me.benjozork.onyx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import me.benjozork.onyx.config.Configs;
import me.benjozork.onyx.config.ProjectConfig;

/**
 * @author Benjozork
 */
public class DebugInfo {

    public static Array<Float> frameTimes = new Array<Float>();

    public static String get() {
        return
        Gdx.graphics.getFramesPerSecond()
        + "fps\n"
        + ScreenManager.getCurrentScreen().getClass().getSimpleName()
        + "\n"
        + "averageFrameTime: "
        + averageFrameTime()
        + "\n"
        + Configs.loadCached(ProjectConfig.class).version;
    }

    private static float averageFrameTime() { //fixme
        float tmp = 0f;
        int i = frameTimes.size;
        while (i --> 0) {
            tmp += frameTimes.get(i);
        }
        return tmp / frameTimes.size;
    }

}
