package me.benjozork.onyx.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ArrayMap;
import me.benjozork.onyx.backend.models.Drawable;

/**
 * This class allows to create predefined movement sequences of {@link Drawable} objects.
 */
public class DrawableMover {

    /**
     * Creates a {@link Movement} for a {}
     *
     * @param drawable the {@link Drawable} to move
     * @param targetPosition the target position to move the {@link Drawable} to
     * @param movementType the type of movement to use ({@link MovementType})
     * @param targetTime the target time for the movement to take, in milliseconds
     * @param precision the number of steps to generate
     *
     * @return a {@link Movement} for the provided parameters
     */
    public static Movement createMovementTo(Drawable drawable, Vector2 targetPosition, MovementType movementType, float targetTime, int precision) {

        Movement ret = new Movement();

        switch (movementType) {

            case LINEAR: {

                ArrayMap<Float, Vector2> steps = new ArrayMap<Float, Vector2>();

                float xDist = drawable.getX() - targetPosition.x;
                float yDist = drawable.getY() - targetPosition.y;

                float timeStep = targetTime / precision;
                float xDisplacementStep = xDist / precision;
                float yDisplacementStep = yDist / precision;

                for (int i = 0; i < precision; i++) {
                    steps.put(timeStep * i, new Vector2(xDisplacementStep, yDisplacementStep));
                }

            }

            case EXPONENTIAL: {

            }

        }

        return ret;

    }

    public enum MovementType {
        LINEAR,
        EXPONENTIAL,
    }

}