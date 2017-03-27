package me.benjozork.onyx.ui.object;

import me.benjozork.onyx.ui.UIElement;

/**
 * Created by Benjozork on 2017-03-20.
 */
public class Action {

    private ActionEvent event;
    private Runnable action;
    private String identifier;

    private UIElement parent;

    /**
     * An Action defines a code that is to be run by an UIElement when a specified ActionEvent takes place relative to the element.
     * @param parent the UIElement to which the Action is assigned.
     * @param identifier the identifier of the Action
     * @param action the code to be run when the event is triggered
     * @param event the ActionEvent to which the Action listens to
     */
    public Action(UIElement parent, String identifier, Runnable action, ActionEvent event) {
        this.parent = parent;
        this.identifier = identifier;
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
     * The Runnable that will be run by the action
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
     * The ActionEvent to which the Action listens
     * @return the ActionEvent
     */
    public ActionEvent getEvent() {
        return event;
    }

    /**
     * Sets the ActionEvent to which the Action listens
     * @param event the ActionEvent to be used
     */
    public void setEvent(ActionEvent event) {
        this.event = event;
    }

    /**
     * The UIElement to which the Action is assigned
     * @return the UIElement
     */
    public UIElement getParent() {
        return parent;
    }

    /**
     * Sets the UIElement to which the Action is assigned.
     * @param parent the UIElement to be used
     */
    public void setParent(UIElement parent) {
        this.parent = parent;
    }

}
