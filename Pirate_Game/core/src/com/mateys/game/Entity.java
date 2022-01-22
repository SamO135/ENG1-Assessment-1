package com.mateys.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Null;

public class Entity {
    protected Texture image;
    protected Vector2 position; // position vector of entity
    protected TextureRegion textureRegion;
    protected Rectangle rect; // The hitbox of the entity
    public int rotation;


    protected Entity(){
    }

    /**
     * Draws the entity image at its x and y position
     * @param batch a SpriteBatch instance
     */
    protected void render(@Null SpriteBatch batch){
        batch.draw(image, position.x, position.y);
    }

    protected void update(){}


    /** @return the x position of the entity*/
    public float getX(){return this.position.x;}
    /** @return the y position of the entity*/
    public float getY(){return this.position.y;}

}
