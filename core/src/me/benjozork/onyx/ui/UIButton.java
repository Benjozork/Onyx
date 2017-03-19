package me.benjozork.onyx.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import me.benjozork.onyx.internal.GameManager;

/**
 * Created by Benjozork on 2017-03-19.
 */
public class UIButton extends UIElement {

     private BitmapFont font;

     private GlyphLayout layout = new GlyphLayout();

     private Runnable action;

     private Vector2 dimension = new Vector2();

     private String text = new String();

     private ShapeRenderer renderer;

     public UIButton(float x, float y, float width, float height, BitmapFont font, String text, Runnable action) {
          super(x, y);
          this.dimension.set(width, height);
          this.font = font;
          this.text = text;
          this.action = action;
          this.renderer = GameManager.getShapeRenderer();
     }

     public UIButton(Vector2 position, Vector2 dimension, BitmapFont font, String text, Runnable action) {
          super(position);
          this.dimension = dimension;
          this.font = font;
          this.text = text;
          this.action = action;
     }

     @Override
     public void init() {
          renderer = GameManager.getShapeRenderer();
          bounds = new Rectangle(getX(), getY(), dimension.x, dimension.y);
     }

     @Override
     public void update() {

     }

     @Override
     public void draw() {
          renderer.begin(ShapeRenderer.ShapeType.Filled);
          renderer.setColor(Color.GRAY);
          renderer.rect(getX(), getY(), dimension.x, dimension.y);
          renderer.setColor(Color.DARK_GRAY);
          renderer.rect(getX() + 5, getY() + 5, getWidth() - 10, getHeight() - 10);
          renderer.end();

          layout.setText(font, text);

          GameManager.getBatch().begin();
          font.draw(GameManager.getBatch(), text, (getX() +  getWidth() / 2) - layout.width / 2, (getY() + getHeight() / 2) + layout.height / 2);
          GameManager.getBatch().end();
     }

     @Override
     public void dispose() {

     }

     @Override
     public boolean click(Vector2 localPosition) {
          action.run();
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
