package com.mateys.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class GameScreen extends ScreenAdapter {
	SpriteBatch batch;
	Texture img;
	private OrthographicCamera camera;
	private int score;
	private float period = 1f;
	private float time = 0f;
	private BitmapFont scoreText;
	private FreeTypeFontGenerator fontGenerator;
	private FreeTypeFontGenerator.FreeTypeFontParameter  fontParameter;
	private Mateys game;
	private PlayerShip player;
	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;




	public GameScreen (Mateys game) {
		this.game = game;
	}

	@Override
	public void show() {

		//create the score text font
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("BlackSamsGold.ttf"));
		fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		fontParameter.size = 100;
		fontParameter.borderWidth = 2f;
		fontParameter.borderColor = Color.BLACK;
		fontParameter.color = Color.WHITE;
		scoreText = fontGenerator.generateFont(fontParameter);


		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1500, 1500);
		// camera.setToOrtho(false, 800, 480);


		// initialise the player
		float centerX = (camera.viewportWidth / 2);
		float centerY = (camera.viewportHeight / 2);
		player = new PlayerShip(centerX, centerY);


		// load map
		tiledMap = new TmxMapLoader().load("PirateMap.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
	}

	@Override
	public void render (float delta) {

		ScreenUtils.clear(0f, 0.4f, 0.6f, 1);

		camera.update();

		game.batch.setProjectionMatrix(camera.combined);


		// begin a new batch
		game.batch.begin();

		// process user input
		player.movement.set(0, 0);
		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			player.movement.add(-1, 0);
			player.rotation = 270;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)){
			player.movement.add(1, 0);
			player.rotation = 90;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)){
			player.movement.add(0, 1);
			player.rotation = 180;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)){
			player.movement.add(0, -1);
			player.rotation = 0;
		}

		// Update and Render Player Ship
		player.update();
		player.render(game.batch);

		// Update and Render TileMap
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();

		// Update Camera Position
		camera.position.set(player.getX(), player.getY(), 0);

		// Update Timer
		if (time > period){
			time = 0f;
			score += 1;
		} else{
			time += Gdx.graphics.getDeltaTime();
		}

		// Draw Score to Screen
		scoreText.draw(game.batch, "Score: " + score, camera.position.x - 700f, camera.position.y + 700f);

		// End Batch
		game.batch.end();
	}
	
	@Override
	public void dispose () {
		player.dispose();
		batch.dispose();
		img.dispose();
	}
}
