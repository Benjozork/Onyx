package me.benjozork.onyx.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.NoSuchElementException;

import me.benjozork.onyx.object.exception.DuplicateElementIdentifierException;
import me.benjozork.onyx.object.Drawable;
import me.benjozork.onyx.utils.Utils;

/**
 * Contains {@link UIElement}s
 * @author Benjozork
 */
public class UIScreen extends Drawable {

    protected Array<UIElement> elements = new Array<UIElement>();

    protected Vector2 dimension = new Vector2();

    protected UIScreen parent;

    public UIScreen(float x, float y) {
        super(x, y);
    }

    @Override
    public void init() {
        for (UIElement element : elements) {
            element.init();
        }
    }

    @Override
    public void update() {
        if (Gdx.input.justTouched()) {
           click();
        }

        for (UIElement element : elements) {
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
        for (UIElement element : elements) {
            element.dispose();
        }
    }

    public boolean click() {
        for (UIElement element : elements) {

            Vector2 mouse = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            Vector2 unprojected = Utils.unprojectGui(mouse);

            if (element.getBounds().contains(unprojected)) {
                element.clickElement(unprojected);
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
        if (elements.size > 1)
            throw new DuplicateElementIdentifierException("multiple elemnts with the same name in container");
        throw new NoSuchElementException();
    }

    public void add(UIElement e) {
        elements.add(e);
        e.init();
    }

    public UIScreen getParent() {
        return parent;
    }

    public void setParent(UIScreen parent) {
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
