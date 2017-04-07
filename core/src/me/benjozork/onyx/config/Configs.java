package me.benjozork.onyx.config;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;

import me.benjozork.onyx.config.serializer.BoundsSerializer;
import me.benjozork.onyx.logger.Log;
import me.benjozork.onyx.object.Bounds;
import me.benjozork.onyx.object.Maybe;

/**
 * Manages the game's various configuration files
 * @author angelickite
 */
public class Configs {

    private static final Log log = Log.create("Configs");
    private static final Json json = new Json();
    private static Files files;

    private static ObjectMap<String, Object> cache = new ObjectMap<String, Object>();

    static {
        json.setSerializer(Bounds.class, new BoundsSerializer());
    }

    /**
     * Sets the {@link Files} object
     * @param files the {@link Files} object to be used
     */
    public static void setFiles(Files files) {
        Configs.files = files;
    }

    /**
     * Reloads a certain config file
     *
     * @param internalPath the path for the file to be reloaded from
     * @param clazz the class of the config file that has to be reloaded
     * @return the loaded file
     */
    public static <T> Maybe<T> reload(String internalPath, Class<T> clazz) {
        cache.remove(internalPath);
        return load(internalPath, clazz);
    }

    /**
     * Loads a certain config file
     *
     * @param internalPath the path for the file to be loaded from
     * @param clazz the class of the config file that has to be loaded
     * @return the loaded file
     */
    public static <T> Maybe<T> load(String internalPath, Class<T> clazz) {
        if (cache.containsKey(internalPath)) {
            return Maybe.of((T) cache.get(internalPath));
        }

        FileHandle handle = files.internal(internalPath);

        if (! handle.exists())
            return Maybe.empty();

        T config = json.fromJson(clazz, handle);

        if (config == null) {
            log.print("Could not read config file '%s'", internalPath);
            return Maybe.empty();
        }

        cache.put(internalPath, config);
        log.print("config '%s' loaded and cached.", internalPath);
        return Maybe.of(config);
    }

    /**
     * Reloads a certain config file, and throws an {@link IllegalStateException} if said file does not exist
     *
     * @param internalPath the path for the file to be reloaded from
     * @param clazz the config file class that has to be reloaded
     * @return the reloaded file
     *
     * @throws IllegalStateException if the file does not exist
     */
    public static <T> T reloadRequire(String internalPath, Class<T> clazz) {
        cache.remove(internalPath);
        return loadRequire(internalPath, clazz);
    }

    /**
     * Loads a certain config file, and throws an {@link IllegalStateException} if said file does not exist
     *
     * @param internalPath the path for the file to be loaded from
     * @param clazz the config file class that has to be loaded
     * @return the loaded file
     *
     * @throws IllegalStateException if the file does not exist
     */
    public static <T> T loadRequire(String internalPath, Class<T> clazz) {
        Maybe<T> configMaybe = load(internalPath, clazz);

        if (! configMaybe.exists()) {
            String format = "config '%s' was required, but not available!";
            throw new IllegalStateException(String.format(format, internalPath));
        }

        return configMaybe.get();
    }

    /**
     * Reloads a certain config file, and tries to reload a fallback file if the former does not exist.
     * Throws an {@link IllegalStateException} if the fallback file also does not exist.
     *
     * @param internalPath the path for the file to be reloaded from
     * @param fallbackInternalPath the path for the fallback file to be reloaded from, if the default file fails
     * @param clazz the config file class that has to be reloaded
     * @return the reloaded file
     *
     * @throws IllegalStateException if the fallback file does not exist
     */
    public static <T> T reloadRequireWithFallback(String internalPath, String fallbackInternalPath, Class<T> clazz) {
        cache.remove(internalPath);
        return loadRequireWithFallback(internalPath, fallbackInternalPath, clazz);
    }

    /**
     * Loads a certain config file, and tries to load a fallback file if the former does not exist.
     * Throws an {@link IllegalStateException} if the fallback file also does not exist.
     *
     * @param internalPath the path for the file to be loaded from
     * @param fallbackInternalPath the path for the fallback file to be loaded from, if the default file fails
     * @param clazz the config file class that has to be loaded
     * @return the loaded file
     *
     * @throws IllegalStateException if the fallback file does not exist
     */
    public static <T> T loadRequireWithFallback(String internalPath, String fallbackInternalPath, Class<T> clazz) {
        Maybe<T> configMaybe = load(internalPath, clazz);

        if (configMaybe.exists()) {
            return configMaybe.get();
        } else {
            return loadRequire(fallbackInternalPath, clazz);
        }
    }

    /**
     * Reloads a certain config file, and tries to reload a fallback file if the former does not exist.
     *
     * @param internalPath the path for the file to be reloaded from
     * @param fallbackInternalPath the path for the fallback file to be reloaded from, if the default file fails
     * @param clazz the config file class that has to be reloaded
     * @return the reloaded file
     */
    public static <T> Maybe<T> reloadWithFallback(String internalPath, String fallbackInternalPath, Class<T> clazz) {
        cache.remove(internalPath);
        return loadWithFallback(internalPath, fallbackInternalPath, clazz);
    }

    /**
     * Loads a certain config file, and tries to load a fallback file if the former does not exist.
     *
     * @param internalPath the path for the file to be loaded from
     * @param fallbackInternalPath the path for the fallback file to be loaded from, if the default file fails
     * @param clazz the class of the config file that has to be loaded
     * @return the loaded file
     */
    public static <T> Maybe<T> loadWithFallback(String internalPath, String fallbackInternalPath, Class<T> clazz) {
        Maybe<T> configMaybe = load(internalPath, clazz);

        if (configMaybe.exists()) {
            return configMaybe;
        } else {
            return load(fallbackInternalPath, clazz);
        }
    }

    /**
     * Clears the file cache
     */
    public static void clearCache() {
        cache.clear();
        int cacheSize = cache.size;
        log.print("cache cleared, removed %d items", cacheSize);
    }

}
