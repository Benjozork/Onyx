package me.benjozork.onyx.ui.object;

import me.benjozork.onyx.ui.UIElement;

/**
 * Defines a code that is to be run by an {@link UIElement} when<br/>
 * a specified {@link ActionEvent} takes place relative to the element.
 * @author Benjozork
 */
public class Action {

    private ActionEvent event;
    private Runnable action;
    private String identifier;

    private UIElement parent;

    /**
     * @param parent     the {@link UIElement} to which the Action is assigned
     * @param identifier the identifier of the Action
     * @param action     the code to be run when the event is triggered
     * @param event      the {@link ActionEvent} to which the Action listens to
     */
    public Action(UIElement parent, String identifier, Runnable action, ActionEvent event) {
        this.parent = parent;
        this.identifier = identifier;
        this.action = action;
        this.event = event;
    }

    /**
     * Runs the assigned code
     */
    public void run() {
        action.run();
    }

    /**
     * Returns the {@link Runnable} that will be run by the action
     * @return the Runnable
     */
    public Runnable getAction() {
        return action;
    }

    public void setAction(Runnable action) {
        this.action = action;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Returns the {@link ActionEvent} to which the Action listens
     */
    public ActionEvent getEvent() {
        return event;
    }

    /**
     * Sets the {@link ActionEvent} to which the Action listens
     */
    public void setEvent(ActionEvent event) {
        this.event = event;
    }

    /**
     * Returns the {@link UIElement} to which the Action is assigned
     */
    public UIElement getParent() {
        return parent;
    }

    /**
     * Sets the {@link UIElement} to which the Action is assigned.
     */
    public void setParent(UIElement parent) {
        this.parent = parent;
    }

}
