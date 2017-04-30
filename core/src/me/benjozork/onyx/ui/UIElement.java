package me.benjozork.onyx.ui;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import me.benjozork.onyx.object.StaticDrawable;
import me.benjozork.onyx.ui.container.UIContainer;
import me.benjozork.onyx.ui.object.Action;
import me.benjozork.onyx.ui.object.ActionEvent;
import me.benjozork.onyx.ui.object.Anchor;
import me.benjozork.onyx.utils.PolygonHelper;

/**
 * Allows the user to interact with the UI
 * @author Benjozork
 */
public abstract class UIElement extends StaticDrawable {

    private Vector2 relativeOrigin = new Vector2();

    private Vector2 relativePosition = new Vector2();

    private Anchor snapMethod;

    private Array<Action> actions = new Array<Action>();

    private UIContainer parent;

    private boolean justHovered = false;

    private boolean enabled = true;

    private Vector2 dimensions = new Vector2();

    public UIElement(float x, float y, UIContainer parent) {
        super(x, y);
        this.parent = parent;
        this.relativeOrigin.set(0, 0);
        this.relativePosition.set(x, y);
        Vector2 absolute = getAbsolutePosition();
        this.position.set(absolute.x, absolute.y);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if (getPosition().x != getAbsolutePosition().x || getPosition().y != getAbsolutePosition().y) getPosition().set(getAbsolutePosition());
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

    @Override
    public abstract void dispose();

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

    /**
     * Returns {@link UIContainer} in which the element is stored
     */
    public UIContainer getParent() {
        return parent;
    }

    /**
     * Returns the {@link Action} objects that have been assigned to this element
     * @return an {@link Array<Action>}
     */
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

    public void resize(float w, float h) {
        this.dimensions.x = w;
        this.dimensions.y = h;
        PolygonHelper.setDimensions(bounds, w, h);
    }

    public void setDimensions(float width, float height) {
        this.dimensions.set(width, height);
    }

    public Vector2 getAbsolutePosition() {
        float x = relativePosition.x;
        float y = relativePosition.y;
        for (UIContainer par = getParent(); par.hasParent(); par = par.getParent()) {
            x += par.getRelativePosition().x;
            y += par.getRelativePosition().y;
        }
        x += relativeOrigin.x;
        y += relativeOrigin.y;

        return new Vector2(x, y);
    }

    // Overridden methods from StaticDrawable

    /**
     * Returns the container's position, relative to the parent container
     * @return a relatiive {@link Vector2}
     */
    public Vector2 getRelativePosition() {
        return relativePosition;
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

    /**
     * Snaps the element to another element, using the provided anchor
     * @param target the element to which this should be snapped to
     * @param anchor the anchor to use for the snapping
     */
    public void snapTo(UIElement target, Anchor anchor) { // NFP: Adjust snapping to keep relative position on movement
        switch (anchor) {
            case CENTER:
                throw new IllegalArgumentException("cannot use center anchor for snapping");
            case TOP_LEFT:
                this.setRelativeOrigin(target.getRelativeX() - getWidth(), target.getRelativeY() + target.getHeight());
                break;
            case TOP:
                this.setRelativeOrigin(target.getRelativeX() + target.getWidth() / 2 - getWidth() / 2, target.getRelativeY() + target.getHeight());
                break;
            case TOP_RIGHT:
                this.setRelativeOrigin(target.getRelativeX() + target.getWidth(), target.getRelativeY() + target.getHeight());
                break;
            case RIGHT:
                this.setRelativeOrigin(target.getRelativeX() + target.getWidth(), target.getRelativeY() + target.getHeight() / 2 - getHeight() / 2);
                break;
            case BOTTOM_RIGHT:
                this.setRelativeOrigin(target.getRelativeX() + target.getWidth(), target.getRelativeY() - getHeight() / 2);
                break;
            case BOTTOM:
                this.setRelativeOrigin(target.getRelativeX() + target.getWidth() / 2 - getWidth() / 2, target.getRelativeY() - getHeight());
                break;
            case BOTTOM_LEFT:
                this.setRelativeOrigin(target.getRelativeX() - getWidth(), target.getRelativeY() - getHeight());
                break;
            case LEFT:
                this.setRelativeOrigin(target.getRelativeX() - getWidth(), target.getRelativeY() + target.getWidth() / 2 - getWidth() / 2);
                break;
        }
    }

    private void setRelativeOrigin(float x, float y) {
        this.relativeOrigin.set(x, y);
    }

}