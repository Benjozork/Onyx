package me.benjozork.onyx.config;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;

import me.benjozork.onyx.config.serializer.BoundsSerializer;
import me.benjozork.onyx.logger.Log;
import me.benjozork.onyx.utils.Bounds;
import me.benjozork.onyx.utils.Maybe;

public class Configs {

    private static final Log log = Log.create("Configs");
    private static final Json json = new Json();
    private static Files files;

    private static ObjectMap<String, Object> cache = new ObjectMap<String, Object>();

    static {
        json.setSerializer(Bounds.class, new BoundsSerializer());
    }

    public static void setFiles(Files files) {
        Configs.files = files;
    }

    public static <T> Maybe<T> reload(String internalPath, Class<T> clazz) {
        cache.remove(internalPath);
        return load(internalPath, clazz);
    }

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

    public static <T> T reloadRequire(String internalPath, Class<T> clazz) {
        cache.remove(internalPath);
        return loadRequire(internalPath, clazz);
    }

    public static <T> T loadRequire(String internalPath, Class<T> clazz) {
        Maybe<T> configMaybe = load(internalPath, clazz);

        if (! configMaybe.exists()) {
            String format = "config '%s' was required, but not available!";
            throw new IllegalStateException(String.format(format, internalPath));
        }

        return configMaybe.get();
    }

    public static <T> T reloadRequireWithFallback(String internalPath, String fallbackInternalPath, Class<T> clazz) {
        cache.remove(internalPath);
        return loadRequireWithFallback(internalPath, fallbackInternalPath, clazz);
    }

    public static <T> T loadRequireWithFallback(String internalPath, String fallbackInternalPath, Class<T> clazz) {
        Maybe<T> configMaybe = load(internalPath, clazz);

        if (configMaybe.exists()) {
            return configMaybe.get();
        } else {
            return loadRequire(fallbackInternalPath, clazz);
        }
    }

    public static <T> Maybe<T> reloadWithFallback(String internalPath, String fallbackInternalPath, Class<T> clazz) {
        cache.remove(internalPath);
        return loadWithFallback(internalPath, fallbackInternalPath, clazz);
    }

    public static <T> Maybe<T> loadWithFallback(String internalPath, String fallbackInternalPath, Class<T> clazz) {
        Maybe<T> configMaybe = load(internalPath, clazz);

        if (configMaybe.exists()) {
            return configMaybe;
        } else {
            return load(fallbackInternalPath, clazz);
        }
    }

    public static void clearCache() {
        cache.clear();
        int cacheSize = cache.size;
        log.print("cache cleared, removed %d items", cacheSize);
    }

}
