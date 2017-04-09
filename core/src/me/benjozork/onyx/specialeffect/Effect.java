package me.benjozork.onyx.specialeffect;

/**
 * Represents a graphical effect applied to components of the game
 * @author Benjozork
 */
public abstract class Effect {

    protected boolean isActive;

    /**
     * Internally updates and applies the Effect
     */
    public abstract void update();

    /**
     * Pauses execution of the Effect
     */
    public void pause() {
        isActive = false;
    }

    /**
     * Resumes execution of the Effect
     */
    public void resume() {
        isActive = true;
    }

    /**
     * Toggles the state of execution of the Effect
     */
    public void toggle() {
        isActive = ! isActive;
    }

    /**
     * Returns whether the Effect is currently running
     */
    public boolean isActive() {
        return isActive;
    }

}
