package me.benjozork.onyx.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.internal.Utils;
import me.benjozork.onyx.object.Action;

/**
 * Created by Benjozork on 2017-03-24.
 */
public class UIRadioButton extends UIElement {

     private FreeTypeFontGenerator generator;
     private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
     private BitmapFont font;

     private GlyphLayout layout = new GlyphLayout();

     private Texture RADIOBUTTON_TEXTURE = new Texture("core/assets/ui/radiobutton/radiobutton_0.png");
     private Texture TICKED_RADIOBUTTON_TEXTURE = new Texture("core/assets/ui/radiobutton/radiobutton_2.png");

     private Texture HOVERED_RADIOBUTTON_TEXTURE = new Texture("core/assets/ui/radiobutton/radiobutton_1.png");
     private Texture HOVERED_TICKED_RADIOBUTTON_TEXTURE = new Texture("core/assets/ui/radiobutton/radiobutton_3.png");

     private Texture currentTexture = RADIOBUTTON_TEXTURE;

     private UIRadioButtonGroup group;

     private Vector2 dimension = new Vector2();

     private boolean selected = false;

     private String text;

     public UIRadioButton(float x, float y, float width, float height, String fontPath, String text) {
          super(x, y);
          this.dimension.x = width;
          this.dimension.y = height;
          this.generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
          this.text = text;
     }

     @Override
     public void init() {
          bounds = new Rectangle(getX(), getY(), dimension.x, dimension.y);

          parameter.color = Utils.rgb(255, 255, 255);
          parameter.size = 35;

          font = generator.generateFont(parameter);
     }

     @Override
     public void update() {
          layout.setText(font, text);
          bounds.width = getWidth() + layout.width + 10;
          bounds.height = getHeight();

          currentTexture = selected ? (hovering() ? HOVERED_TICKED_RADIOBUTTON_TEXTURE : TICKED_RADIOBUTTON_TEXTURE) : (hovering() ? HOVERED_RADIOBUTTON_TEXTURE : RADIOBUTTON_TEXTURE);

          /*if (hovering()) {
               currentTexture = (checked ? HOVERED_TICKED_CHECKBOX_TEXTURE : HOVERED_CHECKBOX_TEXTURE);
          } else {
               currentTexture = (checked ? TICKED_CHECKBOX_TEXTURE : CHECKBOX_TEXTURE);
          }*/
     }

     @Override
     public void draw() {
          layout.setText(font, text);

          GameManager.getBatch().begin();
          GameManager.getBatch().draw(currentTexture, getX(), getY(), getWidth(), getHeight());
          font.draw(GameManager.getBatch(), text, (getX() +  getWidth() + 50) - layout.width / 2, (getY() + getHeight() / 2) + layout.height / 2);
          GameManager.getBatch().end();
     }

     @Override
     public void dispose() {

     }

     @Override
     public boolean click(Vector2 localPosition) {
          group.select(this);
          return true;
     }

     public void set(boolean b) {
          triggerEvent(Action.ActionEvent.VALUE_CHANGED);
          this.selected = b;
     }

     public Vector2 getDimension() {
          return dimension;
     }

     public void setDimension(Vector2 dimension) {
          this.dimension = dimension;
     }

     public float getWidth() {
          return dimension.x;
     }

     public void setWidth(float v) {
          dimension.x = v;
          bounds.width = v;
     }

     public float getHeight() {
          return dimension.y;
     }

     public void setHeight(float v) {
          this.dimension.y = v;
          bounds.height = v;
     }

     public void resize(float dx, float dy) {
          this.dimension.x += dx;
          this.bounds.width += dx;
          this.dimension.y += dy;
          this.bounds.height += dy;
     }

     public String getText() {
          return text;
     }

     public void setText(String v) {
          text = v;
     }

     public void setGroup(UIRadioButtonGroup group) {
          this.group = group;
     }
}
