package com.mateys.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	private OrthographicCamera camera;
	private Rectangle playerShip;
	private Texture playerShipImage;


	
	@Override
	public void create () {
		playerShipImage = new Texture(Gdx.files.internal("PlayerShip.png"));


		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");
		playerShip = new Rectangle();
		playerShip.width = 64;
		playerShip.height = 64;

		playerShip.x = (camera.viewportWidth / 2) - (playerShip.width / 2); // center the bucket horizontally
		playerShip.y = (camera.viewportHeight / 2) - (playerShip.height / 2); // bottom left corner of the bucket is 20 pixels above the bottom screen edge

	}

	@Override
	public void render () {
		ScreenUtils.clear(0f, 0.4f, 0.6f, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(playerShipImage, playerShip.x, playerShip.y);

		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			playerShip.x -= 200 * Gdx.graphics.getDeltaTime();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)){
			playerShip.x += 200 * Gdx.graphics.getDeltaTime();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)){
			playerShip.y += 200 * Gdx.graphics.getDeltaTime();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)){
			playerShip.y -= 200 * Gdx.graphics.getDeltaTime();
		}

		batch.end();
	}
	
	@Override
	public void dispose () {
		playerShipImage.dispose();
		batch.dispose();
		img.dispose();
	}
}
