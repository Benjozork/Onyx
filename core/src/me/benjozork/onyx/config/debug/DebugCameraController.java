package me.benjozork.onyx.config.debug;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

//By Jay

public class DebugCameraController {
    private static final Logger log = new Logger(DebugCameraController.class.getName(), Logger.DEBUG);


    private Vector2 position = new Vector2();
    private Vector2 startPosition = new Vector2();
    private float zoom = 1.0f;
    private DebugCameraConfig config;


    public DebugCameraController() {
        config = new DebugCameraConfig();
        log.debug("cameraConfig = " +config);
    }

    public void setStartPosition(float x, float y){
        position.set(x, y);
        startPosition.set(x, y);
    }

    public void applyTo(OrthographicCamera camera){
        camera.position.set(position, 0); //the first position is from Camera class part of Vector3
        camera.zoom = zoom;              // the first zoom is from OrthographicCamera class
        camera.update();
    }

    public void handleInputDebug(float delta){
        //if it's not on Desktop then don't handle inputs
        if(Gdx.app.getType() != Application.ApplicationType.Desktop){
            return;
        }

        float moveSpeed = config.getMoveSpeed() * delta;
        float zoomSpeed = config.getZoomSpeed() * delta;

        //move controls
        if(config.isLeftPressed()){
           moveLeft(moveSpeed);
        } else if(config.isRightPressed()){
            moveRight(moveSpeed);
        } else if (config.isUpPressed()){
            moveUp(moveSpeed);
        } else if(config.isDownPressed()){
            moveDown(moveSpeed);
        }

        //zoom controls
        if(config.isZoomInPressed()){
            zoomIn(zoomSpeed);
        } else if(config.isZoomOutPressed()){
            zoomOut(zoomSpeed);
        }

        //reset control
        if(config.isResetPressed()){
            reset();
        }

        //log control
        if(config.isLogPressed()){
            toggleLogDebug();
        }

    }

    //private methods
    private void setPosition(float x, float y){
        position.set(x, y);
    }

    private void setZoom(float value){
        //check if value is within the range of zoom in and zoom out
        zoom = MathUtils.clamp(value, config.getMaxZoomIn(),config.getMaxZoomOut());
    }

    private void moveCamera(float xSpeed, float ySpeed){
        setPosition(position.x + xSpeed, position.y + ySpeed);
    }

    //Move methods
    private void moveLeft(float speed){
        moveCamera(-speed, 0);
    }
    private void moveRight(float speed){
        moveCamera(speed, 0);
    }
    private void moveUp(float speed){
        moveCamera(0, speed);
    }
    private void moveDown(float speed){
        moveCamera(0, -speed);
    }

    //Zoom methods
    private void zoomIn(float zoomSpeed){
        setZoom(zoom + zoomSpeed);
    }
    private void zoomOut(float zoomSpeed){
        setZoom(zoom - zoomSpeed);
    }
    //Reset method
    private void reset(){
        position.set(startPosition);
        setZoom(1.0f);
    }
    //Toggle Log Debug method
    private void toggleLogDebug(){
        log.debug("position= " +position + "zoom= " +zoom);
    }
}
