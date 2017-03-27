package me.benjozork.onyx.ui.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by Benjozork on 2017-03-25.
 */
public class TextComponent {

    private String text;
    private String fontPath;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private GlyphLayout layout;

    private BitmapFont generatedFont;

    /**
     * A TextComponent defines a text that is drawn on the screen. A certain font path is specified along with a string and a FreeTypeFontParameter.
     * @param text the text to be displayed
     * @param fontPath the font path to be used
     * @param parameter the FreeTypeFontParameter to be used
     */
    public TextComponent(String text, String fontPath, FreeTypeFontGenerator.FreeTypeFontParameter parameter) {
        this.text = text;
        this.fontPath = fontPath;
        this.generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        this.parameter = parameter;
        this.layout = new GlyphLayout(new FreeTypeFontGenerator(Gdx.files.internal(fontPath)).generateFont(parameter), text);
    }

    public BitmapFont regenerateFont() {
        generatedFont = generator.generateFont(parameter);
        return generatedFont;
    }

    public BitmapFont getFont() {
        return generatedFont;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public String getFontPath() {
        return fontPath;
    }

    public void setFontPath(String fontPath) {
        this.fontPath = fontPath;
    }

    /**
     * The FreeTypeFontParameter of the font used to render the text
     */
    public FreeTypeFontGenerator.FreeTypeFontParameter getParameter() {
        return parameter;
    }

    /**
     * Sets the FreeTypeFontParameter of the font used to render the text
     */
    public void setParameter(FreeTypeFontGenerator.FreeTypeFontParameter parameter) {
        this.parameter = parameter;
    }

    /**
     * The GlyphLayout used
     * @return the layout
     */
    public GlyphLayout getLayout() {
        return layout;
    }

}
