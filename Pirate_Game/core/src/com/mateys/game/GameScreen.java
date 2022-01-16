package com.mateys.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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


	public GameScreen (Mateys game) {

		this.game = game;

		//create the score text font
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("BlackSamsGold.ttf"));
		fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		fontParameter.size = 30;
		fontParameter.borderWidth = 2f;
		fontParameter.borderColor = Color.BLACK;
		fontParameter.color = Color.WHITE;
		scoreText = fontGenerator.generateFont(fontParameter);


		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		//playerShip.width = 64;
		//playerShip.height = 64;

		// initialise the player
		float centerX = (camera.viewportWidth / 2);
		float centerY = (camera.viewportHeight / 2);
		player = new PlayerShip(centerX, centerY);

	}

	@Override
	public void render (float delta) {
		ScreenUtils.clear(0f, 0.4f, 0.6f, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(player.playerShipImage, player.getX(), player.getY());

		player.playerMovement.set(0, 0);
		// input for player movement
		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			player.playerMovement.add(-1, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)){
			player.playerMovement.add(1, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)){
			player.playerMovement.add(0, 1);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)){
			player.playerMovement.add(0, -1);

		}

		player.render();


		//timer for score over time
		if (time > period){
			time = 0f;
			score += 1;
		} else{
			time += Gdx.graphics.getDeltaTime();
		}

		// drawing the score to the screen
		scoreText.draw(batch, "Score: " + score, 20f, camera.viewportHeight - 20f);



		batch.end();
	}
	
	@Override
	public void dispose () {
		player.dispose();
		batch.dispose();
		img.dispose();
	}
}
