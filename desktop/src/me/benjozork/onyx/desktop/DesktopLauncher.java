package me.benjozork.onyx.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.awt.*;

import me.benjozork.onyx.OnyxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Onyx 0.0.1";
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		config.width = (int) dimension.getWidth();
		config.height = (int) dimension.getHeight();
		config.fullscreen = false;
		config.vSyncEnabled = false;
		new LwjglApplication(new OnyxGame(), config);
	}
}
