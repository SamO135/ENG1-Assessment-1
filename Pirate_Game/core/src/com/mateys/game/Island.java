package com.mateys.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public class Island {

    private final ArrayList<Rectangle> hitBoxes = new ArrayList<Rectangle>();
    private final String name;
    private final int goldStored;
    private int health;
    private int state = 0; // 1 if island is destroyed/captured, 2 otherwise (i think)
    private int shootRange;
    private float timeBtwShots = 2f;
    private float timeElapsed = 0f; //Used for the timer method
    private Rectangle visionBox; // The area around the island where, if the player is inside, the island will start shooting
    private TiledMap tiledMap;
    public Vector2 position;
    public Boolean readyToShoot = false;


    /**
     * Constructs a new Island object
     * @param tiledMap The .tmx file that contains the map layout/data
     * @param ObjectLayerName The name of a layer in the .tmx file
     * @param health The health given to the island
     * @param pos The position of the island
     */
    public Island(TiledMap tiledMap, String ObjectLayerName, int health, Vector2 pos, int goldStored) {
        this.name = ObjectLayerName;
        this.health = health;
        this.position = pos;
        this.tiledMap = tiledMap;
        this.shootRange = 2000;
        this.visionBox = new Rectangle();
        this.visionBox.setSize(this.shootRange);
        this.visionBox.setCenter(this.position);
        this.goldStored = goldStored;


        for (MapObject object: this.tiledMap.getLayers().get(name).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            hitBoxes.add(rect);
        }

    }

    public void update() {
        if (!isCaptured()){
            if (timer(timeBtwShots))
                readyToShoot = true;
            else
                readyToShoot = false;
        }
    }

    /**
     * Subtracts the provided number from the island's health
     * @param damage an integer value of the damage the island will take
     */
    public void takeDamage(int damage) {
        if (this.state == 0) {
            health -= damage;
            if (health <= 0) {
                this.state = 1;
            }
        }
    }

    /** @return 1 if the island has been captured, 2 otherwise */
    public int getState() {
        if (this.state == 1) {
            this.state = 2;
            return 1;
        }
        return this.state;
    }

    /** @return the name of the island */
    public String getName() {
        return this.name;
    }

    /** @return the current health of the island */
    public int getHealth() {
        return this.health;
    }

    /** @return a list of all the hitboxes of the island */
    public ArrayList<Rectangle> getHitBoxes() {
        return hitBoxes;
    }

    /** @return true if the island has been captured, false otherwise*/
    public boolean isCaptured(){
        if (this.health <= 0)
            return true;
        else
            return false;
    }

    /** @return a rect that specifies a region where the island can see the player. If they are not within this region, the island will not shoot. */
    public Rectangle getVisionBox(){
        return this.visionBox;
    }

    /**
     * @return an integer value of the amount of gold stored on the island
     */
    public int getGoldStored(){
        return this.goldStored;
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
        else{
            timeElapsed += Gdx.graphics.getDeltaTime();
            return false;
        }
    }
}
