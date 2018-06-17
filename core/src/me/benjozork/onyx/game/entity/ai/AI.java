package me.benjozork.onyx.game.entity.ai;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import me.benjozork.onyx.game.entity.LivingEntity;
import me.benjozork.onyx.utils.Utils;

public class AI {

    private LivingEntity controlledEntity;

    private LivingEntity targetedEntity;

    private final float ANGLE_TRACKING_DELTA = 60f;

    private final float MIN_DISTANCE_TO_PLAYER =  300;

    private final float MAX_DISTANCE_TO_PLAYER = 300;

    private final float MIN_PLAYER_TRAVEL_DISTANCE_UPDATE_THRESHOLD = 100f;

    private final int MIN_ANGLE_TO_PLAYER = 25;

    private final int MAX_ANGLE_TO_PLAYER = 155;

    private final float SPEED = 150f;

    private Vector2 previousTargetedPosition = new Vector2(0, 0);

    private boolean targetPositionUpdateLock = false;

    private float xSpeedToTarget;

    private float ySpeedToTarget;

    private Vector2 targetPosition;

    private float targetDistanceToTargeted;

    private Vector2 norVectorToTargeted;

    public AI(LivingEntity controlledEntity, LivingEntity targetedEntity) {

        this.controlledEntity = controlledEntity;
        this.targetedEntity = targetedEntity;

        this.updateTargetPosition();

    }

    public void update() {

        // Angle tracking

        float angle;

            // Figure out angle

        angle = MathUtils.radiansToDegrees * MathUtils.atan2(controlledEntity.getX() - targetedEntity.getX(), controlledEntity.getY() - targetedEntity.getY());
        angle = -angle;

        float mazZeroFrameError = ANGLE_TRACKING_DELTA * Utils.delta();

        if (
            this.controlledEntity.getRotation() < angle + mazZeroFrameError
            && this.controlledEntity.getRotation() > angle - mazZeroFrameError
        )
            this.controlledEntity.setRotation(angle);
        else {

            if (this.controlledEntity.getRotation() < angle) {

                this.controlledEntity.setRotation(this.controlledEntity.getRotation() + ANGLE_TRACKING_DELTA * Utils.delta());

            } else if (this.controlledEntity.getRotation() > angle) {

                this.controlledEntity.setRotation(this.controlledEntity.getRotation() - ANGLE_TRACKING_DELTA * Utils.delta());

            }

        }

        if (!this.previousTargetedPosition.equals(this.targetedEntity.getPosition())) {
            this.updateTargetPosition();
        }

        previousTargetedPosition.set(this.targetedEntity.getPosition().cpy());

        float theta = MathUtils.atan2(this.getControlledEntity().getY() - this.targetPosition.y, this.getControlledEntity().getX() - this.targetPosition.x);

        this.xSpeedToTarget = Math.abs(SPEED * MathUtils.cos(theta));
        this.ySpeedToTarget = Math.abs(SPEED * MathUtils.sin(theta));

        System.out.println("xSpeed: " + xSpeedToTarget + " xCurrent: " + this.getControlledEntity().getX() + " xTarget: " + targetPosition.x);
        System.out.println("ySpeed: " + ySpeedToTarget + " yCurrent: " + this.getControlledEntity().getY() + " yTarget: " + targetPosition.y);

        //fixme completely linear movement

        if (this.controlledEntity.getX() < this.targetPosition.x) this.controlledEntity.getPosition().x += xSpeedToTarget * Utils.delta();
        if (this.controlledEntity.getX() > this.targetPosition.x) this.controlledEntity.getPosition().x -= xSpeedToTarget * Utils.delta();

        if (this.controlledEntity.getY() < this.targetPosition.y) this.controlledEntity.getPosition().y += ySpeedToTarget * Utils.delta();
        if (this.controlledEntity.getY() > this.targetPosition.y) this.controlledEntity.getPosition().y -= ySpeedToTarget * Utils.delta();

    }

    private void updateTargetPosition() {

        // for now, set a random angle

        float angleToPlayerDeg = Utils.randomBetween(MIN_ANGLE_TO_PLAYER, MAX_ANGLE_TO_PLAYER);

        System.out.println("vec supposed angle: " + angleToPlayerDeg);

        // set a random target distance

        this.targetDistanceToTargeted = Utils.randomBetween(MIN_DISTANCE_TO_PLAYER, MAX_DISTANCE_TO_PLAYER);

        // figure out where we need to go using target distance and angle

        Vector2 targetPositionVectorFromPlayer =
                new Vector2(1, 1)
                        .setLength(targetDistanceToTargeted)
                        .setAngle(angleToPlayerDeg);


        System.out.println("vec actual angle: " + MathUtils.radiansToDegrees * Math.atan2(targetPositionVectorFromPlayer.y, targetPositionVectorFromPlayer.x));

        this.targetPosition = this.getTargetedEntity().getPosition().cpy().add(targetPositionVectorFromPlayer);

    }

    public LivingEntity getControlledEntity() {
        return controlledEntity;
    }

    public void setControlledEntity(LivingEntity controlledEntity) {
        this.controlledEntity = controlledEntity;
    }

    public LivingEntity getTargetedEntity() {
        return targetedEntity;
    }

    public void setTargetedEntity(LivingEntity targetedEntity) {
        this.targetedEntity = targetedEntity;
    }

}