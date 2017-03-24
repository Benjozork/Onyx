package me.benjozork.onyx.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.internal.Utils;

/**
 * Created by Benjozork on 2017-03-19.
 */
public class UIButton extends UIElement {

     private FreeTypeFontGenerator generator;
     private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
     private BitmapFont font;

     private GlyphLayout layout = new GlyphLayout();

     private Vector2 dimension = new Vector2();

     private String text = new String();

     private final Texture BUTTON_TEXTURE = new Texture("core/assets/ui/button/button_0.png");
     private final NinePatch BUTTON = new NinePatch(BUTTON_TEXTURE, 6, 6, 6, 6);
     private final Texture HOVERED_BUTTON_TEXTURE = new Texture("core/assets/ui/button/button_1.png");
     private final NinePatch HOVERED_BUTTON = new NinePatch(HOVERED_BUTTON_TEXTURE, 6, 6, 6, 6);
     private final Texture CLICKED_BUTTON_TEXTURE = new Texture("core/assets/ui/button/button_2.png");
     private final NinePatch CLICKED_BUTTON = new NinePatch(CLICKED_BUTTON_TEXTURE, 6, 6, 6, 6);

     private NinePatch currentPatch = BUTTON;

     private float colorTimer;
     private float maxColorTimer = 0.1f;

     public UIButton(float x, float y, float width, float height, String fontPath, String text) {
          super(x, y);
          this.dimension.set(width, height);
          this.text = text;
          this.generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
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

          if (colorTimer >= 0) {
               if (colorTimer <= maxColorTimer) {
                    colorTimer += Utils.delta();
               } else {
                    currentPatch = BUTTON;
                    colorTimer = -1;
               }
          } else {
               if (hovering()) {
                    currentPatch = HOVERED_BUTTON;
               } else {
                    currentPatch = BUTTON;
               }
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
          currentPatch.draw(GameManager.getBatch(), getX(), getY(), getWidth(), getHeight());
          font.draw(GameManager.getBatch(), text, (getX() +  getWidth() / 2) - layout.width / 2, (getY() + getHeight() / 2) + layout.height / 2);

          GameManager.getBatch().end();
     }

     @Override
     public void dispose() {

     }

     @Override
     public boolean click(Vector2 localPosition) {
          colorTimer = 0;
          currentPatch = CLICKED_BUTTON;
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
}
