package me.benjozork.onyx.specialeffect.crossfade;

import com.badlogic.gdx.graphics.Color;

import me.benjozork.onyx.utils.Utils;
import me.benjozork.onyx.utils.collection.CycleList;

/**
 * A CrossFadeColorEffect allows transforming a source color according to the provided
 * configuration. The provided color instance 'source' will be manipulated when update() is called!
 * @author angelickite
 */
public class CrossFadeColorEffect {

    private final Color source;
    private final CycleList<Color> cycle;

    private boolean isActive;
    private float crossFadeTime;
    private float crossFadeDeltaTimeStepRequirement;
    private float fadeInDeltaMultiplier;
    private float fadeOutDeltaMultiplier;

    /**
     * @param source the color to be manipulated
     * @param configuration the configuration which drives the effect
     */
    public CrossFadeColorEffect(Color source, CrossFadeColorEffectConfiguration configuration) {
        this.source = source;
        this.cycle = CycleList.of(configuration.cycleColors);
        this.crossFadeTime = configuration.crossFadeTime;
        this.crossFadeDeltaTimeStepRequirement = configuration.crossFadeDeltaTimeStepRequirement;
        fadeInDeltaMultiplier = configuration.fadeInDeltaMultiplier;
        fadeOutDeltaMultiplier = configuration.fadeOutDeltaMultiplier;
    }

    public void resume() {
        isActive = true;
    }

    public void pause() {
        isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public void update() {
        if (!isActive) return;

        Color target = cycle.current();

        float dr = target.r - source.r;
        float dg = target.g - source.g;
        float db = target.b - source.b;

        float fadeStep = crossFadeTime / Utils.delta();

        source.r += (dr / fadeStep);
        source.g += (dg / fadeStep);
        source.b += (db / fadeStep);

        float totalFadeDelta = accumulateDelta(dr) + accumulateDelta(dg) + accumulateDelta(db);
        totalFadeDelta *= 255f;

        if (Math.abs(totalFadeDelta) < crossFadeDeltaTimeStepRequirement) {
            cycle.next();
        }
    }

    private float accumulateDelta(float delta) {
        if (delta < 0) return delta * fadeOutDeltaMultiplier;
        else return delta * fadeInDeltaMultiplier;
    }

}
