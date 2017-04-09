package me.benjozork.onyx.specialeffect.zoompulse;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;

import me.benjozork.onyx.specialeffect.Effect;
import me.benjozork.onyx.utils.Utils;

/**
 * A ZoomPulseEffect allows creating a short zoom pulse effect according to the provided<br/>
 * configuration. The provided {@link OrthographicCamera} instances 'cameras' will be manipulated when update() is called!
 *
 * @author Benjozork
 */
public class ZoomPulseEffect extends Effect {

    private final Array<OrthographicCamera> cameras;

    private float maxZoomTime;
    private float targetZoom;

    private boolean zoomBack;

    /**
     * @param configuration the {@link ZoomPulseEffectConfiguration} which drives the effect
     * @param cameras       the cameras to be manipulated
     */
    public ZoomPulseEffect(ZoomPulseEffectConfiguration configuration, OrthographicCamera... cameras) {
        if (cameras.length == 0) throw new IllegalArgumentException("at least one camera is required");
        this.cameras = Array.with(cameras);
        this.maxZoomTime = configuration.maxZoomTime / 6;
        this.targetZoom = configuration.targetZoom;
    }

    @Override
    public void update() {
        if (! isActive) return;

        float deltaZoom;

        if (zoomBack) {
            deltaZoom = - (targetZoom - cameras.get(0).zoom);
        } else {
            deltaZoom = targetZoom - cameras.get(0).zoom;
        }

        float zoomStep = maxZoomTime / Utils.delta();

        for (OrthographicCamera cam : cameras) {
            cam.zoom += (deltaZoom / zoomStep);
        }

        if (deltaZoom > - 0.05f) {
            zoomBack = true;
        }

        for (OrthographicCamera cam : cameras) {
            if (cam.zoom > 1f) {
                for (OrthographicCamera cam2 : cameras) {
                    cam2.zoom = 1f;
                }
                zoomBack = false;
                isActive = false;

                break;
            }
        }
    }

}
