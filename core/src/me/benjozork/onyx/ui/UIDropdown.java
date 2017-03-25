package me.benjozork.onyx.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.internal.Utils;
import me.benjozork.onyx.ui.object.ActionEvent;
import me.benjozork.onyx.ui.object.TextComponent;

/**
 * Created by Benjozork on 2017-03-19.
 */
public class UIDropdown extends UIElement {

    private final Texture DROPDOWN_TEXTURE = new Texture("ui/dropdown/dropdown_0.png");
    private final NinePatch DROPDOWN = new NinePatch(DROPDOWN_TEXTURE, 6, 40, 6, 6);
    private final Texture HOVERED_DROPDOWN_TEXTURE = new Texture("ui/dropdown/dropdown_1.png");
    private final NinePatch HOVERED_DROPDOWN = new NinePatch(HOVERED_DROPDOWN_TEXTURE, 6, 40, 6, 6);
    private final Texture CLICKED_DROPDOWN_TEXTURE = new Texture("ui/dropdown/dropdown_2.png");
    private final NinePatch CLICKED_DROPDOWN = new NinePatch(CLICKED_DROPDOWN_TEXTURE, 6, 40, 6, 6);

    // Textures
    private final Texture EXPANDED_DROPDOWN_TEXTURE = new Texture("ui/dropdown/dropdown_3.png");
    private final NinePatch EXPANDED_DROPDOWN = new NinePatch(EXPANDED_DROPDOWN_TEXTURE, 6, 40, 6, 6);
    private final Texture EXPANDED_HOVERED_DROPDOWN_TEXTURE = new Texture("ui/dropdown/dropdown_4.png");
    private final NinePatch EXPANDED_HOVERED_DROPDOWN = new NinePatch(EXPANDED_HOVERED_DROPDOWN_TEXTURE, 6, 40, 6, 6);
    private final Texture EXPANDED_CLICKED_DROPDOWN_TEXTURE = new Texture("ui/dropdown/dropdown_5.png");
    private final NinePatch EXPANDED_CLICKED_DROPDOWN = new NinePatch(EXPANDED_CLICKED_DROPDOWN_TEXTURE, 6, 40, 6, 6);
    private final Texture EXPANDED_MENU_UPPER_TEXTURE = new Texture("ui/dropdown/dropdown_menu_upper_0.png");
    private final NinePatch EXPANDED_MENU_UPPER = new NinePatch(EXPANDED_MENU_UPPER_TEXTURE, 6, 6, 0, 6);
    private final Texture EXPANDED_HOVERED_MENU_UPPER_TEXTURE = new Texture("ui/dropdown/dropdown_menu_upper_1.png");
    private final NinePatch EXPANDED_HOVERED_MENU_UPPER = new NinePatch(EXPANDED_HOVERED_MENU_UPPER_TEXTURE, 6, 6, 0, 0);
    private final Texture EXPANDED_CLICKED_MENU_UPPER_TEXTURE = new Texture("ui/dropdown/dropdown_menu_upper_2.png");
    private final NinePatch EXPANDED_CLICKED_MENU_UPPER = new NinePatch(EXPANDED_CLICKED_MENU_UPPER_TEXTURE, 6, 6, 0, 0);
    private final Texture EXPANDED_MENU_LOWER_TEXTURE = new Texture("ui/dropdown/dropdown_menu_lower_0.png");
    private final NinePatch EXPANDED_MENU_LOWER = new NinePatch(EXPANDED_MENU_LOWER_TEXTURE, 6, 6, 0, 6);
    private final Texture EXPANDED_HOVERED_MENU_LOWER_TEXTURE = new Texture("ui/dropdown/dropdown_menu_lower_1.png");
    private final NinePatch EXPANDED_HOVERED_MENU_LOWER = new NinePatch(EXPANDED_HOVERED_MENU_LOWER_TEXTURE, 6, 6, 0, 6);
    private final Texture EXPANDED_CLICKED_MENU_LOWER_TEXTURE = new Texture("ui/dropdown/dropdown_menu_lower_2.png");
    private final NinePatch EXPANDED_CLICKED_MENU_LOWER = new NinePatch(EXPANDED_CLICKED_MENU_LOWER_TEXTURE, 6, 6, 0, 6);
    private FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/cc_red_alert_inet.ttf"));
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private BitmapFont font;
    private GlyphLayout layout = new GlyphLayout();
    private Vector2 dimension = new Vector2();
    private String text = new String();
    private NinePatch currentPatch = DROPDOWN;

    private float colorTimer = - 1f, movementTimer;
    private float maxColorTimer = 0.1f, maxMovementTimer = 0.25f;

