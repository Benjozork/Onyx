package me.benjozork.onyx.config;

import me.benjozork.onyx.object.Bounds;

/**
 * @author angelickite
 */
public class DisplayConfig {

    public String title;
    public boolean vsync;
    public boolean fullscreen;
    public int foregroundFps;
    public int backgroundFps;
    public Bounds windowBounds;

    @Override
    public String toString() {
        return "DisplayConfig [title=" + title + ", vsync=" + vsync + ", fullscreen=" + fullscreen + ", foregroundFps="
                + foregroundFps + ", backgroundFps=" + backgroundFps + ", windowBounds=" + windowBounds + "]";
    }

}