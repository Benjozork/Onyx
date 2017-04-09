package me.benjozork.onyx.ui;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import me.benjozork.onyx.utils.PolygonHelper;
import me.benjozork.onyx.object.Drawable;
import me.benjozork.onyx.ui.object.Action;
import me.benjozork.onyx.ui.object.ActionEvent;

/**
 * Allows the user to interact with the UI
 * @author Benjozork
 */
public abstract class UIElement extends Drawable {

    private Array<Action> actions = new Array<Action>();

    private UIContainer parent;

    private String identifier;

    private boolean justHovered = false;

    private boolean enabled = true;

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

    /**
     * Triggers an {@link ActionEvent} and runs all the {@link Action} objects bound to this {@link ActionEvent}
     * @param e the {@link ActionEvent} to trigger
     */
    public void triggerEvent(ActionEvent e) {
        for (Action a : actions) {
            if (a.getEvent() == e) {
                a.run();
            }
        }
    }

    /**
     * Calls a click event
     * @param localPosition the position of the click
     * @return if the click was successful
     */
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

    /**
     * Returns {@link UIContainer} in which the element is stored
     */
    public UIContainer getParent() {
        return parent;
    }

    public Array<Action> getActions() {
        return actions;
    }

    /**
     * Adds a new {@link Action} to the element
     *
     * @param identifier the action's identifier
     * @param action     the code to execute
     * @param event      the {@link ActionEvent} to listen to
     */
    public void addAction(String identifier, Runnable action, ActionEvent event) {
        actions.add(new Action(this, identifier, action, event));
    }

    public float getWidth() {
        return dimensions.x;
    }

    public void setWidth(float v) {
        dimensions.x = v;
        PolygonHelper.setWidth(bounds, v);
    }

    public float getHeight() {
        return dimensions.y;
    }

    public void setHeight(float v) {
        dimensions.y = v;
        PolygonHelper.setHeight(bounds, v);
    }

    public void resize(float dx, float dy) {
        this.dimensions.x += dx;
        this.dimensions.y += dy;
        PolygonHelper.setDimensions(bounds, dimensions.x, dimensions.y);
    }

    public void setDimensions(float w, float h) {
        this.dimensions.x = w;
        this.dimensions.y = h;
        PolygonHelper.setDimensions(bounds, w, h);
    }

}
