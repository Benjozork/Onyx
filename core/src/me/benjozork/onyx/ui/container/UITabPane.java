package me.benjozork.onyx.ui.container;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ArrayMap;

import me.benjozork.onyx.GameManager;
import me.benjozork.onyx.object.TextComponent;
import me.benjozork.onyx.ui.UIElement;
import me.benjozork.onyx.utils.Utils;

/**
 * Allows to display an {@link UIPane} from a list of selections, based on a clickable list.
 *
 * @author Benjozork
 */
public class UITabPane extends UIPane {

    // Horizontal tab textures

    private static final NinePatch HORIZONTAL_LEFT_TAB_TEXTURE = new NinePatch(new Texture("ui/tabpane/tabpane_tab_horizontal_left_0.png"), 2, 2, 2, 2);
    private static final NinePatch HOVERED_HORIZONTAL_LEFT_TAB_TEXTURE = new NinePatch(new Texture("ui/tabpane/tabpane_tab_horizontal_left_1.png"), 2, 2, 2, 2);
    private static final NinePatch CLICKED_HORIZONTAL_LEFT_TAB_TEXTURE = new NinePatch(new Texture("ui/tabpane/tabpane_tab_horizontal_left_2.png"), 2, 1, 2, 1);

    private static final NinePatch HORIZONTAL_CENTER_TAB_TEXTURE = new NinePatch(new Texture("ui/tabpane/tabpane_tab_horizontal_center_0.png"), 2, 2, 2, 2);
    private static final NinePatch HOVERED_HORIZONTAL_CENTER_TAB_TEXTURE = new NinePatch(new Texture("ui/tabpane/tabpane_tab_horizontal_center_1.png"), 2, 2, 2,2);
    private static final NinePatch CLICKED_HORIZONTAL_CENTER_TAB_TEXTURE = new NinePatch(new Texture("ui/tabpane/tabpane_tab_horizontal_center_2.png"), 1, 1, 2, 1);

    private static final NinePatch HORIZONTAL_RIGHT_TAB_TEXTURE = new NinePatch(new Texture("ui/tabpane/tabpane_tab_horizontal_right_0.png"), 2, 2, 2, 2);
    private static final NinePatch HOVERED_HORIZONTAL_RIGHT_TAB_TEXTURE = new NinePatch(new Texture("ui/tabpane/tabpane_tab_horizontal_right_1.png"), 2, 2, 2, 2);
    private static final NinePatch CLICKED_HORIZONTAL_RIGHT_TAB_TEXTURE = new NinePatch(new Texture("ui/tabpane/tabpane_tab_horizontal_right_2.png"), 1, 2, 2, 1);

    /*
     * The tab height
     */
    public float tabHeight = 50f;

    /*
     * The vertical offset to apply to a selected tab
     */
    public float tabSelectedVerticalOffset = 2f;

    private float tabRequiredSize;

    private ArrayMap<TextComponent, UIPane> tabs = new ArrayMap<TextComponent, UIPane>();

    private int currentTab = 0;

    private final SpriteBatch batch = GameManager.getBatch();

    private Vector2 mouse = new Vector2();

    public UITabPane(float x, float y, float w, float h, UIContainer parent) {
        super(x, y, w, h, parent);
    }

    /**
     * Adds an {@link UIPane} to the list along with it's {@link TextComponent}, used to represent the former.
     *
     * @param item the {@link TextComponent} to add in the clickable list
     * @param pane the {@link UIPane} to be added
     */
    public void addTab(TextComponent item, UIPane pane) {
        pane.setRelativeX(0);
        pane.setRelativeY(0);
        pane.setWidth(getWidth());
        pane.setHeight(getHeight());

        super.add(pane);
        tabs.put(item, pane);
            tabRequiredSize = getWidth() / tabs.size;
    }

    // Overridden methods

    @Override
    public void update() {
        if (Gdx.input.justTouched()) {
            click();
        }

        for (UIPane pane : tabs.values()) {
            pane.update();
            pane.setWidth(getWidth() - pane.getRelativeX());
            pane.setHeight(getHeight() - pane.getRelativeY());
        }

        tabRequiredSize = getWidth() / tabs.size;
    }

