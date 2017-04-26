package me.benjozork.onyx.ui.container;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import me.benjozork.onyx.ui.UIElement;
import me.benjozork.onyx.utils.Utils;

/**
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
        if (parent != null) parent.addChild(this);
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

    public Vector2 getRelativePosition() {
        return relativePosition;
    }

    public Vector2 getAbsolutePosition() {
        float x = relativePosition.x;
        float y = relativePosition.y;
        if (parent != null) for (UIContainer par = getParent(); par.hasParent(); par = par.getParent()) {
            x += par.getRelativePosition().x;
            y += par.getRelativePosition().y;
        }
        return new Vector2(x, y);
    }


    public float getAbsoluteX() {
        return position.x;
    }

    public float getRelativeX() {
        return relativePosition.x;
    }

    public float getAbsoluteY() {
        return position.y;
    }

    public float getRelativeY() {
        return relativePosition.y;
    }

    public void setAbsoluteX(float x) {
        this.position.x = x;
    }

    public void setRelativeX(float x) {
        this.relativePosition.x = x;
    }

    public void setAbsoluteY(float y) {
        this.position.y = y;
    }

    public void setRelativeY(float y) {
        this.relativePosition.y = y;
    }

    // Dimension

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

    public void addElement(UIElement element) {
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

    public void addChild(UIContainer container) {
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