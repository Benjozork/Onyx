package me.benjozork.onyx.specialeffect;

/**
 * @author Benjozork
 */
public abstract class Effect {

    protected boolean isActive;

    public abstract void update();

    public void pause() {
        isActive = false;
    }

    public void resume() {
        isActive = true;
    }

    public void toggle() {
        isActive = ! isActive;
    }

    public boolean isActive() {
        return isActive;
    }

}
