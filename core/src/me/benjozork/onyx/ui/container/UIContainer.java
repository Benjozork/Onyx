package me.benjozork.onyx.ui.container;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import me.benjozork.onyx.ui.UIElement;
import me.benjozork.onyx.ui.object.Anchor;
import me.benjozork.onyx.utils.Utils;

/**
 * Displays and organizes {@link UIElement} objects.<br/>
 * Can also contain other {@link UIContainer} objects, described as children. They will<br/>
 * be updated, drawn and disposed of at the same time as the parent {@link UIContainer}.
 *
 * @author Benjozork
 */
public abstract class UIContainer {

    private Vector2 position = new Vector2();

    private Vector2 relativePosition = new Vector2();

    private Vector2 dimension = new Vector2();

    private UIContainer parent;

    private Array<UIElement> elements = new Array<UIElement>();

    private Array<UIContainer> children = new Array<UIContainer>();

    public UIContainer(float x, float y, float w, float h, UIContainer parent) {
        this.parent = parent;
        this.relativePosition.set(x, y);
        Vector2 absolute = getAbsolutePosition();
        this.position.set(absolute.x, absolute.y);
        this.dimension.set(w, h);
    }

    public void update() {
        if (Gdx.input.justTouched()) {
            click();
        }

        for (UIElement element : elements) {
            element.update(Utils.delta());
            element.update();
        }

        for (UIContainer container : children) {
            container.update();
        }
    }

    public void draw() {
        for (UIElement element : elements) {
            element.draw();
        }

        for (UIContainer container : children) {
            container.draw();
        }
    }

    public void dispose() {
        for (UIElement element : elements) {
            element.dispose();
        }

        for (UIContainer container : children) {
            container.dispose();
        }
    }

    // Position

    /**
     * Returns the container's position, relative to the parent container
     * @return a relatiive {@link Vector2}
     */
    public Vector2 getRelativePosition() {
        return relativePosition;
    }

    /**
     * Returns the container's position, relative to the screen origin
     * @return an aboslutely relative {@link Vector2}
     */
    public Vector2 getAbsolutePosition() {
        float x = relativePosition.x;
        float y = relativePosition.y;
        if (parent != null) for (UIContainer par = getParent(); par.hasParent(); par = par.getParent()) {
            x += par.getRelativePosition().x;
            y += par.getRelativePosition().y;
        }
        return new Vector2(x, y);
    }

    /**
     * Returns the x coordinate, relative to the screen origin
     * @return an absolutely relative {@link Vector2}
     */
    public float getAbsoluteX() {
        return position.x;
    }

    /**
     * Returns the x coordinate, relative to the parent {@link UIContainer}
     * @return a relative {@link Vector2}
     */
    public float getRelativeX() {
        return relativePosition.x;
    }

    /**
     * Returns the y coordinate, relative to the screen origin
     * @return an absolutely relative {@link Vector2}
     */
    public float getAbsoluteY() {
        return position.y;
    }

    /**
     * Returns the y coordinate, relative to the parent {@link UIContainer}
     * @return a relative {@link Vector2}
     */
    public float getRelativeY() {
        return relativePosition.y;
    }

    /**
     * Sets the x coordinate, relative to the screen origin
     */
    public void setAbsoluteX(float x) {
        this.position.x = x;
    }

    /**
     * Sets the x coordinate, relative to the parent {@link UIContainer}
     */
    public void setRelativeX(float x) {
        this.relativePosition.x = x;
    }

    /**
     * Sets the y coordinate, relative to the screen origin
     */
    public void setAbsoluteY(float y) {
        this.position.y = y;
    }

    /**
     * Sets the y coordinate, relative to the parent {@link UIContainer}
     */
    public void setRelativeY(float y) {
        this.relativePosition.y = y;
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

    public Vector2 getDimension() {
        return dimension;
    }

    public UIContainer getParent() {
        return parent;
    }

    public void setParent(UIContainer parent) {
        this.parent = parent;
    }

    public Array<UIElement> getElements() {
        return elements;
    }

    public void add(UIElement element) {
        elements.add(element);
    }

    public void add(UIElement element, Anchor relativeAnchor) {
        switch (relativeAnchor) {
            case CENTER:
                element.setRelativeX(element.getRelativeX() + (getWidth() / 2) - element.getWidth() / 2);
                element.setRelativeY(element.getRelativeY() + (getHeight() / 2) - element.getHeight() / 2);
                break;
            case TOP_LEFT:
                element.setRelativeY(getHeight() - element.getHeight() + element.getRelativeY());
                break;
            case TOP:
                element.setRelativeX(element.getRelativeX() + (getWidth() / 2) - element.getWidth() / 2);
                element.setRelativeY(getHeight() - element.getHeight() + element.getRelativeY());
                break;
            case TOP_RIGHT:
                element.setRelativeX(element.getRelativeX() + getWidth() - element.getWidth());
                element.setRelativeY(getHeight() - element.getHeight() + element.getRelativeY());
                break;
            case RIGHT:
                element.setRelativeX(element.getRelativeX() + getWidth() - element.getWidth());
                element.setRelativeY(element.getRelativeY() + (getHeight() / 2) - element.getHeight() / 2);
                break;
            case BOTTOM_RIGHT:
                element.setRelativeX(element.getRelativeX() + getWidth() - element.getWidth());
                break;
            case BOTTOM:
                element.setRelativeX(element.getRelativeX() + (getWidth() / 2) - element.getWidth() / 2);
                break;
            case BOTTOM_LEFT:
                element.setPosition(element.getAbsolutePosition());
                break;
            case LEFT:
                element.setRelativeY(element.getRelativeY() + (getHeight() / 2) - element.getHeight() / 2);
                break;
        }
        elements.add(element);
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

    public void resize(float w, float h) {
        this.dimension.set(w, h);
    }

    public boolean hasParent() {
        return parent != null;
    }

    public Array<UIContainer> getChildren() {
        return children;
    }

    public void add(UIContainer container) {
        children.add(container);
    }

    public float getWidth() {
        return dimension.x;
    }

    public void setWidth(float width) {
        this.dimension.x = width;
    }

    public float getHeight() {
        return dimension.y;
    }

    public void setHeight(float height) {
        this.dimension.y = height;
    }

}