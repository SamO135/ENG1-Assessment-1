package com.mateys.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import java.util.ArrayList;
import java.util.Iterator;



public class GameScreen extends ScreenAdapter {
	private final Mateys game;
	private int score;
	private int gold;
	private float timeElapsed; //Used for the timer in the 'timer' method.
	private PlayerShip player;

	private BitmapFont scoreText;
	private BitmapFont goldText;
	private BitmapFont islandText;
	private BitmapFont healthText;
	private BitmapFont hudText;
	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;
	/** A list of all the hitboxes of the islands and map boundary */
	private ArrayList<Rectangle> rects = new ArrayList<Rectangle>();

	private ArrayList<EnemyBullet> enemyBullets;
	private BitmapFont completedTaskText;

	private ArrayList<Bullet> playerBullets = new ArrayList<Bullet>();
	private ArrayList<Island> allIslands = new ArrayList<Island>();
	private ArrayList<Barrel> allBarrels = new ArrayList<Barrel>();
	private ArrayList<Entity> allEntities = new ArrayList<Entity>();

	private int barrelsCollected = 0;
	private boolean isBarrelsTaskComplete;
	private boolean isCoordTaskComplete;
	private boolean isFinalTaskComplete;

	private OrthographicCamera camera;



	public GameScreen (Mateys game) {
		this.game = game;
	}

