package me.benjozork.onyx.ui;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import me.benjozork.onyx.ui.container.UIContainer;
import me.benjozork.onyx.ui.object.ActionEvent;
import me.benjozork.onyx.utils.PolygonHelper;

/**
 * @author Benjozork
 */
public class UIRadioButtonGroup extends UIElement {

    private Array<UIRadioButton> buttons = new Array<UIRadioButton>();
    private int selected;

    public UIRadioButtonGroup(UIContainer parent) {
        super(0, 0, parent);
        this.bounds = PolygonHelper.getPolygon(0, 0, 0,0);
        selected = 0;
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

    public void addButton(UIRadioButton uiRadioButton) {
        buttons.add(uiRadioButton);
    }

}