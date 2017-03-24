package me.benjozork.onyx.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Arrays;

import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.internal.Utils;

/**
 * Created by Benjozork on 2017-03-19.
 */
public class UIDropdown extends UIElement {

     private FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("core/assets/ui/cc_red_alert_inet.ttf"));
     private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
     private BitmapFont font;

     private GlyphLayout layout = new GlyphLayout();

     private Vector2 dimension = new Vector2();

     private String text = new String();

     private final Texture DROPDOWN_TEXTURE = new Texture("core/assets/ui/dropdown/dropdown_0.png");
     private final NinePatch DROPDOWN = new NinePatch(DROPDOWN_TEXTURE, 6, 40, 6, 6);
     private final Texture HOVERED_DROPDOWN_TEXTURE = new Texture("core/assets/ui/dropdown/dropdown_1.png");
     private final NinePatch HOVERED_DROPDOWN = new NinePatch(HOVERED_DROPDOWN_TEXTURE, 6, 40, 6, 6);
     private final Texture CLICKED_DROPDOWN_TEXTURE = new Texture("core/assets/ui/dropdown/dropdown_2.png");
     private final NinePatch CLICKED_DROPDOWN = new NinePatch(CLICKED_DROPDOWN_TEXTURE, 6, 40, 6, 6);

     private final Texture EXPANDED_DROPDOWN_TEXTURE = new Texture("core/assets/ui/dropdown/dropdown_3.png");
     private final NinePatch EXPANDED_DROPDOWN = new NinePatch(EXPANDED_DROPDOWN_TEXTURE, 6, 40, 6, 6);
     private final Texture EXPANDED_HOVERED_DROPDOWN_TEXTURE = new Texture("core/assets/ui/dropdown/dropdown_4.png");
     private final NinePatch EXPANDED_HOVERED_DROPDOWN = new NinePatch(EXPANDED_HOVERED_DROPDOWN_TEXTURE, 6, 40, 6, 6);
     private final Texture EXPANDED_CLICKED_DROPDOWN_TEXTURE = new Texture("core/assets/ui/dropdown/dropdown_5.png");
     private final NinePatch EXPANDED_CLICKED_DROPDOWN = new NinePatch(EXPANDED_CLICKED_DROPDOWN_TEXTURE, 6, 40, 6, 6);

     private final Texture EXPANDED_MENU_UPPER_TEXTURE = new Texture("core/assets/ui/dropdown/dropdown_menu_upper_0.png");
     private final NinePatch EXPANDED_MENU_UPPER = new NinePatch(EXPANDED_MENU_UPPER_TEXTURE, 6, 6, 0, 6);
     private final Texture EXPANDED_HOVERED_MENU_UPPER_TEXTURE = new Texture("core/assets/ui/dropdown/dropdown_menu_upper_1.png");
     private final NinePatch EXPANDED_HOVERED_MENU_UPPER = new NinePatch(EXPANDED_HOVERED_MENU_UPPER_TEXTURE, 6, 6, 0, 6);
     private final Texture EXPANDED_CLICKED_MENU_UPPER_TEXTURE = new Texture("core/assets/ui/dropdown/dropdown_menu_upper_2.png");
     private final NinePatch EXPANDED_CLICKED_MENU_UPPER = new NinePatch(EXPANDED_CLICKED_MENU_UPPER_TEXTURE, 6, 6, 0, 6);

     private final Texture EXPANDED_MENU_LOWER_TEXTURE = new Texture("core/assets/ui/dropdown/dropdown_menu_lower_0.png");
     private final NinePatch EXPANDED_MENU_LOWER = new NinePatch(EXPANDED_MENU_LOWER_TEXTURE, 6, 6, 0, 6);
     private final Texture EXPANDED_HOVERED_MENU_LOWER_TEXTURE = new Texture("core/assets/ui/dropdown/dropdown_menu_lower_1.png");
     private final NinePatch EXPANDED_HOVERED_MENU_LOWER = new NinePatch(EXPANDED_HOVERED_MENU_LOWER_TEXTURE, 6, 6, 0, 6);
     private final Texture EXPANDED_CLICKED_MENU_LOWER_TEXTURE = new Texture("core/assets/ui/dropdown/dropdown_menu_lower_2.png");
     private final NinePatch EXPANDED_CLICKED_MENU_LOWER = new NinePatch(EXPANDED_CLICKED_MENU_LOWER_TEXTURE, 6, 6, 0, 6);

     private NinePatch currentPatch = DROPDOWN;

     private float colorTimer = -1f, movementTimer;
     private float maxColorTimer = 0.1f, maxMovementTimer = 0.25f;

     private Array<String> items = new Array<String>();
     private boolean expanded = false;
     private boolean expanding, collapsing;

     private float movement_index = 0f;

     public UIDropdown(float x, float y, float width, float height, BitmapFont font, String text) {
          super(x, y);
          this.dimension.set(width, height);
          this.font = font;
          this.text = text;
     }

     public UIDropdown(Vector2 position, Vector2 dimension, BitmapFont font, String text) {
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
          bounds.width = getWidth();
          if (expanded) {
               bounds.height = ((items.size + 1) * getHeight()) - 6;
               bounds.y = getY() - (items.size * getHeight()) + 6;
               System.out.println(bounds);
          }  else {
               bounds.height = getHeight();
               bounds.y = getY();
          }

          if (colorTimer <= maxColorTimer && colorTimer > 0) {
               colorTimer += Utils.delta();
          } else {
               colorTimer = -1;
          }

          if (colorTimer == -1) {
               currentPatch = expanded ? EXPANDED_DROPDOWN : DROPDOWN;
          }

          if (hovering()) {
               currentPatch = expanded ? EXPANDED_HOVERED_DROPDOWN : HOVERED_DROPDOWN;
          }
     }

     @Override
     public void draw() {

          layout.setText(font, text);

          GameManager.getBatch().begin();

          if (expanded) {
               for (int i = 0; i < items.size; i++) {
                    if (i == items.size - 1) {
                         EXPANDED_MENU_LOWER.draw(GameManager.getBatch(), getX(), getY() - (getHeight() *  (i + 1)) + 6, getWidth(), getHeight());
                         break;
                    }
                    EXPANDED_MENU_UPPER.draw(GameManager.getBatch(), getX(), getY() - (getHeight() * (i + 1)) + 6, getWidth(), getHeight());
               }

               for (int i = 0; i < items.size; i++) {
                    layout.setText(font, items.get(i));
                    font.draw(GameManager.getBatch(), items.get(i), (getX() + getWidth() / 2) - layout.width / 2, getY() - (i * getHeight() + layout.height / 2));
               }

               layout.setText(font, text);
          }

          currentPatch.draw(GameManager.getBatch(), getX(), getY(), getWidth(), getHeight());
          font.draw(GameManager.getBatch(), text, (getX() + getWidth() / 2) - layout.width / 2, (getY() + getHeight() / 2) + layout.height / 2);
          GameManager.getBatch().end();
     }

     @Override
     public void dispose() {

     }

     @Override
     public boolean click(Vector2 localPosition) {
          if (expanded) {
               expanded = false;
          } else {
               expanded = true;
          }

          colorTimer = 0;
          currentPatch = expanded ? EXPANDED_CLICKED_DROPDOWN : CLICKED_DROPDOWN;
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

     public Array<String> getItems() {
          return items;
     }

     public void add(String... s) {
          for (String s1 : s) items.add(s1);
     }

}