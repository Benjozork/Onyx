package me.benjozork.onyx.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Version;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

import java.lang.reflect.Field;

import me.benjozork.onyx.config.Configs;
import me.benjozork.onyx.config.KeymapConfig;
import me.benjozork.onyx.config.ProjectConfig;
import me.benjozork.onyx.logger.Log;
import me.benjozork.onyx.object.Maybe;
import me.benjozork.onyx.utils.Utils;

/**
 * Loads keymaps from 'config/keymap.json'.<br/>
 * WARNING: {@link KeymapLoader#init()} should ALWAYS be called before requesting any keycodes!
 *
 * @author Benjozork
 */
public class KeymapLoader {

    private static Log log = Log.create("KeymapLoader");

    private static KeymapConfig keymapConfig;

    private static ArrayMap<String, Integer> keymaps = new ArrayMap<String, Integer>();

    private static Array<String> illegalKeyMapIDs = new Array<String>();

    private static boolean debug = true;

    public static void init() {
         keymapConfig = Configs.loadRequire("config/keymap.json", KeymapConfig.class);
         for (Field field : keymapConfig.getClass().getFields()) {
             try {
                 if (field.get(keymapConfig).equals("SPACE")) { // "Because fuck consistency, right ?" -Libgdx devs
                     keymaps.put(field.getName(), Input.Keys.valueOf("Space"));
                     if (debug) log.print("Loaded keymap '%s' -> '%s'", field.getName(), "SPACE");
                     return;
                 }
                 keymaps.put(field.getName(), Input.Keys.valueOf((String) field.get(keymapConfig)));
                 if (debug) log.print("Loaded keymap '%s' -> '%s'", field.getName(), field.get(keymapConfig));
             } catch (IllegalAccessException e) {
                 Maybe<ProjectConfig> projectConfig = Configs.load("config/project.json", ProjectConfig.class);
                 log.print("FATAL: Failed to access field '%s' in object '%s': Illegal access.", field.getName(), keymapConfig);
                 log.print("Please report this crash to the Onyx devs:");
                 log.print("reflectedClass: %s", keymapConfig);
                 log.print("reflectedFieldName: %s", field.getName());
                 log.print("reflectedFieldType: %s", field.getType());
                 log.print("gameBackend: %s/%s", Gdx.app.getType(), System.getProperty("os.name"));
                 if (! projectConfig.exists()) log.print("FATAL: Additionally, we were unable to access the project config.");
                 else log.print("gameVersion: %s",projectConfig.get().version);
                 log.print("gdxVersion: %s", Version.VERSION);
                 Gdx.app.exit();
             } catch (NullPointerException e) {
                 log.print("ERROR: Field '%s' does exist, but is not defined in configs/keymap.json!", field.getName());
                 illegalKeyMapIDs.add(field.getName());
             }
         }
    }

    /**
     * Returns a keycode, in the form of an int, using a provided action.
     * @param action the action to lookup
     * @return the requested keycode. returns {@code -1} if there was an error in the process.
     */
    public static int getKeyCode(String action) {
        if (keymapConfig == null) {
            log.print("ERROR: keymapConfig not initialized! Please call init() before requesting any keycodes!");
            return -1;
        }
        if (keymaps.get(action) == null) {
            if (illegalKeyMapIDs.contains(action, true)) return -1;

            if (debug) { // Dirty hack to get the method caller class
                StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
                StackTraceElement e = stacktrace[2];
                String className = Utils.sanitizeClassName(e.getClassName());

                log.print("ERROR: Invalid keymap ID '%s' requested by class '%s'!", action, className);
            } else log.print("ERROR: Invalid keymap ID '%s'!", action);
            illegalKeyMapIDs.add(action);
            return -1;
        }
        return keymaps.get(action);
    }

    public static void toggleDebug() {
        debug = ! debug;
    }

}
