package me.benjozork.onyx.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;

/**
 * Caches {@link FreeTypeFontGenerator} objects to avoid unnecessary recreations of generators that<br/>
 * share the same font.
 *
 * @author Benjozork
 */
public class FTFGeneratorCache {

    private static HashMap<String, FreeTypeFontGenerator> cache = new HashMap<String, FreeTypeFontGenerator>();

    /**
     * Returns a {@link FreeTypeFontGenerator} that generates a {@link BitmapFont} with a specified path.
     * @param fontPath the path of the desired font, relative to {@link Gdx#files#internal()}
     * @return the requested {@link FreeTypeFontGenerator}
     */
    public static FreeTypeFontGenerator getFTFGenerator(String fontPath) {
        if (cache.containsKey(fontPath)) return cache.get(fontPath);
        else {
            cache.put(fontPath, new FreeTypeFontGenerator(Gdx.files.internal(fontPath)));
            return cache.get(fontPath);
        }
    }

    /**
     * Disposes of all {@link FreeTypeFontGenerator} objects present in the cache.
     */
    public static void dispose() {
        for (FreeTypeFontGenerator generator : cache.values()) {
            generator.dispose();
        }
    }

}
