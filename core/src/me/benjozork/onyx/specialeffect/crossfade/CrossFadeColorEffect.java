package me.benjozork.onyx.specialeffect.crossfade;

import com.badlogic.gdx.graphics.Color;

import me.benjozork.onyx.backend.collection.CycleList;
import me.benjozork.onyx.specialeffect.Effect;
import me.benjozork.onyx.utils.Utils;

/**
 * A CrossFadeColorEffect allows transforming a source color according to the provided<br/>
 * configuration. The provided {@link Color} instance 'source' will be manipulated when update() is called!
 *
 * @author angelickite
 */
public class CrossFadeColorEffect extends Effect {

    private final Color source;
    private final CycleList<Color> cycle;

    private float crossFadeTime;
    private float crossFadeDeltaTimeStepRequirement;
    private float fadeInDeltaMultiplier;
    private float fadeOutDeltaMultiplier;

    /**
     * @param configuration the configuration which drives the effect
     * @param source        the color to be manipulated
     */
    public CrossFadeColorEffect(CrossFadeColorEffectConfiguration configuration, Color source) {
        this.source = source;
        this.cycle = CycleList.of(configuration.cycleColors);
        this.crossFadeTime = configuration.crossFadeTime;
        this.crossFadeDeltaTimeStepRequirement = configuration.crossFadeDeltaTimeStepRequirement;
        this.fadeInDeltaMultiplier = configuration.fadeInDeltaMultiplier;
        this.fadeOutDeltaMultiplier = configuration.fadeOutDeltaMultiplier;
    }

    public void update() {
        if (! isActive) return;

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
