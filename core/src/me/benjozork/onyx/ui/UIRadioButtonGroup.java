package me.benjozork.onyx.ui;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import me.benjozork.onyx.internal.PolygonHelper;
import me.benjozork.onyx.ui.object.ActionEvent;

/**
 * Created by Benjozork on 2017-03-24.
 */
public class UIRadioButtonGroup extends UIElement {

    private Array<UIRadioButton> buttons = new Array<UIRadioButton>();
    private int selected;

    public UIRadioButtonGroup() {
        super(0, 0);
    }

    /**
     * The button that is currently selected
     * @return the button's index
     */
    public int getSelected() {
        return selected;
    }

    /**
     * Sets the button that is currently selected
     * @param selected the button's index
     */
    public void setSelected(int selected) {
        this.selected = selected;
    }

    /**
     * The list of buttons that the UIRadioButtonGroup contains
     * @return the list of buttons
     */
    public Array<UIRadioButton> getButtons() {
        return buttons;
    }

    /**
     * Adds a button to the list
     * @param b the button
     */
    public void addButton(UIRadioButton b) {
        b.setGroup(this);
        buttons.add(b);
    }

    /**
     * Selects a button from the list
     * @param b the button
     */
    public void select(UIRadioButton b) {
        triggerEvent(ActionEvent.VALUE_CHANGED);
        selected = buttons.indexOf(b, true);
        b.set(true);
        if (selected == - 1) {
            selected = 0;
        }
        for (UIRadioButton button : buttons) {
            if (buttons.indexOf(button, true) != selected) {
                button.set(false);
            }
        }
    }

    @Override
    public void init() {
        bounds= PolygonHelper.getPolygon(0,0,0,0);
        selected = buttons.size - 1;
    }

    @Override
    public void update() {
        for (UIRadioButton button : buttons) {
            if (selected == buttons.indexOf(button, true)) {
                button.set(true);
            }
        }
    }

    @Override
    public void draw() {

    }

    @Override
    public boolean click(Vector2 localPosition) {
        return false;
    }

    @Override
    public void dispose() {
        for (UIRadioButton b : buttons) {
            b.dispose();
        }
    }
}
