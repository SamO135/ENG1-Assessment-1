package com.mateys.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import java.util.ArrayList;


import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {
	SpriteBatch batch;
	private OrthographicCamera camera;
	private int score;
	private float period = 1f;
	private float time = 0f;
	private BitmapFont scoreText;
	private BitmapFont goldText;
	private int gold;
	private FreeTypeFontGenerator fontGenerator;
	private FreeTypeFontGenerator.FreeTypeFontParameter  fontParameter;
	private Mateys game;
	private PlayerShip player;
	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;
	private World world;
	private Box2DDebugRenderer b2dr;
	private ArrayList<Rectangle> rects = new ArrayList<Rectangle>();
	private ArrayList<Entity> allEntities;





	public GameScreen (Mateys game) {
		this.game = game;
	}

	@Override
	public void show() {

		this.world = world;

		//create the score text font
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("BlackSamsGold.ttf"));
		fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		fontParameter.size = 100;
		fontParameter.borderWidth = 2f;
		fontParameter.borderColor = Color.BLACK;
		fontParameter.color = Color.WHITE;
		scoreText = fontGenerator.generateFont(fontParameter);
		fontParameter.color = Color.GOLD;
		goldText = fontGenerator.generateFont(fontParameter);


		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1500, 1000);
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




		// load map
		tiledMap = new TmxMapLoader().load("PirateMap.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);


		world = new World(new Vector2(0, 0), true);
		b2dr = new Box2DDebugRenderer();


		for (MapObject object: tiledMap.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			rects.add(rect);

		}


	}



	@Override
	public void render (float delta) {
		ScreenUtils.clear(0.67f, 0.91f, 1f, 1);

		camera.update();

		game.batch.setProjectionMatrix(camera.combined);


		// begin a new batch
		game.batch.begin();


		// process user input
		player.movement.set(0, 0);

		Boolean canMove = true;

		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			player.rotation = 270;

			float newXPos = player.getX() - 32;
			Rectangle testRect = new Rectangle(newXPos, player.getY(), 64, 64);

			for (Rectangle rect : rects) {
				if (testRect.overlaps(rect)) {
					canMove = false;
				}
			}
			if (canMove) {
				player.movement.add(-1, 0);
			}

		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)){
			player.rotation = 90;

			float newXPos = player.getX() + 16;
			Rectangle testRect = new Rectangle(newXPos, player.getY(), 64, 64);

			for (Rectangle rect : rects) {
				if (testRect.overlaps(rect)) {
					canMove = false;
				}
			}
			if (canMove) {
				player.movement.add(1, 0);
			}

		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)){
			player.rotation = 180;

			float newYPos = player.getY() + 32;
			Rectangle testRect = new Rectangle(player.getX(), newYPos, 64, 64);

			for (Rectangle rect : rects) {
				if (testRect.overlaps(rect)) {
					canMove = false;
				}
			}
			if (canMove) {
				player.movement.add(0, 1);
			}

		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)){
			player.rotation = 0;

			float newYPos = player.getY() - 32;
			Rectangle testRect = new Rectangle(player.getX(), newYPos, 64, 64);

			for (Rectangle rect : rects) {
				if (testRect.overlaps(rect)) {
					canMove = false;
				}
			}
			if (canMove) {
				player.movement.add(0, -1);
			}


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
		scoreText.draw(game.batch, "Score: " + score, camera.position.x - 700, camera.position.y + 450);
		goldText.draw(game.batch, "Gold " + gold, camera.position.x - 250, camera.position.y + 450);

		b2dr.render(world, camera.combined);

		// End Batch
		game.batch.end();
	}
	
	@Override
	public void dispose () {
		player.dispose();
		batch.dispose();
	}
}
