package me.benjozork.onyx.ui;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import me.benjozork.onyx.object.Drawable;
import me.benjozork.onyx.ui.object.Action;
import me.benjozork.onyx.ui.object.ActionEvent;

/**
 * Created by Benjozork on 2017-03-19.
 */
public abstract class UIElement extends Drawable {

    private Array<Action> actions = new Array<Action>();

    private UIContainer parent;

    private String identifier;

    private boolean justHovered = false;

    private Vector2 dimensions = new Vector2();

    public UIElement(float x, float y) {
        super(new Vector2(x, y));
    }

    public UIElement(Vector2 position) {
        super(position);
    }

    public void update(float dt) {
        if (hovering()) {
            if (justHovered) return;
            triggerEvent(ActionEvent.HOVERED);
            justHovered = true;
        } else {
            justHovered = false;
        }
    }

    @Override
    public abstract void init();

    @Override
    public abstract void update();

    @Override
    public abstract void draw();

    public void triggerEvent(ActionEvent e) {
        for (Action a : actions) {
            if (a.getEvent() == e) {
                a.run();
            }
        }
    }

    public boolean clickElement(Vector2 localPosition) {
        click(localPosition);
        triggerEvent(ActionEvent.CLICKED);
        return true;
    }

    public abstract boolean click(Vector2 localPosition);

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public UIContainer getParent() {
        return parent;
    }

    public Array<Action> getActions() {
        return actions;
    }

    public void addAction(String identifier, Runnable action, ActionEvent event) {
        actions.add(new Action(this, identifier, action, event));
    }

    public float getWidth() {
        return dimensions.x;
    }

    public void setWidth(float v) {
        dimensions.x = v;
        bounds.width = v;
    }

    public float getHeight() {
        return dimensions.y;
    }

    public void setHeight(float v) {
        dimensions.y = v;
        bounds.height = v;
    }

    public void resize(float dx, float dy) {
        this.dimensions.x += dx;
        this.bounds.width += dx;
        this.dimensions.y += dy;
        this.bounds.height += dy;
    }

    public void setDimensions(float w, float h) {
        this.dimensions.x = w;
        this.bounds.width = w;
        this.dimensions.y = h;
        this.bounds.height = h;
    }

}
