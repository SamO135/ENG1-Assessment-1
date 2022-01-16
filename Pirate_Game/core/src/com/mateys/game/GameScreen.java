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
	private Rectangle playerShip;
	private Texture playerShipImage;
	private int score;
	private float period = 1f;
	private float time = 0f;
	private BitmapFont scoreText;
	private FreeTypeFontGenerator fontGenerator;
	private FreeTypeFontGenerator.FreeTypeFontParameter  fontParameter;
	private Vector2 playerPosition;
	private Vector2 playerMovement;
	private float MOVESPEED;
	private Mateys game;


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

		// load the ship image
		playerShipImage = new Texture(Gdx.files.internal("PlayerShip.png"));


		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		playerShip = new Rectangle();
		//playerShip.width = 64;
		//playerShip.height = 64;

		// initialise the player position
		playerShip.x = (camera.viewportWidth / 2) - (playerShip.width / 2); // center the bucket horizontally
		playerShip.y = (camera.viewportHeight / 2) - (playerShip.height / 2); // bottom left corner of the bucket is 20 pixels above the bottom screen edge
		playerPosition = new Vector2(playerShip.x, playerShip.y);

		// initialise default movement speed
		MOVESPEED = 200;

		// initialise movement vector
		playerMovement = new Vector2(0,0);
	}

	@Override
	public void render (float delta) {
		ScreenUtils.clear(0f, 0.4f, 0.6f, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(playerShipImage, playerShip.x, playerShip.y);

		playerMovement.set(0, 0);
		// input for player movement
		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			playerMovement.add(-1, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)){
			playerMovement.add(1, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)){
			playerMovement.add(0, 1);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)){
			playerMovement.add(0, -1);

		}
		//update the player's position
		playerShip.setPosition(playerPosition.mulAdd(((playerMovement.nor()).scl(MOVESPEED)), Gdx.graphics.getDeltaTime())); // set ship position at 'position' + (normalised 'movement' * 'MOVESPEED') * DeltaTime

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
		playerShipImage.dispose();
		batch.dispose();
		img.dispose();
	}
}
