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

/**
 * Created by Benjozork on 2017-03-19.
 */
public class UICheckbox extends UIElement {

     private FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("core/assets/ui/cc_red_alert_inet.ttf"));
     private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
     private BitmapFont font;

     private GlyphLayout layout = new GlyphLayout();

     private Vector2 dimension = new Vector2();

     private String text = new String();

     private final Texture CHECKBOX_TEXTURE = new Texture("core/assets/ui/checkbox/checkbox_0.png");
     private final Texture TICKED_CHECKBOX_TEXTURE = new Texture("core/assets/ui/checkbox/checkbox_2.png");
     private final Texture HOVERED_CHECKBOX_TEXTURE = new Texture("core/assets/ui/checkbox/checkbox_1.png");
     private final Texture HOVERED_TICKED_CHECKBOX_TEXTURE = new Texture("core/assets/ui/checkbox/checkbox_3.png");
     private Texture currentTexture = CHECKBOX_TEXTURE;

     private boolean checked = false;

     private float colorTimer;
     private float maxColorTimer = 0.1f;

     public UICheckbox(float x, float y, float width, float height, BitmapFont font, String text) {
          super(x, y);
          this.dimension.set(width, height);
          this.font = font;
          this.text = text;
     }

     public UICheckbox(Vector2 position, Vector2 dimension, BitmapFont font, String text) {
          super(position);
          this.dimension = dimension;
          this.font = font;
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

          if (hovering()) {
               currentTexture = (checked ? HOVERED_TICKED_CHECKBOX_TEXTURE : HOVERED_CHECKBOX_TEXTURE);
          } else {
               currentTexture = (checked ? TICKED_CHECKBOX_TEXTURE : CHECKBOX_TEXTURE);
          }
     }

     @Override
     public void draw() {
          /*renderer.begin(ShapeRenderer.ShapeType.Filled);
          renderer.setColor(outerColor);
          renderer.rect(getX(), getY(), dimension.x, dimension.y);
          renderer.setColor(innerColor);
          renderer.rect(getX() + 5, getY() + 5, getWidth() - 10, getHeight() - 10);
          renderer.end();*/

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
          toggle();
          return true;
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

     public boolean isChecked() {
          return checked;
     }

     public void setChecked(boolean checked) {
          this.checked = checked;
     }

     public void toggle() {
          currentTexture = !checked ? TICKED_CHECKBOX_TEXTURE : CHECKBOX_TEXTURE;
          checked = !checked;
     }
}
