package me.benjozork.onyx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Version;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import me.benjozork.onyx.internal.GameUtils;
import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.screen.GameScreen;
import me.benjozork.onyx.screen.MenuScreen;

public class OnyxGame extends Game {

     OrthographicCamera cam;

     @Override
     public void create() {
          //Camera
          cam = new OrthographicCamera();
          cam.setToOrtho(false);
          cam.viewportWidth = Gdx.graphics.getWidth();
          cam.viewportHeight = Gdx.graphics.getHeight();

          Gdx.app.log("[onyx/info] ", "Onyx 0.0.1 starting");
          Gdx.app.log("[onyx/debug] ", "Current libGDX version is " + Version.VERSION);
          Gdx.app.log("[onyx/debug] ", "Current backend is " + Gdx.app.getType() + "/" + System.getProperty("os.name"));
          Gdx.app.log("[onyx/debug] ", "Current JRE version is " + System.getProperty("java.version"));
          //GameScreen screen = new GameScreen();
          MenuScreen screen = new MenuScreen();
          //screen.setCamera(cam);
          GameManager.setCamera(cam);
          GameManager.setCurrentScreen(screen);
          GameManager.setRenderer(new ShapeRenderer());
          GameManager.setBatch(new SpriteBatch());
          setScreen(GameManager.getCurrentScreen());

     }

     public void resize(int width, int height) {
          cam.viewportWidth = width;
          cam.viewportHeight = height;
     }

     @Override
     public void render() {
          //((GameScreen) GameManager.getCurrentScreen()).getBatch().setProjectionMatrix(cam.combined);
          // Camera
          cam.update();

          // Prepare OpenGL
          Gdx.gl.glClearColor(1f, 1f, 1f, 1);
          Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
          // Render frame
          getScreen().render(GameUtils.getDelta());
     }

     @Override
     public void dispose() {
          //batch.dispose();
          //shapeRenderer.dispose();
     }

     public OrthographicCamera getCam() {
          return cam;
     }

}