    @Override
    public void draw() {
        mouse.set(Gdx.input.getX(), Gdx.input.getY());
        mouse = Utils.unprojectGui(mouse);

        float dx = mouse.x - getAbsoluteX();
        int index = (int) (dx / tabRequiredSize);

        for (TextComponent component : tabs.keys()) {
            if (currentTab == tabs.indexOfKey(component)) {
                if (tabs.indexOfKey(component) == 0) {
                    CLICKED_HORIZONTAL_LEFT_TAB_TEXTURE.draw(batch, getAbsoluteX() + (tabRequiredSize * tabs.indexOfKey(component) + 1), getAbsoluteY() + getHeight(), tabRequiredSize, tabHeight + tabSelectedVerticalOffset);
                } else if (tabs.indexOfKey(component) == tabs.size - 1) {
                    CLICKED_HORIZONTAL_RIGHT_TAB_TEXTURE.draw(batch, getAbsoluteX() + (tabRequiredSize * tabs.indexOfKey(component) + 1), getAbsoluteY() + getHeight(), tabRequiredSize, tabHeight + tabSelectedVerticalOffset);
                } else {
                    CLICKED_HORIZONTAL_CENTER_TAB_TEXTURE.draw(batch, getAbsoluteX() + (tabRequiredSize * tabs.indexOfKey(component) + 1), getAbsoluteY() + getHeight(), tabRequiredSize, tabHeight + tabSelectedVerticalOffset);
                }
            } else if (hovering() && index == tabs.indexOfKey(component)) {
                    if (index == 0) {
                        HOVERED_HORIZONTAL_LEFT_TAB_TEXTURE.draw(batch, getAbsoluteX() + (tabRequiredSize * index + 1), getAbsoluteY() + getHeight(), tabRequiredSize, tabHeight + tabSelectedVerticalOffset);
                    } else if (index == tabs.size - 1) {
                        HOVERED_HORIZONTAL_RIGHT_TAB_TEXTURE.draw(batch, getAbsoluteX() + (tabRequiredSize * index + 1), getAbsoluteY() + getHeight(), tabRequiredSize, tabHeight + tabSelectedVerticalOffset);
                    } else {
                        HOVERED_HORIZONTAL_CENTER_TAB_TEXTURE.draw(batch, getAbsoluteX() + (tabRequiredSize * index + 1), getAbsoluteY() + getHeight(), tabRequiredSize, tabHeight + tabSelectedVerticalOffset);
                    }
            } else {
                if (tabs.indexOfKey(component) == 0) {
                    HORIZONTAL_LEFT_TAB_TEXTURE.draw(batch, getAbsoluteX() + (tabRequiredSize * tabs.indexOfKey(component) + 1), getAbsoluteY() + getHeight(), tabRequiredSize, tabHeight);
                } else if (tabs.indexOfKey(component) == tabs.size - 1) {
                    HORIZONTAL_RIGHT_TAB_TEXTURE.draw(batch, getAbsoluteX() + (tabRequiredSize * tabs.indexOfKey(component) + 1), getAbsoluteY() + getHeight(), tabRequiredSize, tabHeight);
                } else {
                    HORIZONTAL_CENTER_TAB_TEXTURE.draw(batch, getAbsoluteX() + (tabRequiredSize * tabs.indexOfKey(component) + 1), getAbsoluteY() + getHeight(), tabRequiredSize, tabHeight);
                }
            }
            component.drawCenteredInContainer(batch, getAbsoluteX() + (tabRequiredSize * tabs.indexOfKey(component) + 1), getAbsoluteY() + getHeight(), tabRequiredSize, tabHeight);
        }

        tabs.getValueAt(currentTab).draw();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public boolean click() {
        Vector2 mouse = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        Vector2 unprojected = Utils.unprojectWorld(mouse);

        if (unprojected.x > getAbsoluteX()
                && unprojected.x < getAbsoluteX() + getWidth()
                && unprojected.y > getAbsoluteY() + getHeight()
                && unprojected.y < getAbsoluteY() + getHeight() + tabHeight) {
            float dx = unprojected.x - getAbsoluteX();
            currentTab = (int) (dx / tabRequiredSize);
            return true;
        }
        return false;
    }

    @Override
    public void add(UIElement e) {
        throw new IllegalStateException("cannot add elements or containers to UITabPane");
    }

    @Override
    public void add(UIContainer e) {
        throw new IllegalStateException("cannot add elements or containers to UITabPane");
    }

    private boolean hovering() {
        return (mouse.x > getAbsoluteX() && mouse.x < getAbsoluteX() + getWidth() &&
        mouse.y > getAbsoluteY() + getHeight() && mouse.y < getAbsoluteY() + getHeight() + tabHeight);
    }

}