package me.benjozork.onyx.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import me.benjozork.onyx.GameManager;
import me.benjozork.onyx.object.TextComponent;
import me.benjozork.onyx.ui.container.UIContainer;
import me.benjozork.onyx.ui.object.ActionEvent;
import me.benjozork.onyx.utils.PolygonHelper;
import me.benjozork.onyx.utils.Utils;

/**
 * @author Benjozork
 */
public class UIDropdown extends UIElement {

    // Unexpanded Dropdown textures

    private final Texture DROPDOWN_TEXTURE = new Texture("ui/dropdown/dropdown_0.png");
    private final NinePatch DROPDOWN = new NinePatch(DROPDOWN_TEXTURE, 6, 40, 6, 6);
    private final Texture HOVERED_DROPDOWN_TEXTURE = new Texture("ui/dropdown/dropdown_1.png");
    private final NinePatch HOVERED_DROPDOWN = new NinePatch(HOVERED_DROPDOWN_TEXTURE, 6, 40, 6, 6);
    private final Texture CLICKED_DROPDOWN_TEXTURE = new Texture("ui/dropdown/dropdown_2.png");
    private final NinePatch CLICKED_DROPDOWN = new NinePatch(CLICKED_DROPDOWN_TEXTURE, 6, 40, 6, 6);

    // Expanded Dropdown textures

    private final Texture EXPANDED_DROPDOWN_TEXTURE = new Texture("ui/dropdown/dropdown_3.png");
    private final NinePatch EXPANDED_DROPDOWN = new NinePatch(EXPANDED_DROPDOWN_TEXTURE, 6, 40, 6, 6);
    private final Texture EXPANDED_HOVERED_DROPDOWN_TEXTURE = new Texture("ui/dropdown/dropdown_4.png");
    private final NinePatch EXPANDED_HOVERED_DROPDOWN = new NinePatch(EXPANDED_HOVERED_DROPDOWN_TEXTURE, 6, 40, 6, 6);
    private final Texture EXPANDED_CLICKED_DROPDOWN_TEXTURE = new Texture("ui/dropdown/dropdown_5.png");
    private final NinePatch EXPANDED_CLICKED_DROPDOWN = new NinePatch(EXPANDED_CLICKED_DROPDOWN_TEXTURE, 6, 40, 6, 6);

    // Upper Dropdown menu textures

    private final Texture EXPANDED_MENU_UPPER_TEXTURE = new Texture("ui/dropdown/dropdown_menu_upper_0.png");
    private final NinePatch EXPANDED_MENU_UPPER = new NinePatch(EXPANDED_MENU_UPPER_TEXTURE, 6, 6, 0, 6);
    private final Texture EXPANDED_HOVERED_MENU_UPPER_TEXTURE = new Texture("ui/dropdown/dropdown_menu_upper_1.png");
    private final NinePatch EXPANDED_HOVERED_MENU_UPPER = new NinePatch(EXPANDED_HOVERED_MENU_UPPER_TEXTURE, 6, 6, 0, 0);
    private final Texture EXPANDED_CLICKED_MENU_UPPER_TEXTURE = new Texture("ui/dropdown/dropdown_menu_upper_2.png");
    private final NinePatch EXPANDED_CLICKED_MENU_UPPER = new NinePatch(EXPANDED_CLICKED_MENU_UPPER_TEXTURE, 6, 6, 0, 0);

    // Lower Dropdown menu textures

    private final Texture EXPANDED_MENU_LOWER_TEXTURE = new Texture("ui/dropdown/dropdown_menu_lower_0.png");
    private final NinePatch EXPANDED_MENU_LOWER = new NinePatch(EXPANDED_MENU_LOWER_TEXTURE, 6, 6, 0, 6);
    private final Texture EXPANDED_HOVERED_MENU_LOWER_TEXTURE = new Texture("ui/dropdown/dropdown_menu_lower_1.png");
    private final NinePatch EXPANDED_HOVERED_MENU_LOWER = new NinePatch(EXPANDED_HOVERED_MENU_LOWER_TEXTURE, 6, 6, 0, 6);
    private final Texture EXPANDED_CLICKED_MENU_LOWER_TEXTURE = new Texture("ui/dropdown/dropdown_menu_lower_2.png");
    private final NinePatch EXPANDED_CLICKED_MENU_LOWER = new NinePatch(EXPANDED_CLICKED_MENU_LOWER_TEXTURE, 6, 6, 0, 6);

    private TextComponent component;

    private String text = new String();
    private NinePatch currentPatch = DROPDOWN;

    private float colorTimer = - 1f;
    private final float maxColorTimer = 0.1f;

    private Array<String> items = new Array<String>();

    private boolean expanded = false;

