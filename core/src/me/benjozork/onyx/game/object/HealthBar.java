package me.benjozork.onyx.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ArrayMap;

import java.math.BigDecimal;

import me.benjozork.onyx.GameManager;
import me.benjozork.onyx.config.Configs;
import me.benjozork.onyx.config.ProjectConfig;
import me.benjozork.onyx.game.entity.LivingEntity;
import me.benjozork.onyx.object.StaticDrawable;
import me.benjozork.onyx.object.TextComponent;
import me.benjozork.onyx.utils.CenteredDrawer;
import me.benjozork.onyx.utils.PolygonHelper;
import me.benjozork.onyx.utils.Utils;

/**
 * @author Benjozork
 */
public class HealthBar extends StaticDrawable {

    private LivingEntity parent;

    private float width, height;

    private float maxValue, value;

    private ArrayMap<Integer, Color> healthColors = new ArrayMap<Integer, Color>();

    private TextComponent component;

    private ShapeRenderer renderer = GameManager.getRenderer();

    private final Color BACKGROUND_COLOR = Utils.rgba(0, 0, 0, 200);

    private final float VALUE_DELTA = 70f;

    private final float HEALTH_TEXT_VERTICAL_OFFSET = 5;

    private BigDecimal bd;

    public HealthBar(LivingEntity parent, float width, float height, float maxValue) {
        super(parent.getX(), parent.getY());

        this.parent = parent;
        this.position = parent.getPosition().cpy();

        this.width = width;
        this.height = height;

        this.bounds = PolygonHelper.getPolygon(getX(), getY(), width, height);

        this.maxValue = maxValue;
        this.value = parent.getHealth();
        this.component = new TextComponent(String.valueOf(maxValue), Configs.loadCached(ProjectConfig.class).default_font);

        healthColors.put(10, Utils.rgb(255, 0, 0));
        healthColors.put(20, Utils.rgb(226, 26, 0));
        healthColors.put(30, Utils.rgb(198, 56, 0));
        healthColors.put(40, Utils.rgb(170, 85, 0));
        healthColors.put(50, Utils.rgb(141, 113, 0));
        healthColors.put(60, Utils.rgb(113, 141, 0));
        healthColors.put(70, Utils.rgb(85, 170, 0));
        healthColors.put(80, Utils.rgb(56, 198, 0));
        healthColors.put(90, Utils.rgb(28, 226, 0));
        healthColors.put(100, Utils.rgb(0, 255, 0));
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

        // Set value with delay

        if (value > parent.getHealth()) value -= VALUE_DELTA * Utils.delta();

        if (value < parent.getHealth()) value += VALUE_DELTA * Utils.delta();

        bd = new BigDecimal(Float.toString(value));
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);

        component.setText(String.valueOf(bd.toPlainString()));

        this.bounds = PolygonHelper.getPolygon(getX(), getY() - getHeight() - HEALTH_TEXT_VERTICAL_OFFSET, width, height);
    }

    @Override
    public void draw() {

        GameManager.setIsShapeRendering(true);
        renderer.setProjectionMatrix(GameManager.getWorldCamera().combined);
        renderer.set(ShapeRenderer.ShapeType.Filled);

        // Draw background

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        renderer.setColor(BACKGROUND_COLOR);

        Vector2 centerPos = CenteredDrawer.get(CenteredDrawer.CenteredDrawingType.CENTERED_AT_POINT, parent.getX() + parent.getTextureWidth() / 2, parent.getY() - getHeight() / 0.5f, getWidth(), getHeight());
        renderer.rect(centerPos.x, centerPos.y, getWidth(), getHeight());
        getPosition().set(centerPos.x ,centerPos.y);

        // Draw health

        Color drawColor = Color.WHITE;

        for (Integer integer : healthColors.keys()) {
            if (value <= integer) {
                drawColor = healthColors.get(integer);
                break;
            }
        }

        // Draw health

        renderer.set(ShapeRenderer.ShapeType.Filled);

        renderer.setColor(drawColor);

        renderer.rect(centerPos.x, centerPos.y, (value / maxValue) * width, height);

        GameManager.setIsShapeRendering(false);

        // Draw text

        centerPos.set(centerPos.x + getWidth() / 2, centerPos.y);

        GameManager.setIsRendering(true);

        component.drawCenteredInContainer(GameManager.getBatch(), getX(), getY() - getHeight() - HEALTH_TEXT_VERTICAL_OFFSET, getWidth(), getHeight());

    }

    @Override
    public void dispose() {

    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

}
