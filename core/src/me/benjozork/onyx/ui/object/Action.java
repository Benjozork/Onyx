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

    private UIElement parent;

    /**
     * @param parent     the {@link UIElement} to which the Action is assigned.
     * @param action     the code to be run when the event is triggered
     * @param event      the {@link ActionEvent} to which the Action listens to
     */
    public Action(UIElement parent, Runnable action, ActionEvent event) {
        this.parent = parent;
        this.action = action;
        this.event = event;
    }

    /**
     * Run the assigned code
     */
    public void run() {
        action.run();
    }

    /**
     * The {@link Runnable} that will be run by the action
     * @return the Runnable
     */
    public Runnable getAction() {
        return action;
    }

    public void setAction(Runnable action) {
        this.action = action;
    }

    /**
     * The {@link ActionEvent} to which the Action listens
     * @return the {@link ActionEvent}
     */
    public ActionEvent getEvent() {
        return event;
    }

    /**
     * Sets the {@link ActionEvent} to which the Action listens
     * @param event the {@link ActionEvent} to be used
     */
    public void setEvent(ActionEvent event) {
        this.event = event;
    }

    /**
     * The {@link UIElement} to which the Action is assigned
     * @return the {@link UIElement}
     */
    public UIElement getParent() {
        return parent;
    }

    /**
     * Sets the UIElement to which the Action is assigned.
     * @param parent the {@link UIElement} to be used
     */
    public void setParent(UIElement parent) {
        this.parent = parent;
    }

}