    private Array<String> items = new Array<String>();
    private int currentItem;
    private boolean expanded = false;

    public UIDropdown(float x, float y, float width, float height, TextComponent component) {
        super(x, y);
        bounds = new Rectangle(getX(), getY(), width, height);
        setWidth(width);
        setHeight(height);
        this.text = component.getText();
        this.generator = new FreeTypeFontGenerator(Gdx.files.internal(component.getFontPath()));
        this.parameter = component.getParameter();
    }

    @Override
    public void init() {
        font = generator.generateFont(parameter);
    }

    @Override
    public void update() {
        layout.setText(font, text);
        bounds.width = getWidth();
        if (expanded) {
            bounds.height = ((items.size + 1) * getHeight()) - 6;
            bounds.y = getY() - (items.size * getHeight()) + 6;
        } else {
            bounds.height = getHeight();
            bounds.y = getY();
        }


        if (colorTimer <= maxColorTimer && colorTimer > 0) {
            colorTimer += Utils.delta();
        } else {
            colorTimer = - 1;
        }

        if (colorTimer == - 1) {
            currentPatch = expanded ? EXPANDED_DROPDOWN : DROPDOWN;
        }

        if (hovering()) {
            // Do element highlighting
            currentPatch = expanded ? EXPANDED_HOVERED_DROPDOWN : HOVERED_DROPDOWN;
        }
    }

    @Override
    public void draw() {

        layout.setText(font, text);

        if (expanded) {
            for (int i = 0; i < items.size; i++) {
                if (i == items.size - 1) {
                    GameManager.getBatch().begin();
                    EXPANDED_MENU_LOWER.draw(
                            GameManager.getBatch(),
                            getX(),
                            getY() - getHeight() * (i + 1),
                            getWidth(),
                            getHeight()
                    );
                    GameManager.getBatch().end();
                } else {
                    GameManager.getBatch().begin();
                    EXPANDED_MENU_UPPER.draw(
                            GameManager.getBatch(),
                            getX(),
                            getY() - getHeight() * (i + 1),
                            getWidth(),
                            getHeight()
                    );
                    GameManager.getBatch().end();
                }
            }

            if (hovering()) {
                Vector2 mouse = Utils.unprojectGui(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
                float dy = getY() - mouse.y;
                int index = (int) (dy / getHeight());
                if (index < 0 || dy < 0) {
                    GameManager.getBatch().begin();
                    currentPatch.draw(GameManager.getBatch(), getX(), getY(), getWidth(), getHeight());
                    font.draw(GameManager.getBatch(), text, (getX() + getWidth() / 2) - layout.width / 2, (getY() + getHeight() / 2) + layout.height / 2);
                    GameManager.getBatch().end();

                    drawText();

                    return;
                }

                if (index == items.size - 1) {
                    GameManager.getBatch().begin();
                    EXPANDED_HOVERED_MENU_LOWER.draw(
                            GameManager.getBatch(),
                            getX(),
                            getY() - getHeight() * (index + 1),
                            getWidth(),
                            getHeight()
                    );
                    GameManager.getBatch().end();
                } else {
                    GameManager.getBatch().begin();
                    EXPANDED_HOVERED_MENU_UPPER.draw(
                            GameManager.getBatch(),
                            getX(),
                            getY() - getHeight() * (index + 1),
                            getWidth(),
                            getHeight()
                    );
                    GameManager.getBatch().end();
                }

            }

            drawText();

            layout.setText(font, text);
        }

        GameManager.getBatch().begin();
        currentPatch.draw(GameManager.getBatch(), getX(), getY(), getWidth(), getHeight());
        font.draw(GameManager.getBatch(), text, (getX() + getWidth() / 2) - layout.width / 2, (getY() + getHeight() / 2) + layout.height / 2);
        GameManager.getBatch().end();
    }

    @Override
    public boolean click(Vector2 localPosition) {
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
        GameManager.getBatch().begin();
        for (int j = 0; j < items.size; j++) {
            layout.setText(font, items.get(j));
            font.draw(GameManager.getBatch(), items.get(j), (getX() + getWidth() / 2) - layout.width / 2, getY() - (j * getHeight() + layout.height / 2) - 4);
        }
        GameManager.getBatch().end();
    }

    @Override
    public void dispose() {

    }

    public String getText() {
        return text;
    }

    public void setText(String v) {
        text = v;
    }

    public Array<String> getItems() {
        return items;
    }

    public void add(String... s) {
        for (String s1 : s) items.add(s1);
    }

}