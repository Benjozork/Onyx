package me.benjozork.onyx;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import me.benjozork.onyx.entity.EntityEnemy;
import me.benjozork.onyx.entity.EntityPlayer;

public class OnyxGame extends ApplicationAdapter {

     SpriteBatch batch;
     ShapeRenderer shapeRenderer;

     OrthographicCamera cam;

     @Override
     public void create() {
          //Camera
          cam = new OrthographicCamera();
          cam.setToOrtho(false);
          cam.viewportWidth = Gdx.graphics.getWidth();
          cam.viewportHeight = Gdx.graphics.getHeight();

          batch = new SpriteBatch();
          shapeRenderer = new ShapeRenderer();
          Gdx.app.log("[onyx/info] ", "Onyx 0.0.1 starting");
          Gdx.app.log("[onyx/debug] ", "Current libGDX version is " + Version.VERSION);
          Gdx.app.log("[onyx/debug] ", "Current backend is " + Gdx.app.getType() + "/" + System.getProperty("os.name"));
          Gdx.app.log("[onyx/debug] ", "Current JRE version is " + System.getProperty("java.version"));
          GameManager.setBatch(batch);
          GameManager.setShapeRenderer(shapeRenderer);
          GameManager.setCamera(cam);
          // Create box and configure it
          EntityPlayer player = new EntityPlayer(0, 0);
          // Push box to GameManager
          GameManager.registerEntity(player);
          GameManager.initGame();
     }

     @Override
     public void render() {
          batch.setProjectionMatrix(cam.combined);
          shapeRenderer.setProjectionMatrix(cam.combined);
          // Camera
          cam.update();

          // Prepare OpenGL
          Gdx.gl.glClearColor(1f, 1f, 1f, 1);
          Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
          // Render frame
          GameManager.tickGame();
     }

     @Override
     public void dispose() {
          batch.dispose();
          shapeRenderer.dispose();
     }

     public void resize(int width, int height) {
          GameManager.getCamera().viewportWidth = width;
          GameManager.getCamera().viewportHeight = height;
     }

     public OrthographicCamera getCam() {
          return cam;
     }

}
