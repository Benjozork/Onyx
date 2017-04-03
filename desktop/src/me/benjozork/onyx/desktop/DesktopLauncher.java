package me.benjozork.onyx.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglFiles;

import me.benjozork.onyx.OnyxGame;
import me.benjozork.onyx.config.Configs;
import me.benjozork.onyx.config.DisplayConfig;
import me.benjozork.onyx.config.ProjectConfig;

public class DesktopLauncher {
    public static void main(String[] arg) {
	LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

	// Since Gdx.files is only set up once we launch an application and we
	// therefore cant use that we need to provide Configs with a Files instance for
	// loading our configurations.
	Configs.setFiles(new LwjglFiles());

	setupDisplay(config);

	new LwjglApplication(new OnyxGame(), config);
    }

    private static void setupDisplay(LwjglApplicationConfiguration config) {
	DisplayConfig displayConfig = Configs.loadRequireWithFallback("mod/display.json", "config/display.json",
		DisplayConfig.class);

	config.title = displayConfig.title;
	config.width = displayConfig.windowBounds.width;
	config.height = displayConfig.windowBounds.height;
	config.fullscreen = displayConfig.fullscreen;
	config.vSyncEnabled = displayConfig.vsync;
	config.backgroundFPS = displayConfig.backgroundFps;
	config.foregroundFPS = displayConfig.foregroundFps;

	// prepend current version to title
	ProjectConfig projectConfig = Configs.loadRequire("config/project.json", ProjectConfig.class);
	config.title = "[" + projectConfig.version + "] " + config.title;
    }
}
