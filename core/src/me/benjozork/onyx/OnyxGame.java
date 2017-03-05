package me.benjozork.onyx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import me.benjozork.onyx.entity.EntityEnemy;
import me.benjozork.onyx.entity.EntityPlayer;
import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.internal.GameUtils;


public class OnyxGame extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	Texture img;

	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		img = new Texture("android/assets/badlogic.jpg");
		Gdx.app.log("[onyx/info] ", "Onyx 0.0.1 started");
		// Prepare GameManager for rendering
		GameManager.setBatch(batch);
		GameManager.setShapeRenderer(shapeRenderer);
		// Create box and configure it
		EntityPlayer player = new EntityPlayer(GameUtils.getCenterPos(50), 25);
		EntityEnemy enemy = new EntityEnemy(150, 150);
		// Push box to GameManager
		GameManager.registerEntity(player);
		GameManager.registerEntity(enemy);
		GameManager.initGame();
	}

	@Override
	public void render () {
		// Prepare OpenGL
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Render frame
		GameManager.tickGame();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
