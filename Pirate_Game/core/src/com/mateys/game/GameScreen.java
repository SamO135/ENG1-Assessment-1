package com.mateys.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import java.util.ArrayList;


public class GameScreen extends ScreenAdapter {
	SpriteBatch batch;
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
	private ArrayList<Entity> allEntities;





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



		// load map
		tiledMap = new TmxMapLoader().load("PirateMap.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

		//Initialise entities list
		allEntities = new ArrayList<Entity>();

		// initialise the player
		float centerX = (camera.viewportWidth / 2);
		float centerY = (camera.viewportHeight / 2);
		player = new PlayerShip(centerX, centerY);
		allEntities.add(player);
	}





	@Override
	public void render (float delta) {
		ScreenUtils.clear(0.67f, 0.91f, 1f, 1);

		camera.update();

		game.batch.setProjectionMatrix(camera.combined);


		// process user input
		player.movement.set(0, 0);
		if(Gdx.input.isKeyPressed(Input.Keys.A)){player.moveLeft();}
		if(Gdx.input.isKeyPressed(Input.Keys.D)){player.moveRight();}
		if(Gdx.input.isKeyPressed(Input.Keys.W)){player.moveUp();}
		if(Gdx.input.isKeyPressed(Input.Keys.S)){player.moveDown();}


		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
			Bullet newBullet = new Bullet(player.getX(), player.getY());
			allEntities.add(newBullet);
			newBullet.shootLeft();
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
			Bullet newBullet = new Bullet(player.getX(), player.getY());
			allEntities.add(newBullet);
			newBullet.shootRight();
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
			Bullet newBullet = new Bullet(player.getX(), player.getY());
			allEntities.add(newBullet);
			newBullet.shootUp();
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
			Bullet newBullet = new Bullet(player.getX(), player.getY());
			allEntities.add(newBullet);
			newBullet.shootDown();
		}




		// Update Camera Position
		camera.position.set(player.getX(), player.getY(), 0);

		// Update Timer
		if (time > period){
			time = 0f;
			score += 1;
		} else{
			time += Gdx.graphics.getDeltaTime();
		}


		// begin a new batch
		game.batch.begin();


		// Update and Render TileMap
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();



		//Update and Render all Entities
		for(Entity entity : allEntities){
			entity.update();
			entity.render(game.batch);
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
	}
}
