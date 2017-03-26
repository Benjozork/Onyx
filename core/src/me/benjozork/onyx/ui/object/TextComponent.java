package me.benjozork.onyx.ui.object;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by Benjozork on 2017-03-25.
 */
public class TextComponent {

    private String text;
    private String fontPath;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    /**
     * A TextComponent defines the text component of an UIElement. A certain font path is specified along with a string and a FreeTypeFontParameter.
     * @param text the text to be displayed
     * @param fontPath the font path to be used
     * @param parameter the FreeTypeFontParameter to be used
     */
    public TextComponent(String text, String fontPath, FreeTypeFontGenerator.FreeTypeFontParameter parameter) {
        this.text = text;
        this.fontPath = fontPath;
        this.parameter = parameter;
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

}
