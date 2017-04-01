package me.benjozork.onyx.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import me.benjozork.onyx.OnyxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		config.title = "Onyx v0.0.1 ";
		try {
			JsonReader reader = new JsonReader();
			JsonValue configs = reader.parse(new FileInputStream("config.json"));
			config.title += configs.getString("title","");
			config.width = configs.getInt("width",(int)dimension.getWidth());
			config.height = configs.getInt("height",(int)dimension.getHeight());
			config.fullscreen = configs.getBoolean("fullscreen",false);
			config.vSyncEnabled = configs.getBoolean("vsync",false);
			config.backgroundFPS = configs.getInt("bg_fps",60);
			config.foregroundFPS = configs.getInt("fg_fps",60);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		new LwjglApplication(new OnyxGame(), config);
	}
}
