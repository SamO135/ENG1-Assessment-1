package com.mateys.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    private Rectangle rect; // The hitbox of the entity
    public Texture image;
    public Vector2 position; // position vector of entity
    public TextureRegion textureRegion;
    public int rotation;


    public Entity(float x, float y){
        this.image = new Texture("PlayerShip.png");
        this.textureRegion = new TextureRegion(image);
        this.position = new Vector2(x, y);
        this.rect = new Rectangle(this.position.x, this.position.y, this.image.getWidth(), this.image.getHeight());
    }

    /**
     * Draws the entity image at its x and y position
     * @param batch a SpriteBatch instance
     */
    public void render(SpriteBatch batch){
        batch.draw(textureRegion, this.position.x - this.image.getWidth()/2, this.position.y - this.image.getHeight()/2, this.textureRegion.getRegionWidth()/2, this.textureRegion.getRegionHeight()/2, this.image.getWidth(), this.image.getHeight(), 1, 1, this.rotation);
    }

    public void update(){
        this.rect.setPosition(this.position);
    }


    /** @return the x position of the entity*/
    public float getX(){return this.position.x;}
    /** @return the y position of the entity*/
    public float getY(){return this.position.y;}
    /** @return the rect of the entity */
    public Rectangle getRect(){return this.rect;}

}
