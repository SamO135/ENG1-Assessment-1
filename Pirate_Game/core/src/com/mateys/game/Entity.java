package com.mateys.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Entity {
    protected Texture image;
    protected Vector2 position; // position vector of entity
    protected TextureRegion textureRegion;
    protected Rectangle rect; // The hitbox of the entity
    public int rotation;


    protected Entity(){
    }

    protected void render(SpriteBatch batch){
        batch.draw(image, position.x, position.y);
    }

    protected void update(){}

    //returns x position of entity
    public float getX(){return this.position.x;}
    //returns y position of entity
    public float getY(){return this.position.y;}

}
