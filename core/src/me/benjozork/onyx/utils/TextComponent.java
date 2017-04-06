package me.benjozork.onyx.utils;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import me.benjozork.onyx.internal.FTFGeneratorCache;

/**
 * Defines a text that is drawn on the screen.<br/>
 * A certain font path is specified along with a string and a {@link FreeTypeFontGenerator.FreeTypeFontParameter}.
 * @author Benjozork
 */
public class TextComponent {

    private String text;
    private String fontPath;

    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    private GlyphLayout layout;

    private BitmapFont generatedFont;

    /**
     * @param text      the text to be displayed
     * @param fontPath  the font path to be used
     * @param parameter the {@link FreeTypeFontGenerator.FreeTypeFontParameter} to be used
     */
    public TextComponent(String text, String fontPath, FreeTypeFontGenerator.FreeTypeFontParameter parameter) {
        this.text = text;
        this.fontPath = fontPath;
        this.parameter = parameter;
        this.generatedFont = FTFGeneratorCache.getFTFGenerator(fontPath).generateFont(parameter);
        this.layout = new GlyphLayout(generatedFont, text);
    }

    /**
     * @param text the text to be displayed
     */
    public TextComponent(String text) {
        this.text = text;
        this.generatedFont = new BitmapFont();
        this.layout = new GlyphLayout(new BitmapFont(), text);
    }


    /**
     * Returns and generates a new {@link BitmapFont}
     */
    public BitmapFont generateFont() {
        generatedFont = FTFGeneratorCache.getFTFGenerator(fontPath).generateFont(parameter);
        return generatedFont;
    }

    /**
     * Returns a {@link BitmapFont} without regenerating it
     */
    public BitmapFont getFont() {
        return generatedFont;
    }

    /**
     * Draws the TextComponent's text
     * @param b the {@link SpriteBatch to use}
     */
    public void draw(SpriteBatch b, float x, float y) {
        generatedFont.draw(b, text, x, y);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        updateLayout();
    }


    public String getFontPath() {
        return fontPath;
    }

    public void setFontPath(String fontPath) {
        this.fontPath = fontPath;
    }

    /**
     * Returns the FreeTypeFontParameter of the font used to render the text
     */
    public FreeTypeFontGenerator.FreeTypeFontParameter getParameter() {
        return parameter;
    }

    /**
     * Sets the FreeTypeFontParameter of the font used to render the text
     * WARNING: This method generates a new {@link BitmapFont} using the cached {@link FreeTypeFontGenerator}<br/>
     * instance in {@link FTFGeneratorCache} that matches the font path.
     */
    public void setParameter(FreeTypeFontGenerator.FreeTypeFontParameter parameter) {
        this.parameter = parameter;
        generateFont();
    }

    /**
     * Returns the {@link GlyphLayout} instance
     */
    public GlyphLayout getLayout() {
        return layout;
    }

    /**
     * Updates the {@link GlyphLayout} instance to match an updated text
     */
    public void updateLayout() {
        this.getLayout().setText(this.getFont(), this.getText());
    }

    public void dispose() {
        if (generatedFont != null) generatedFont.dispose();
    }

}
