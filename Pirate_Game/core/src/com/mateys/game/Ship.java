package com.mateys.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Ship extends Entity{
    public static final int MOVE_SPEED = 500;
    protected Vector2 movement;
    private float health;


    public Ship(float x, float y){
        this.image = new Texture(Gdx.files.internal("PlayerShip.png")); //Assigns a default image of a ship to the instance's image variable
        this.textureRegion = new TextureRegion(image);
        this.movement = new Vector2(0, 0);
        this.position = new Vector2(x, y);
        this.rect = new Rectangle(this.position.x, this.position.y, this.image.getWidth(), this.image.getHeight());
    }

    // subtracts damage from health
    public void takeDamage(float damage){this.health -= damage;}

    //returns the health of the Ship instance
    public float getHealth(){return this.health;}

    //returns the rect/hitbox of the Ship instance
    public Rectangle getRect(){return this.rect;}


    //Move ship in each direction
    public void moveLeft(){
        this.movement.add(-1, 0); //set 'direction' to left
        this.rotation = 270;
    }
    public void moveRight(){
        this.movement.add(1, 0); //set 'direction' to right
        this.rotation = 90;
    }
    public void moveUp(){
        this.movement.add(0, 1); //set 'direction' to up
        this.rotation = 180;
    }
    public void moveDown(){
        this.movement.add(0, -1); //set 'direction' to down
        this.rotation = 0;
    }

}
