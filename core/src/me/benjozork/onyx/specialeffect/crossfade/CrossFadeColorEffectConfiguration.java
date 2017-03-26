package me.benjozork.onyx.specialeffect.crossfade;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;

public class CrossFadeColorEffectConfiguration {

    /**
     * The colors which are cycled through in the added order. The cycle is circular,
     * meaning from the last color in the cycle a crossfade will happen to the first
     * color in the cycle, ad infinitum.
     */
    public final Array<Color> cycleColors = new Array<Color>();

    /**
     * How long a single crossfade between two colors should take. [in seconds]
     */
    public float maxFadeTime;

    /**
     * How much the red, green and blue components need to differ in total
     * from the current values of the source color to fade into the next color.
     * The delta range is 0(inc) to 255(inc), therefore valid ranges for this variable are
     * in the range of 0(inc) to 255*3(inc) = 0(inc) to 765(inc). A value of 765 would mean only
     * a transition from white(255,255,25) to black(0,0,0) or vice-versa is possible.
     */
    public float totalFadeDeltaStepRequirement;

}

