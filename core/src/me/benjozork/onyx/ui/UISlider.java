package me.benjozork.onyx.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.internal.Utils;
import me.benjozork.onyx.ui.object.TextComponent;

/**
 * Created by Benjozork on 2017-03-25.
 */
public class UISlider extends UIElement {

     private FreeTypeFontGenerator generator;
     private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
     private BitmapFont font;

     private GlyphLayout layout = new GlyphLayout();

     private String text = new String();

     private final Texture SLIDER_TEXTURE = new Texture("android/assets/ui/slider/slider_bar_0.png");
     private final Texture HOVERED_SLIDER_TEXTURE = new Texture("android/assets/ui/slider/slider_bar_1.png");
     private final Texture SLIDER_CONTROL_TEXTURE  = new Texture("android/assets/ui/slider/slider_control_0.png");
     private final Texture HOVERED_SLIDER_CONTROL_TEXTURE  = new Texture("android/assets/ui/slider/slider_control_1.png");

     private Texture currentSliderTexture = SLIDER_TEXTURE;
     private Texture currentSliderControlTexture = SLIDER_CONTROL_TEXTURE;

     public UISlider(float x, float y, float width, float height, TextComponent component) {
          super(x, y);
          setWidth(width);
          setHeight(height);
          this.text = component.getText();
          this.generator = new FreeTypeFontGenerator(Gdx.files.internal(component.getFontPath()));
          this.parameter = component.getParameter();
     }

     @Override
     public void init() {
          bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
          font = generator.generateFont(parameter);
     }

     @Override
     public void update() {

     }

     @Override
     public void draw() {

     }

     @Override
     public void dispose() {

     }

     @Override
     public boolean click(Vector2 localPosition) {
          return false;
     }
}