    public UIDropdown(float x, float y, float width, float height, TextComponent component, UIContainer parent) {
        super(x, y, parent);
        bounds = PolygonHelper.getPolygon(x, y, width, height);
        setWidth(width);
        setHeight(height);
        this.component = component;
        this.text = component.getText();
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

        // Set hitbox position and dimensions according to expanded status and list length

        PolygonHelper.setWidth(bounds, getWidth());
        if (expanded) {
            PolygonHelper.setHeight(bounds, ((items.size + 1) * getHeight()) - 6);
            PolygonHelper.setY(bounds, getY() - (items.size * getHeight()) + 6);
        } else {
            PolygonHelper.setY(bounds, getY());
            PolygonHelper.setHeight(bounds, getHeight());
        }

        // Reset click backgroundColor

        if (colorTimer <= maxColorTimer && colorTimer > 0) {
            colorTimer += Utils.delta();
        } else {
            colorTimer = - 1;
        }

        // Assign textures depending on expanded/hovered status

        if (colorTimer == - 1) {
            currentPatch = expanded ? EXPANDED_DROPDOWN : DROPDOWN;
        }

        if (hovering()) {
            currentPatch = expanded ? EXPANDED_HOVERED_DROPDOWN : HOVERED_DROPDOWN;
        }

    }

    @Override
    public void draw() {

        // Draw list element textures

        if (expanded) {
            for (int i = 0; i < items.size; i++) {
                if (i == items.size - 1) {
                    EXPANDED_MENU_LOWER.draw (
                        GameManager.getBatch(),
                        getX(),
                        getY() - getHeight() * (i + 1) - 10,
                        getWidth(),
                        getHeight() + 10
                    );
                } else {
                    EXPANDED_MENU_UPPER.draw (
                        GameManager.getBatch(),
                        getX(),
                        getY() - getHeight() * (i + 1),
                        getWidth(),
                        getHeight() + 6
                    );
                }
            }

            // Find element to highlight depending on mouse position and change it's texture

            if (hovering()) {
                Vector2 mouse = Utils.unprojectGui(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
                float dy = getY() - mouse.y;
                int index = (int) (dy / getHeight());
                if (index < 0 || dy < 0) {
                    currentPatch.draw(GameManager.getBatch(), getX(), getY(), getWidth(), getHeight());
                    component.getFont().draw(GameManager.getBatch(), component.getText(), (getX() + getWidth() / 2) - component.getLayout().width / 2, (getY() + getHeight() / 2) + component.getLayout().height / 2);

                    drawText();

                    return;
                }

                if (index == items.size - 1) {
                    EXPANDED_HOVERED_MENU_LOWER.draw (
                        GameManager.getBatch(),
                        getX(),
                        getY() - getHeight() * (index + 1) - 10,
                        getWidth(),
                        getHeight() + 10
                    );
                } else {
                    EXPANDED_HOVERED_MENU_UPPER.draw (
                        GameManager.getBatch(),
                        getX(),
                        getY() - getHeight() * (index + 1),
                        getWidth(),
                        getHeight()
                    );
                }

            }

            // Draw list element texts

            drawText();

        }

        // Draw button

        currentPatch.draw(GameManager.getBatch(), getX(), getY(), getWidth(), getHeight());
        component.setText(text);
        component.drawCenteredInContainer(GameManager.getBatch(), getX(), getY(), getWidth(), getHeight());

    }

    @Override
    public boolean click(Vector2 localPosition) {

        // Find the element to select and change the button's text, then trigger an event accordingly

        if (expanded) {
            float position = localPosition.y;
            for (int i = 0; i < items.size; i++) {
                if (position < getY() - (i * getHeight())) {
                    if (position > getY() - getHeight() - ((i + 1) * getHeight())) {
                        text = items.get(i);
                        triggerEvent(ActionEvent.VALUE_CHANGED);
                    }
                }
            }

            expanded = false;
        } else {
            expanded = true;
        }

        colorTimer = 0;
        currentPatch = expanded ? EXPANDED_CLICKED_DROPDOWN : CLICKED_DROPDOWN;
        return true;
    }

    public void drawText() {
        for (int j = 0; j < items.size; j++) {
            component.setText(items.get(j));
            component.drawCenteredInContainer(GameManager.getBatch(), getX(), getY() - (j  * getHeight()) - getHeight(), getWidth(), getHeight());
        }
        component.setText(text);
    }

    @Override
    public void dispose() {
        // Dispose of textures
        DROPDOWN_TEXTURE.dispose();
        HOVERED_DROPDOWN_TEXTURE.dispose();
        CLICKED_DROPDOWN_TEXTURE.dispose();

        EXPANDED_DROPDOWN_TEXTURE.dispose();
        EXPANDED_HOVERED_DROPDOWN_TEXTURE.dispose();
        EXPANDED_CLICKED_DROPDOWN_TEXTURE.dispose();

        EXPANDED_MENU_UPPER_TEXTURE.dispose();
        EXPANDED_HOVERED_MENU_UPPER_TEXTURE.dispose();
        EXPANDED_CLICKED_MENU_UPPER_TEXTURE.dispose();

        EXPANDED_MENU_LOWER_TEXTURE.dispose();
        EXPANDED_HOVERED_MENU_LOWER_TEXTURE.dispose();
        EXPANDED_CLICKED_MENU_LOWER_TEXTURE.dispose();
    }

    /**
     * The items contained in the UIDropdown
     * @return the items
     */
    public Array<String> getItems() {
        return items;
    }

    /**
     * Adds an item to the list of items
     * @param s the item to add
     */
    public void add(String... s) {
        for (String s1 : s) items.add(s1);
    }

}