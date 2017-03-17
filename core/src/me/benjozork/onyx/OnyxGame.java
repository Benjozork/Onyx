package me.benjozork.onyx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import me.benjozork.onyx.entity.EntityEnemy;
import me.benjozork.onyx.entity.EntityPlayer;
import me.benjozork.onyx.internal.GameUtils;


public class OnyxGame extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;

	OrthographicCamera cam;

	@Override
	public void create () {
		//Camera
		cam = new OrthographicCamera();
		cam.setToOrtho(false);
		cam.viewportWidth = Gdx.graphics.getWidth();
		cam.viewportHeight = Gdx.graphics.getHeight();

		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		Gdx.app.log("[onyx/info] ", "Onyx 0.0.1 started");
		// Prepare GameManager for rendering
		GameManager.setBatch(batch);
		GameManager.setShapeRenderer(shapeRenderer);
		GameManager.setCamera(cam);
		// Create box and configure it
		EntityPlayer player = new EntityPlayer(0, 0);
		EntityEnemy enemy = new EntityEnemy(0, 0);
		// Push box to GameManager
		GameManager.registerEntity(player);
		GameManager.registerEntity(enemy);
		GameManager.initGame();
	}

	@Override
	public void render () {
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
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
	}

	public OrthographicCamera getCam() {
		return cam;
	}

}
