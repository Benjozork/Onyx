package me.benjozork.onyx.ui.object;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by Benjozork on 2017-03-25.
 */
public class TextComponent {

    private String text;
    private String fontPath;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    public TextComponent(String text, String fontPath, FreeTypeFontGenerator.FreeTypeFontParameter parameter) {
        this.text = text;
        this.fontPath = fontPath;
        this.parameter = parameter;
    }


    /**
     * The text to be displayed
     */
    public String getText() {
        return text;
    }

    /**
     * Set the text to be displayed
    */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * The path of the font used to render the text
     */
    public String getFontPath() {
        return fontPath;
    }

    /**
     * Set the path of the font used to render the text
     */
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
     * Set the FreeTypeFontParameter of the font used to render the text
     */
    public void setParameter(FreeTypeFontGenerator.FreeTypeFontParameter parameter) {
        this.parameter = parameter;
    }

}
