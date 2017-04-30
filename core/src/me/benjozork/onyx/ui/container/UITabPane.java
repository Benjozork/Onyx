package me.benjozork.onyx.ui.container;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ArrayMap;

import me.benjozork.onyx.GameManager;
import me.benjozork.onyx.object.TextComponent;
import me.benjozork.onyx.ui.UIElement;
import me.benjozork.onyx.ui.object.Anchor;
import me.benjozork.onyx.ui.object.TabLayout;
import me.benjozork.onyx.utils.Utils;

/**
 * Allows to display an {@link UIPane} from a list of selections, based on a clickable list.
 * @author Benjozork
 */
public class UITabPane extends UIPane { // NFP: Undone layout-based drawing, no customizable drawing methods/textures

    /**
     * The position of the tabs.<br/>
     * Valid values:</br>
     *  - TOP, RIGHT, BOTTOM, LEFT
     */
    public TabLayout layout = TabLayout.TOP;

    /**
     * The background color of the displayed {@link UIPane}
     */
    public Color selectedPaneBackgroundColor = Utils.rgba(160, 160, 160, 200);

    /**
     * The color of an unselected item from the list
     */
    public Color selectorBackgroundColorUnselected = Utils.rgba(130, 130, 130, 200);

    /**
     * The color of a selected item from the list
     */
    public Color selectorBackgroundColorSelected = Utils.rgba(160, 160, 160, 110);

    /**
     * The width of the list
     */
    public float selectorItemWidth = 80;

    // Private members

    private float SELECTOR_ITEM_HEIGHT;

    private ArrayMap<TextComponent, UIPane> selectiorItems = new ArrayMap<TextComponent, UIPane>();
    private int currentPane = 0;

    public UITabPane(float x, float y, float w, float h, UIContainer parent) {
        super(x, y, w, h, parent);
    }

    /**
     * Adds an {@link UIPane} to the list along with it's {@link TextComponent}, used to represent the former.
     * @param item the {@link TextComponent} to add in the clickable list
     * @param pane the {@link UIPane} to be added
     */
    public void addSelection(TextComponent item, UIPane pane) {
        pane.setRelativeX(selectorItemWidth);
        pane.setRelativeY(0);
        pane.setWidth(getWidth() - selectorItemWidth);
        pane.setHeight(getHeight());
        super.add(pane);
        selectiorItems.put(item, pane);
        SELECTOR_ITEM_HEIGHT = getHeight() / selectiorItems.size;
    }

    // Overridden methods

    @Override
    public void update() {
        if (Gdx.input.justTouched()) {
            click();
        }

        selectiorItems.getValueAt(currentPane).update();
        selectiorItems.getValueAt(currentPane).setWidth(getWidth() - selectiorItems.getValueAt(currentPane).getRelativeX());
        selectiorItems.getValueAt(currentPane).update();
        selectiorItems.getValueAt(currentPane).setHeight(getHeight() - selectiorItems.getValueAt(currentPane).getRelativeY());

        SELECTOR_ITEM_HEIGHT = getHeight() / selectiorItems.size;
    }

    @Override
    public void draw() { // @TODO: Improve this horrible drawing method, add customizations
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        GameManager.setIsShapeRendering(true);
        GameManager.getRenderer().set(ShapeRenderer.ShapeType.Filled);
        GameManager.getRenderer().setColor(selectedPaneBackgroundColor);
        GameManager.getRenderer().rect(getAbsoluteX() + selectorItemWidth, getAbsoluteY(), getWidth() - selectorItemWidth, getHeight());
        GameManager.setIsShapeRendering(false);
        Gdx.gl.glDisable(GL20.GL_BLEND);
        for (TextComponent component : selectiorItems.keys()) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            GameManager.setIsShapeRendering(true);
            GameManager.getRenderer().set(ShapeRenderer.ShapeType.Filled);
            if (selectiorItems.indexOfKey(component) != currentPane) {
                GameManager.getRenderer().setColor(selectorBackgroundColorUnselected);
            } else GameManager.getRenderer().setColor(selectorBackgroundColorSelected);

            GameManager.getRenderer().rect(getAbsoluteX(), getAbsoluteY() + getHeight() - (SELECTOR_ITEM_HEIGHT * (selectiorItems.indexOfKey(component) + 1)), selectorItemWidth, SELECTOR_ITEM_HEIGHT);
            GameManager.setIsShapeRendering(false);
            Gdx.gl.glDisable(GL20.GL_BLEND);
            component.drawCenteredInContainer(GameManager.getBatch(), getAbsoluteX(), getAbsoluteY() + getHeight() - ((selectiorItems.indexOfKey(component) + 1) * SELECTOR_ITEM_HEIGHT), selectorItemWidth, SELECTOR_ITEM_HEIGHT);
        }

        selectiorItems.getValueAt(currentPane).draw();
    }

    @Override
    public boolean click() {
        Vector2 mouse = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        Vector2 unprojected = Utils.unprojectWorld(mouse);

        if (unprojected.x > getAbsoluteX()
            && unprojected.x < getAbsoluteX() + selectorItemWidth
            && unprojected.y > getAbsoluteY()
            && unprojected.y < getAbsoluteY() + getHeight()) {
            float dy = getAbsoluteY() + getHeight() - unprojected.y;
            currentPane = (int) (dy / SELECTOR_ITEM_HEIGHT);
            return true;
        }
        return false;
    }

    @Override
    public void add(UIElement e) {
         throw new IllegalStateException("cannot add elements or containers to UITabPane");
    }

    @Override
    public void add(UIElement e, Anchor a) {
        throw new IllegalStateException("cannot add elements or containers to UITabPane");
    }

    @Override
    public void add(UIContainer e) {
        throw new IllegalStateException("cannot add elements or containers to UITabPane");
    }

}