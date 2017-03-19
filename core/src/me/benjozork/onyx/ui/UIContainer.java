package me.benjozork.onyx.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.NoSuchElementException;

import me.benjozork.onyx.exception.DuplicateElementIdentifierException;
import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.object.Drawable;

/**
 * Created by Benjozork on 2017-03-19.
 */
public class UIContainer extends Drawable {

     protected Array<UIElement> elements = new Array<UIElement>();

     protected Vector2 dimension = new Vector2();

     protected UIContainer parent;

     public UIContainer(Vector2 position) {
          super(position);
     }

     @Override
     public void init() {
          for(UIElement element : elements) {
               System.out.println(element);
               element.init();
          }
     }

     @Override
     public void update() {
          for(UIElement element:elements){
               element.update();
          }
     }

     @Override
     public void draw() {
          for (UIElement element : elements) {
               element.draw();
          }
     }

     @Override
     public void dispose() {

     }

     public boolean click(Vector2 localPosition) {
          for (UIElement element : elements) {
               Rectangle elementBounds = element.getBounds();
               Rectangle localElementBounds = new Rectangle(0, 0, 0, 0);

               localElementBounds.setPosition(-((Gdx.graphics.getWidth() - element.getX()) - dimension.x), -((Gdx.graphics.getHeight() - element.getY()) - dimension.y));
               localElementBounds.setWidth(elementBounds.width);
               localElementBounds.setHeight(elementBounds.height);


               Vector3 unprojected = GameManager.getCamera().unproject(new Vector3(localPosition.x, localPosition.y, 0f));
               Vector2 unprojected2d = new Vector2(unprojected.x, unprojected.y);

               if (localElementBounds.contains(unprojected2d)) {
                    element.click(localPosition);
                    return true;
               }
          }
          return false;
     }

     public Array<UIElement> getElements() {
          return elements;
     }

     public UIElement getByIdentifier(String identifier) {
          Array<UIElement> val = new Array<UIElement>();
          for (UIElement element : elements) {
               if (element.getIdentifier().equals(identifier)) val.add(element);
          }
          if (elements.size > 1) throw new DuplicateElementIdentifierException("multiple elemnts with the same name in container");
          throw new NoSuchElementException();
     }

     public void add(UIElement e) {
          elements.add(e);
          e.init();
     }

     public UIContainer getParent() {
          return parent;
     }

     public void setParent(UIContainer parent) {
          this.parent = parent;
     }

     public Vector2 getDimension() {
          return dimension;
     }

     public void setDimension(Vector2 dimension) {
          this.dimension = dimension;
     }

     public void resize(int width, int height) {
          dimension.x = width;
          dimension.y = height;
     }
}
