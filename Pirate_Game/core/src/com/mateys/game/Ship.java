package com.mateys.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Ship extends Entity{
    public static int MOVE_SPEED = 500;
    protected Vector2 movement; // The direction of movement as a vector;
    private float health;


    public Ship(float x, float y){
        super(x, y);
        this.image = new Texture(Gdx.files.internal("PlayerShip.png")); //Assigns a default image of a ship to the instance's image variable
        this.textureRegion = new TextureRegion(image);
        this.movement = new Vector2(0, 0);
        //this.position = new Vector2(x, y);
        //this.rect = new Rectangle(this.position.x, this.position.y, this.image.getWidth(), this.image.getHeight());
    }

    /**
     * subtracts damage from health
     * @param damage the amount of health to remove
     */
    public void takeDamage(float damage){this.health -= damage;}

    /** @return the health of the Ship instance */
    public float getHealth(){return this.health;}

    /** @return the rect of the ship */
    public Rectangle getRect(){return this.rect;}

    
    /** Move the ship to the left */
    public void moveLeft(){
        this.movement.add(-1, 0); //set the 'direction' to left
        this.rotation = 270;
    }
    /** Move the ship to the right */
    public void moveRight(){
        this.movement.add(1, 0); //set the 'direction' to right
        this.rotation = 90;
    }
    /** Move the ship up */
    public void moveUp(){
        this.movement.add(0, 1); //set the 'direction' to up
        this.rotation = 180;
    }
    /** Move the ship down */
    public void moveDown(){
        this.movement.add(0, -1); //set the 'direction' to down
        this.rotation = 0;
    }

}
