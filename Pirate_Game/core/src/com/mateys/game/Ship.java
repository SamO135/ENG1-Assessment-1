package com.mateys.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Ship extends Entity{
    public static final int MOVE_SPEED = 500;
    private int health;
    protected boolean isDead = false;
    protected Vector2 direction;

    /**
     * Constructs a new Ship
     * @param x the x position of the ship
     * @param y the y positin of the ship
     */
    public Ship(float x, float y){
        super(x, y);
        this.image = new Texture(Gdx.files.internal("PlayerShip.png")); //Assigns a default image of a ship to the instance's image variable
        this.textureRegion = new TextureRegion(image);
        this.direction = new Vector2(0, 0);
    }

    /**
     * Constructs a new Ship
     * @param x the x position of the ship
     * @param y the y position of the ship
     * @param imagePath the path to an image to use for the ship
     */
    public Ship(float x, float y, String imagePath){
        this(x, y);
        this.image = new Texture(Gdx.files.internal(imagePath));
        this.textureRegion = new TextureRegion(image);
    }


    /** Renders the player ship at its position and rotation
     * @param batch a SpriteBatch instance
     */
    public void render(SpriteBatch batch) {
        //This is long and ugly but allows you to specify the rotation of the image.
        //parameters are: TextureRegion, x position on screen, y position on screen, x position within TextureRegion, y position within TextureRegion, image width, image height, image x scale, image y scale, image rotation
        if(!isDead) {
            batch.draw(textureRegion, this.position.x - this.image.getWidth() / 2, this.position.y - this.image.getHeight() / 2, this.textureRegion.getRegionWidth() / 2, this.textureRegion.getRegionHeight() / 2, this.image.getWidth(), this.image.getHeight(), 1, 1, this.rotation);
        }
    }

    /**
     * subtracts damage from health
     * @param damage the amount of health to remove
     */
    public void takeDamage(float damage){this.health -= damage;}

    /** @return the health of the Ship instance */
    public int getHealth(){return this.health;}

    public void setHealth(int health) {
        this.health = health;
    }

    /** @return the rect of the ship */
    public Rectangle getRect(){return this.rect;}

    
    /** Move the ship to the left */
    public void moveLeft(){
        this.direction.add(-1, 0); //set the 'direction' to left
        this.rotation = 270;
    }
    /** Move the ship to the right */
    public void moveRight(){
        this.direction.add(1, 0); //set the 'direction' to right
        this.rotation = 90;
    }
    /** Move the ship up */
    public void moveUp(){
        this.direction.add(0, 1); //set the 'direction' to up
        this.rotation = 180;
    }
    /** Move the ship down */
    public void moveDown(){
        this.direction.add(0, -1); //set the 'direction' to down
        this.rotation = 0;
    }

}
