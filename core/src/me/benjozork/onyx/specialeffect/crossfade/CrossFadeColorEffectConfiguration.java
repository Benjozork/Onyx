package me.benjozork.onyx.specialeffect.crossfade;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;

/**
 * @author angelickite
 */
public class CrossFadeColorEffectConfiguration {

    /**
     * The colors which are cycled through in the added order. The cycle is circular,<br/>
     * meaning from the last color in the cycle a crossfade will happen to the first<br/>
     * color in the cycle, ad infinitum.
     */
    public final Array<Color> cycleColors = new Array<Color>();

    /**
     * How long a single crossfade between two colors should take. [in seconds]
     */
    public float crossFadeTime;

    /**
     * How much the red, green and blue components need to differ in total<br/>
     * from the current values of the source color to fade into the next color.<br/>
     * The delta range is 0(inc) to 255(inc), therefore valid ranges for this variable are<br/>
     * in the range of 0(inc) to 255*3(inc) = 0(inc) to 765(inc). A value of 765 would mean only<br/>
     * a transition from white(255,255,25) to black(0,0,0) or vice-versa is possible.
     * <p>
     * The above assumes fadeInDeltaMultiplier and fadeOutDeltaMultiplier values of 1. If those<br/>
     * values are not both 1 then the ranges of this variables have to be considered scaled<br/>
     * appropriately!
     */
    public float crossFadeDeltaTimeStepRequirement;

    /**
     * How much the delta between the source colors rgb and target colors rgb are scaled<br/>
     * for the crossFadeDeltaTimeStepRequirement during the fade in step of the cross fade.<br/>
     * A value of 1 means the deltas will be considered at their original scale of 0-255.
     */
    public float fadeInDeltaMultiplier = 1;

    /**
     * How much the delta between the source colors rgb and target colors rgb are scaled<br/>
     * for the crossFadeDeltaTimeStepRequirement during the fade out step of the cross fade.<br/>
     * A value of 1 means the deltas will be considered at their original scale of 0-255.
     */
    public float fadeOutDeltaMultiplier = 1;

}

