package com.mateys.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import java.util.ArrayList;
import java.util.Iterator;


public class GameScreen extends ScreenAdapter {
	private OrthographicCamera camera;
	private int score;
	/** The rate of passive score increase in seconds */
	private float period = 1f;
	/** The variable used to check if there has been a long enough delay since the last passive score increment */
	private float timeElapsed = 0f;
	/** The font, size, colour etc. for the text displaying the score */
	private BitmapFont scoreText;
	/** The font, size, colour etc. for the text displaying the gold */
	private BitmapFont goldText;
	private int gold;
	private Mateys game;
	private PlayerShip player;
	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;
	private World world;
	private Box2DDebugRenderer b2dr;
	/** A list of all the hitboxes of the islands and map boundary */
	private ArrayList<Rectangle> rects = new ArrayList<Rectangle>();

	private ArrayList<Bullet> allBullets;
	private ArrayList<Island> allIslands = new ArrayList<Island>();

	/** A list of all entities present in the game */
	private ArrayList<Entity> allEntities = new ArrayList<Entity>();






	public GameScreen (Mateys game) {
		this.game = game;
	}

	/** A method to initialise variable and objects */
	@Override
	public void show() {

		this.world = world;

		// create fonts
		scoreText = createTextFont("BlackSamsGold.ttf", 100, Color.WHITE, 2f, Color.BLACK);
		goldText = createTextFont("BlackSamsGold.ttf", 100, Color.GOLD, 2f, Color.BLACK);


		// initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1500, 1000); //set camera's view distance


		// load map
		tiledMap = new TmxMapLoader().load("PirateMap.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		world = new World(new Vector2(0, 0), true);
		b2dr = new Box2DDebugRenderer();


		for (MapObject object: tiledMap.getLayers().get("LandObject").getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			rects.add(rect);
		}

		//initialize entities list
		allEntities = new ArrayList<Entity>();

		//initalize bullets list
		allBullets = new ArrayList<Bullet>();

		//Initialize Islands
		allIslands.add(new Island("JamesCollege", tiledMap, 1000));
		allIslands.add(new Island("LangwithCollege", tiledMap, 500));
		allIslands.add(new Island("VanbrughCollege", tiledMap, 500));


		// initialize the player
		float centerX = (camera.viewportWidth / 2);
		float centerY = (camera.viewportHeight / 2);
		player = new PlayerShip(centerX, centerY);

		allEntities.add(player);
	}



	/**
	 * The main method where the game logic is written
	 * @param delta The time in seconds since the last render
	 */
	@Override
	public void render (float delta) {
		ScreenUtils.clear(0.67f, 0.91f, 1f, 1);
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);


		// process user input -- movement
		player.movement.set(0, 0);
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			player.rotation = 270;
			player.moveLeft();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			player.rotation = 90;
			player.moveRight();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			player.rotation = 180;
			player.moveUp();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			player.rotation = 0;
			player.moveDown();
		}

		// process user input -- shooting
		if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			Bullet newBullet = new Bullet(player.getX(), player.getY());
			allEntities.add(newBullet);
			allBullets.add(newBullet);
			newBullet.shootLeft();
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			Bullet newBullet = new Bullet(player.getX(), player.getY());
			allEntities.add(newBullet);
			allBullets.add(newBullet);
			newBullet.shootRight();
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			Bullet newBullet = new Bullet(player.getX(), player.getY());
			allEntities.add(newBullet);
			allBullets.add(newBullet);
			newBullet.shootUp();
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			Bullet newBullet = new Bullet(player.getX(), player.getY());
			allEntities.add(newBullet);
			allBullets.add(newBullet);
			newBullet.shootDown();
		}

		// Check for bullet collision
		for (Island island : allIslands) {
			for (Rectangle rect : island.getHitboxes()) {
				Iterator<Bullet> i = allBullets.iterator();
				while (i.hasNext()) {
					Bullet bullet = i.next();
					if (bullet.getBulletRect().overlaps(rect)) {
						island.takeDamage();

						if (island.getState() == 1) {
							gold += 50;
							score += 100;
						}
						bullet.dispose();
						i.remove();
						allBullets.remove(bullet);
					}


					if (bullet.isDead()) {
						bullet.dispose();
						i.remove();
						allBullets.remove(bullet);
					}
				}
			}
		}


		// update camera position
		camera.position.set(player.getX(), player.getY(), 0);

		// update timer - used for adding score over time
		if (timeElapsed > period) {
			timeElapsed = 0f;
			score += 1;
		} else {
			timeElapsed += delta;
		}


		// update and render tileMap
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();


		// begin a new batch
		game.batch.begin();

		// update and render all entities
		for (Entity entity : allEntities) {
			entity.update();
			entity.render(game.batch);
		}

		//Check if the player has collided with an island or map boundary
		//This has to be checked after the player has been updated
		for (Rectangle rect : rects) {
			if (player.getRect().overlaps(rect)) {
				player.position.mulAdd(player.movement.nor(), -(PlayerShip.MOVE_SPEED) * delta); //If the player rect/hitbox has overlapped a boundary, move back the same distance that it moved forward i.e. move back to where it was before it overlapped.
			}
		}

		//Write text to Screen
		scoreText.draw(game.batch, "Score: " + score, camera.position.x - 700, camera.position.y + 450);
		goldText.draw(game.batch, "Gold " + gold, camera.position.x - 250, camera.position.y + 450);

		b2dr.render(world, camera.combined);

		// End Batch
		game.batch.end();
	}


	/** Creates a font based on the parameters provided and returns it, you can then use the .draw method to display text with this font.
	 * @param fontPath The path to a .ttf file
	 * @param fontSize The size of the font
	 * @param fontColour The colour of the font
	 * @param borderWidth The width of the border around the text
	 * @param borderColour The colour of the border
	 */
	public BitmapFont createTextFont(String fontPath, int fontSize, Color fontColour, float borderWidth, Color borderColour){
		FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
		FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		fontParameter.size = fontSize;
		fontParameter.color = fontColour;
		fontParameter.borderWidth = borderWidth;
		fontParameter.borderColor = borderColour;
		BitmapFont bitmapFont = fontGenerator.generateFont(fontParameter);
		return bitmapFont;
	}

	@Override
	public void dispose() {
		player.dispose();
		tiledMap.dispose();
	}
}
