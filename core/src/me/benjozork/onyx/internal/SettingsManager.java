package me.benjozork.onyx.internal;


import java.io.File;

/**
 * Created by Benjozork on 2017-03-04.
 */
public class SettingsManager {

    private static final String SETTINGS_FILE_PATH = "config/settings.json";
    private static File settingsFile;

    public void init() {
        settingsFile = new File(SETTINGS_FILE_PATH);
    }

}