	/** A method to initialise variable and objects */
	@Override
	public void show() {


		// create fonts
		healthText = createTextFont("fonts/BlackSamsGold.ttf", 100, Color.RED, 2f, Color.BLACK);
		scoreText = createTextFont("fonts/TreasureMapDeadhand-yLA3.ttf", 100, Color.WHITE, 2f, Color.BLACK);
		goldText = createTextFont("fonts/TreasureMapDeadhand-yLA3.ttf", 100, Color.GOLD, 2f, Color.BLACK);
		islandText = createTextFont("fonts/CELTICHD.ttf", 60, Color.WHITE, 1f, Color.BLACK);
		hudText = createTextFont("fonts/TreasureMapDeadhand-yLA3.ttf", 60, Color.WHITE, 2f, Color.BLACK);
		completedTaskText = createTextFont("fonts/TreasureMapDeadhand-yLA3.ttf", 60, Color.GREEN, 2f, Color.BLACK);

		// initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1500, 1000); //set camera's view distance
		camera.zoom = 1.3f;


		// load map
		tiledMap = new TmxMapLoader().load("PirateMap.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);


		for (MapObject object: tiledMap.getLayers().get("LandObject").getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			rects.add(rect);
		}

		//initialize entities list
		allEntities = new ArrayList<Entity>();

		//initalize bullets list
		playerBullets = new ArrayList<Bullet>();
		enemyBullets = new ArrayList<EnemyBullet>();


		//Initialize Islands
		allIslands.add(new Island("JamesCollege", tiledMap, 1000, new Vector2(3800, 5500))); //Island is roughly at position (3800, 5500)
		allIslands.add(new Island("LangwithCollege", tiledMap, 500, new Vector2(6000, 3200))); //Island is roughly at position (6000, 3200)
		allIslands.add(new Island("VanbrughCollege", tiledMap, 500, new Vector2(6400, 6600))); //Island is roughly at position (6400, 6600)


		//Spawn Barrels
		allBarrels.add(new Barrel(6500, 3800, 0));
		allBarrels.add(new Barrel(4800, 4200, 90));
		allBarrels.add(new Barrel(3100, 6500, 0));
		allBarrels.add(new Barrel(6050, 7150, 90));
		for (Barrel barrel : allBarrels){
			allEntities.add(barrel);
		}


		// initialize the player
		player = new PlayerShip(2500, 2500);
		allEntities.add(player);
	}



	/**
	 * The main method where the game logic is written
	 * @param delta The time in seconds since the last render
	 */
	@Override
	public void render (float delta) {
		ScreenUtils.clear(0.67f, 0.91f, 1f, 1);

		// update camera position
		camera.position.set(player.getX(), player.getY(), 0);
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);


		// process user input -- movement
		player.movement.set(0, 0);
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			player.rotation = 270;    // rotation is used to orient the ship depending on the direction of travel i.e. face left if moving left
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
			Bullet newBullet = new Bullet(player.getX(), player.getY(), 1);
			allEntities.add(newBullet);
			playerBullets.add(newBullet);
			newBullet.shootLeft();
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			Bullet newBullet = new Bullet(player.getX(), player.getY(), 1);
			allEntities.add(newBullet);
			playerBullets.add(newBullet);
			newBullet.shootRight();
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			Bullet newBullet = new Bullet(player.getX(), player.getY(), 1);
			allEntities.add(newBullet);
			playerBullets.add(newBullet);
			newBullet.shootUp();
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			Bullet newBullet = new Bullet(player.getX(), player.getY(), 1);
			allEntities.add(newBullet);
			playerBullets.add(newBullet);
			newBullet.shootDown();
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			//System.out.println(Math.random()*5000 + 2200);
			for (Barrel barrel : allBarrels) {
				System.out.println(barrel.position);
				if (player.canShoot()) {
					if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
						Bullet newBullet = new Bullet(player.getX(), player.getY(), 1);
						allEntities.add(newBullet);
						playerBullets.add(newBullet);
						newBullet.shootLeft();
					} else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
						Bullet newBullet = new Bullet(player.getX(), player.getY(), 1);
						allEntities.add(newBullet);
						playerBullets.add(newBullet);
						newBullet.shootRight();
					} else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
						Bullet newBullet = new Bullet(player.getX(), player.getY(), 1);
						allEntities.add(newBullet);
						playerBullets.add(newBullet);
						newBullet.shootUp();
					} else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
						Bullet newBullet = new Bullet(player.getX(), player.getY(), 1);
						allEntities.add(newBullet);
						playerBullets.add(newBullet);
						newBullet.shootDown();
					}
				}
			}
		}
		// Check for bullet collision
		for (Island island : allIslands) { // iterate through islands
			for (Rectangle rect : island.getHitBoxes()) { // iterate through each island's hitboxes
				Iterator<Bullet> i = playerBullets.iterator();
				while (i.hasNext()) {    // iterate through all bullets in game.
					Bullet bullet = i.next();
					if (bullet.getRect().overlaps(rect)) {
						island.takeDamage(bullet.getDamage());    // if bullet collides with island, damage the island

						if (island.getState() == 1) {
							gold += 50;
							score += 100;
						}
						bullet.dispose();
						i.remove();
						playerBullets.remove(bullet);
						allEntities.remove(bullet);    // remove bullet from game
					}

					if (bullet.isDead()) { // if bullet has existed for too long i.e. it's been shot but hasn't collided with anything for x amount of seconds
						bullet.dispose();
						i.remove();
						playerBullets.remove(bullet);
						allEntities.remove(bullet); // remove bullet from game
					}
				}
			}
		}

		Iterator<EnemyBullet> enemyBulletIterator = enemyBullets.iterator();
		while (enemyBulletIterator.hasNext()) {            //loop through all bullets
			EnemyBullet someBullet = enemyBulletIterator.next();

			if (someBullet.isDead()) { // if bullet has existed for too long i.e. it's been shot but hasn't collided with anything for x amount of seconds
				someBullet.dispose();
				enemyBulletIterator.remove();
				enemyBullets.remove(someBullet);
				allEntities.remove(someBullet); // remove bullet from game
			}

			if (someBullet.rect.overlaps(player.getRect())) {    //if player overlaps with bullet
				player.takeDamage(20);
				enemyBulletIterator.remove();
				enemyBullets.remove(someBullet);            //remove bullet
				allEntities.remove(someBullet);
			}
		}

		if (player.getHealth() <= 0) {
			player.dispose();
		}


		// update timer - used for adding score over time
		if (timer(1)) {
			score += 1;
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

		//Check if the player has collided with a barrel
		Iterator<Barrel> i = allBarrels.iterator();
		while (i.hasNext()) {            //loop through all barrels
			Barrel barrel = i.next();
			if (player.getRect().overlaps(barrel.getRect())) {    //if player overlaps barrel
				gold += 10;
				barrelsCollected += 1;
				i.remove();
				allBarrels.remove(barrel);            //remove barrel and add gold
				allEntities.remove(barrel);
				barrel.dispose();
			}
		}


		//Check task completion
		if (barrelsCollected >= 3)
			isBarrelsTaskComplete = true;
		if (player.position.x >= 6000f && player.position.x <= 6100f && player.position.y >= 7050 && player.position.y <= 7250) {
			//if (6050f % player.position.x <= Math.abs(50) && 7150 % player.position.y <= Math.abs(100)){
			player.enableShooting();
			isCoordTaskComplete = true;
		}

		isFinalTaskComplete = true;
		for (Island college : allIslands) {
			if (college.getHealth() > 0)    //Check final task completion
				isFinalTaskComplete = false;
		}


		//Write text to Screen

		islandText.draw(game.batch, allIslands.get(0).getName() + "\n     " + allIslands.get(0).getHealth(), 3620, 5550); // James College
		islandText.draw(game.batch, allIslands.get(1).getName() + "\n      " + allIslands.get(1).getHealth(), 5950, 3250); // Langwith College
		islandText.draw(game.batch, allIslands.get(2).getName() + "\n      " + allIslands.get(2).getHealth(), 6400, 6650); // Vanbruh College


		for (Island island : allIslands) {
			island.update();
			if (island.ready == true) {
				EnemyBullet newEnemyBullet = new EnemyBullet(island.location.x, island.location.y, 20);
				allEntities.add(newEnemyBullet);
				enemyBullets.add(newEnemyBullet);
				newEnemyBullet.targetPlayer(player.position, island.location);
				island.ready = false;
			}
		}


		if (barrelsCollected < 3)
			hudText.draw(game.batch, "Collect 3 barrels: " + barrelsCollected + "/3", camera.position.x - 950, camera.position.y + 270);
		else
			completedTaskText.draw(game.batch, "Collect 3 barrels: Completed", camera.position.x - 950, camera.position.y + 270);

		if (isCoordTaskComplete) {
			completedTaskText.draw(game.batch, "Pick up weapon: Completed", camera.position.x - 950, camera.position.y + 190);
			hudText.draw(game.batch, "Final Objective", camera.position.x - 950, camera.position.y + 50);
			if (isFinalTaskComplete)
				completedTaskText.draw(game.batch, "Capture all colleges: Completed", camera.position.x - 950, camera.position.y + -30);
			else
				hudText.draw(game.batch, "Capture all colleges", camera.position.x - 950, camera.position.y - 30);
		} else {
			hudText.draw(game.batch, "Pick up weapon at: (6050, 7150)", camera.position.x - 950, camera.position.y + 190);
		}

		scoreText.draw(game.batch, "Score: " + score, camera.position.x - 950, camera.position.y + 620);
		goldText.draw(game.batch, "Gold: " + gold, camera.position.x - 125, camera.position.y + 620);
		healthText.draw(game.batch, "Health: " + player.getHealth(), camera.position.x + 400, camera.position.y + 590);
		hudText.draw(game.batch, "x: " + (Math.round(player.getX())) + "    " + "y: " + (Math.round(player.getY())), camera.position.x - 950, camera.position.y + 470);


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
	private BitmapFont createTextFont(String fontPath, int fontSize, Color fontColour, float borderWidth, Color borderColour){
		FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
		FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		fontParameter.size = fontSize;
		fontParameter.color = fontColour;
		fontParameter.borderWidth = borderWidth;
		fontParameter.borderColor = borderColour;
		BitmapFont bitmapFont = fontGenerator.generateFont(fontParameter);
		return bitmapFont;
	}


	/**
	 * A timer method
	 * @param period A length of time in seconds (as a float)
	 * @return true every time a length of 'period' seconds passes, false otherwise
	 */
	private boolean timer(float period){
		if (timeElapsed > period){
			timeElapsed = 0f;
			return true;
		}
		else
			timeElapsed += Gdx.graphics.getDeltaTime();
			return false;
	}





	@Override
	public void dispose() {
		player.dispose();
		tiledMap.dispose();
	}
}
