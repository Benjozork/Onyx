package me.benjozork.onyx;

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
 * Loads keymaps from {@code config/keymap.json}.<br/>
 * WARNING: {@link KeymapLoader#init()} should ALWAYS be called before requesting any keycodes!
 *
 * @see KeymapConfig
 *
 * @author Benjozork
 */
public class KeymapLoader {

    private final static Log log = Log.create("KeymapLoader");

    private static KeymapConfig keymapConfig;

    private final static ArrayMap<String, Integer> keymaps = new ArrayMap<String, Integer>();

    private final static Array<String> illegalKeyMapIDs = new Array<String>();

    private static boolean debug = true;

    public static void init() {
         keymapConfig = Configs.loadRequire("config/keymap.json", KeymapConfig.class);
         for (Field field : keymapConfig.getClass().getFields()) {
             final String name = field.getName();
             try {
                 final Object value = field.get(keymapConfig);
                 if (value.equals("SPACE")) { // "Because fuck consistency, right ?" -Libgdx devs
                     keymaps.put(name, Input.Keys.valueOf("Space"));
                     if (debug) log.print("Loaded keymap '%s' -> '%s'", name, "SPACE");
                     continue;
                 }
                 keymaps.put(name, Input.Keys.valueOf((String) value));
                 if (keymaps.get(name) == -1) {
                    log.print("ERROR: Keymap '%s' bound to invalid key '%s'!", name, value);
                    illegalKeyMapIDs.add((String) value);
                    keymaps.removeKey(name);
                    continue;
                 }
                 if (debug) log.print("Loaded keymap '%s' -> '%s'", name, value);
             } catch (IllegalAccessException e) {
                 Maybe<ProjectConfig> projectConfig = Configs.load("config/project.json", ProjectConfig.class);
                 log.error("FATAL: Failed to access field '%s' in object '%s': Illegal access.", name, keymapConfig);
                 log.error("Please report this crash to the Onyx devs:");
                 log.error("reflectedClass: %s", keymapConfig);
                 log.error("reflectedFieldName: %s", name);
                 log.error("reflectedFieldType: %s", field.getType());
                 log.error("gameBackend: %s/%s", Gdx.app.getType(), System.getProperty("os.name"));
                 if (! projectConfig.exists()) log.error("FATAL: Additionally, we were unable to access the project config.");
                 else log.print("gameVersion: %s",projectConfig.get().version);
                 log.print("gdxVersion: %s", Version.VERSION);
                 Gdx.app.exit();
             } catch (NullPointerException e) {
                 log.error("ERROR: Keymap '%s' does exist, but is not defined in configs/keymap.json!", name);
                 illegalKeyMapIDs.add(name);
             }
         }
         if (illegalKeyMapIDs.size == 0) log.print("%s keymaps loaded", keymaps.size);
         else log.print("%s keymaps loaded with %s errors", keymaps.size, illegalKeyMapIDs.size);
    }

    /**
     * Returns a keycode, in the form of an {@code int}, using a provided action.
     * @param action the action to lookup
     * @return the requested keycode. returns {@code -1} if there was an error in the process.
     */
    public static int getKeyCode(String action) {
        if (keymapConfig == null) {
            log.error("ERROR: keymapConfig not initialized! Please call init()!");
            return -1;
        }
        if (keymaps.get(action) == null) {
            if (illegalKeyMapIDs.contains(action, true)) return -1;

            if (debug) { // Dirty hack to get the method caller class
                StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
                StackTraceElement e = stacktrace[2];
                String className = Utils.sanitizeClassName(e.getClassName());

                log.error("ERROR: Keymap '%s' does not exist! (requested by class '%s')", action, className);
            } else {
                log.error("ERROR: Keymap '%s' does not exist!", action);
            }
            illegalKeyMapIDs.add(action);
            return -1;
        }
        return keymaps.get(action);
    }

    public static void toggleDebug() {
        debug = ! debug;
    }

}
