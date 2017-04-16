package me.benjozork.onyx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;

import me.benjozork.onyx.config.Configs;
import me.benjozork.onyx.config.ProjectConfig;

/**
 * Caches {@link FreeTypeFontGenerator} objects to avoid unnecessary recreations of generators that<br/>
 * share the same font.
 *
 * @author Benjozork
 */
public class FTFGeneratorCache {

    private static HashMap<String, FreeTypeFontGenerator> cache = new HashMap<String, FreeTypeFontGenerator>();

    private static FreeTypeFontGenerator defaultGenerator;

    static {
        ProjectConfig projectConfig = Configs.loadRequire("config/project.json", ProjectConfig.class);
        defaultGenerator = new FreeTypeFontGenerator(Gdx.files.internal(projectConfig.default_font));
        cache.put("default", defaultGenerator);
    }

    /**
     * Returns a {@link FreeTypeFontGenerator} that generates a {@link BitmapFont} with a specified path
     *
     * @param fontPath the path of the desired font, relative to {@link Gdx#files#internal()}
     *
     * @return the requested {@link FreeTypeFontGenerator}
     */
    public static FreeTypeFontGenerator getFTFGenerator(String fontPath) {
        if (fontPath.equals("default")) throw new IllegalArgumentException("fontPath must not be default, use getDefaultFTFGenerator() instead");
        if (cache.containsKey(fontPath)) return cache.get(fontPath);
        else {
            cache.put(fontPath, new FreeTypeFontGenerator(Gdx.files.internal(fontPath)));
            return cache.get(fontPath);
        }
    }

    /**
     * Returns a default {@link FreeTypeFontGenerator} that generates a {@link BitmapFont}
     *
     * @return the default {@link FreeTypeFontGenerator}
     */
    public static FreeTypeFontGenerator getDefaultFTFGenerator() {
        return cache.get("default");
    }


    /**
     * Disposes of all {@link FreeTypeFontGenerator} objects present in the cache
     */
    public static void dispose() {
        for (FreeTypeFontGenerator generator : cache.values()) {
            generator.dispose();
        }
    }

}