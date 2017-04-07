package me.benjozork.onyx.object;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.internal.FTFGeneratorCache;
import me.benjozork.onyx.utils.CenteredDrawer;

/**
 * Defines a text that is drawn on the screen. Includes font and style methods to ease implementation of text.<br/>
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
     * @param text      the text to be displayed
     * @param fontPath  the font path to be used
     */
    public TextComponent(String text, String fontPath) {
        this.text = text;
        this.fontPath = fontPath;
        this.generatedFont = new BitmapFont();
        this.layout = new GlyphLayout(new BitmapFont(), text);
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
        updateLayout();
        return generatedFont;
    }

    /**
     * Returns a {@link BitmapFont} without regenerating it.
     */
    public BitmapFont getFont() {
        return generatedFont;
    }

    /**
     * Draws the TextComponent's text
     * @param b the {@link SpriteBatch to use}
     * @param x the x position of the desired point
     * @param y the y position of the desired point
     */
    public void draw(SpriteBatch b, float x, float y) {
        generatedFont.draw(b, text, x, y);
    }

    /**
     * Draws the TextComponent's text
     * @param b the {@link SpriteBatch to use}
     * @param p the position of the desired point
     */
    public void draw(SpriteBatch b, Vector2 p) {
        generatedFont.draw(b, text, p.x, p.y);
    }


    /**
     * Draws the TextComponent's text, centered at the specified point
     * @param b the {@link SpriteBatch to use}
     * @param x the x position of the desired point
     * @param y the y position of the desired point
     */
    public void drawCenteredAt(SpriteBatch b, float x, float y) {
        Vector2 pos = CenteredDrawer.get(CenteredDrawer.CenteredDrawingType.CENTERED_AT_POINT, x, y, layout.width, layout.height);
        generatedFont.draw(b, text, pos.x, pos.y);
    }

    /**
     * Draws the TextComponent's text, centered at the specified point
     * @param b the {@link SpriteBatch to use}
     * @param x the x position of the desired point
     * @param y the y position of the desired point
     * @param xalign whether x-axis alignment is enabled
     * @param yalign whether y-axis alignment is enabled
     */
    public void drawCenteredAt(SpriteBatch b, float x, float y, boolean xalign, boolean yalign) {
        Vector2 pos = CenteredDrawer.get(CenteredDrawer.CenteredDrawingType.CENTERED_AT_POINT, x, y, layout.width, layout.height);
        if (xalign && yalign) {
            generatedFont.draw(b, text, pos.x, pos.y);
        } else if (xalign) {
            generatedFont.draw(b, text, pos.x, y);
        } else if (yalign) {
            generatedFont.draw(b, text, x, pos.y);
        } else throw new IllegalArgumentException("both xalign and yalign cannot be false");
    }

    /**
     * Draws the TextComponent's text, centered in a container of specified position, width and height
     * @param b the {@link SpriteBatch to use}
     * @param x the x position of the container
     * @param y the y position of the container
     * @param w the width of the container
     * @param h the height of the container
     */
    public void drawCenteredInContainer(SpriteBatch b, float x, float y, float w, float h) {
        Vector2 pos = CenteredDrawer.getContained(CenteredDrawer.CenteredDrawingType.CENTERED_IN_CONTAINER, x, y, layout.width, layout.height, w, h);
        generatedFont.draw(b, text, pos.x, pos.y);
    }

    /**
     * Draws the TextComponent's text, centered in a container of specified position, width and height
     * @param b the {@link SpriteBatch to use}
     * @param x the x position of the container
     * @param y the y position of the container
     * @param w the width of the container
     * @param h the height of the container
     * @param xalign whether x-axis alignment is enabled
     * @param yalign whether y-axis alignment is enabled
     */
    public void drawCenteredInContainer(SpriteBatch b, float x, float y, float w, float h, boolean xalign, boolean yalign) {
        Vector2 pos = CenteredDrawer.getContained(CenteredDrawer.CenteredDrawingType.CENTERED_IN_CONTAINER, x, y, layout.width, layout.height, w, h);
        if (xalign && yalign) {
            generatedFont.draw(b, text, pos.x, pos.y);
        } else if (xalign) {
            generatedFont.draw(b, text, pos.x, y);
        } else if (yalign) {
            generatedFont.draw(b, text, x, pos.y);
        } else throw new IllegalArgumentException("both xalign and yalign cannot be false");
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
        generateFont();
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

    private void updateLayout() {
        this.getLayout().setText(this.getFont(), this.getText());
    }

    public void dispose() {
        if (generatedFont != null) generatedFont.dispose();
    }

}
