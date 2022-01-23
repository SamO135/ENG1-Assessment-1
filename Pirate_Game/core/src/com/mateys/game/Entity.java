package com.mateys.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Entity {
    protected Texture image;
    protected Vector2 position;
    protected TextureRegion textureRegion;
    protected int rotation;


    protected Entity(){

    }

    protected void render(SpriteBatch batch){
        batch.draw(image, position.x, position.y);
    }

    protected void update(){}

    public float getX(){return this.position.x;}
    public float getY(){return this.position.y;}

}
