package com.mateys.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class PlayerShip extends Entity{
    public static final int MOVE_SPEED = 500;
    public Vector2 movement;
    private Rectangle playerRect;
    TextureRegion textureRegion;
    int rotation;


    public PlayerShip(float x, float y) {
        this.image = new Texture(Gdx.files.internal("PlayerShip.png"));
        this.textureRegion = new TextureRegion(image);
        // this.position = new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        this.position = new Vector2(2500, 2500);
        this.movement = new Vector2(0, 0);
        this.playerRect = new Rectangle(this.position.x, this.position.y, this.image.getWidth(), this.image.getHeight());

    }

    public void update() {
        // set ship position at 'position' + (normalised 'movement' * 'MOVE_SPEED') * DeltaTime
        this.position.mulAdd(this.movement.nor(), MOVE_SPEED * Gdx.graphics.getDeltaTime());
        this.playerRect.setPosition(this.position);
    }





    public void render(SpriteBatch batch) {
        batch.draw(textureRegion, this.position.x - this.image.getWidth()/2, this.position.y - this.image.getHeight()/2, this.textureRegion.getRegionWidth()/2, this.textureRegion.getRegionHeight()/2, this.image.getWidth(), this.image.getHeight(), 1, 1, this.rotation);
        //sprite.setPosition(this.position.x, this.position.y);
        //sprite.draw(batch);
        //sprite.setRotation(this.rotation);
    }

    public void moveLeft(){
        this.movement.add(-1, 0);
        this.rotation = 270;
    }
    public void moveRight(){
        this.movement.add(1, 0);
        this.rotation = 90;
    }


    public Rectangle getPlayerRect() { return playerRect; }


    public void moveUp(){
        this.movement.add(0, 1);
        this.rotation = 180;
    }
    public void moveDown(){
        this.movement.add(0, -1);
        this.rotation = 0;
    }


    public void dispose() {this.image.dispose();}
}
