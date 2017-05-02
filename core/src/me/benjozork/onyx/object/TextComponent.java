package me.benjozork.onyx.object;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.FTFGeneratorCache;
import me.benjozork.onyx.GameManager;
import me.benjozork.onyx.utils.CenteredDrawer;

/**
 * Defines a text that is drawn on the screen. Includes font and style methods to ease implementation of text.<br/>
 * A certain font path can be specified along with a text string and a {@link FreeTypeFontGenerator.FreeTypeFontParameter}.<br/>
 * Drawing methods to render the text are provided, which can also center the text if necessary.
 *
 * @author Benjozork
 */
public class TextComponent {

    private String text;
    private String fontPath;

    private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

    private GlyphLayout layout;

    private BitmapFont generatedFont;

    /**
     * @param text      the text to be displayed
     * @param fontPath  the font path to be used
     * @param parameter the {@link FreeTypeFontGenerator.FreeTypeFontParameter} to be used
     */
    public TextComponent(String text, String fontPath, FreeTypeFontGenerator.FreeTypeFontParameter parameter) {
        this.text = text.trim();
        this.fontPath = fontPath;
        this.parameter = parameter;
        this.generatedFont = FTFGeneratorCache.getFTFGenerator(fontPath).generateFont(parameter);
        this.layout = new GlyphLayout(generatedFont, text);
    }

    /**
     * @param text      the text to be displayed
     * @param fontPath  the font path to be used
     * @param size      the size of the font to be used
     */
    public TextComponent(String text, String fontPath, int size) {
        this.text = text.trim();
        this.fontPath = fontPath;
        this.generatedFont = FTFGeneratorCache.getFTFGenerator(fontPath).generateFont(parameter);
        this.getParameter().size = size;
        this.layout = new GlyphLayout(generatedFont, text);
        this.update();
    }

    /**
     * @param text      the text to be displayed
     * @param fontPath  the font path to be used
     */
    public TextComponent(String text, String fontPath) {
        this.text = text.trim();
        this.fontPath = fontPath;
        this.generatedFont = FTFGeneratorCache.getFTFGenerator(fontPath).generateFont(parameter);
        this.layout = new GlyphLayout(generatedFont, text);
    }

    /**
     * @param text the text to be displayed
     */
    public TextComponent(String text) {
        this.text = text.trim();
        this.generatedFont = GameManager.getFont();
        this.layout = new GlyphLayout(generatedFont, text);
    }


    /**
     * Returns and generates a new {@link BitmapFont}
     */
    public BitmapFont update() {
        if (fontPath != null) generatedFont = FTFGeneratorCache.getFTFGenerator(fontPath).generateFont(parameter);
        updateLayout();
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
     *
     * @param b the {@link SpriteBatch to use}
     * @param x the x position of the desired point
     * @param y the y position of the desired point
     */
    public void draw(SpriteBatch b, float x, float y) {
        generatedFont.draw(b, text, x, y);
    }

    /**
     * Draws the TextComponent's text
     *
     * @param b the {@link SpriteBatch to use}
     * @param p the position of the desired point
     */
    public void draw(SpriteBatch b, Vector2 p) {
        generatedFont.draw(b, text, p.x, p.y);
    }


    /**
     * Draws the TextComponent's text, centered at the specified point
     *
     * @param b the {@link SpriteBatch to use}
     * @param x the x position of the desired point
     * @param y the y position of the desired point
     */
    public void drawCenteredAt(SpriteBatch b, float x, float y) {
        CenteredDrawer.switchToBitmap();
        Vector2 pos = CenteredDrawer.get(CenteredDrawer.CenteredDrawingType.CENTERED_AT_POINT, x, y, layout.width, layout.height);
        CenteredDrawer.switchToPixel();
        generatedFont.draw(b, text, pos.x, pos.y);
    }

    /**
     * Draws the TextComponent's text, centered at the specified point+
     *
     * @param b the {@link SpriteBatch to use}
     * @param x the x position of the desired point
     * @param y the y position of the desired point
     * @param xalign whether x-axis alignment is enabled
     * @param yalign whether y-axis alignment is enabled
     */
    public void drawCenteredAt(SpriteBatch b, float x, float y, boolean xalign, boolean yalign) {
        CenteredDrawer.switchToBitmap();
        Vector2 pos = CenteredDrawer.get(CenteredDrawer.CenteredDrawingType.CENTERED_AT_POINT, x, y, layout.width, layout.height);
        CenteredDrawer.switchToPixel();
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
     *
     * @param b the {@link SpriteBatch to use}
     * @param x the x position of the container
     * @param y the y position of the container
     * @param w the width of the container
     * @param h the height of the container
     */
    public void drawCenteredInContainer(SpriteBatch b, float x, float y, float w, float h) {
        CenteredDrawer.switchToBitmap();
        Vector2 pos = CenteredDrawer.getContained(CenteredDrawer.CenteredDrawingType.CENTERED_IN_CONTAINER, x, y, layout.width, layout.height, w, h);
        CenteredDrawer.switchToPixel();
        generatedFont.draw(b, text, pos.x, pos.y);
    }

    /**
     * Draws the TextComponent's text, centered in a container of specified position, width and height
     *
     * @param b the {@link SpriteBatch to use}
     * @param x the x position of the container
     * @param y the y position of the container
     * @param w the width of the container
     * @param h the height of the container
     * @param xalign whether x-axis alignment is enabled
     * @param yalign whether y-axis alignment is enabled
     */
    public void drawCenteredInContainer(SpriteBatch b, float x, float y, float w, float h, boolean xalign, boolean yalign) {
        CenteredDrawer.switchToBitmap();
        Vector2 pos = CenteredDrawer.getContained(CenteredDrawer.CenteredDrawingType.CENTERED_IN_CONTAINER, x, y, layout.width, layout.height, w, h);
        CenteredDrawer.switchToPixel();
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
        if (! text.equals(this.text)) { // To prevent unnecessary computations
            this.text = text.trim();
            updateLayout();
        }
    }


    public String getFontPath() {
        return fontPath;
    }

    public void setFontPath(String fontPath) {
        this.fontPath = fontPath;
        update();
    }

    /**
     * Returns the FreeTypeFontParameter of the font used to render the text.<br/>
     * WARNING: {@link TextComponent#update()} should ALWAYS be called after editing the parameter.
     */
    public FreeTypeFontGenerator.FreeTypeFontParameter getParameter() {
        return parameter;
    }

    /**
     * Sets the FreeTypeFontParameter of the font used to render the text<br/>
     * WARNING: This method generates a new {@link BitmapFont} using the cached {@link FreeTypeFontGenerator}<br/>
     * instance in {@link FTFGeneratorCache} that matches the font path.
     */
    public void setParameter(FreeTypeFontGenerator.FreeTypeFontParameter parameter) {
        this.parameter = parameter;
        update();
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