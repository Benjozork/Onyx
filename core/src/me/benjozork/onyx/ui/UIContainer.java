package me.benjozork.onyx.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.NoSuchElementException;

import me.benjozork.onyx.exception.DuplicateElementIdentifierException;
import me.benjozork.onyx.utils.Utils;
import me.benjozork.onyx.object.Drawable;

/**
 * Contains {@link UIElement}s
 * @author Benjozork
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
        for (UIElement element : elements) {
            element.init();
        }
    }

    @Override
    public void update() {
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

    public boolean click(Vector2 localPosition) {
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
