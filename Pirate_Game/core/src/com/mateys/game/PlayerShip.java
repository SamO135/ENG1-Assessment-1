package com.mateys.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class PlayerShip extends Ship{


    public PlayerShip(float x, float y) {
        super(x, y);
    }

    public void update() {
        // set ship position at: 'position' + (normalised 'movement' * 'MOVE_SPEED') * DeltaTime
        this.position.mulAdd(this.movement.nor(), MOVE_SPEED * Gdx.graphics.getDeltaTime()); //calculates and sets new player position
        this.rect.setPosition(this.position); //updates the player rect/hitbox
    }

    public void render(SpriteBatch batch) {
        //It is ugly but allows you to specify the rotation of the image.
        //parameters are: TextureRegion, x position on screen, y position on screen, x position within TextureRegion, y position within TextureRegion, image width, image height, image x scale, image y scale, image rotation
        batch.draw(textureRegion, this.position.x - this.image.getWidth()/2, this.position.y - this.image.getHeight()/2, this.textureRegion.getRegionWidth()/2, this.textureRegion.getRegionHeight()/2, this.image.getWidth(), this.image.getHeight(), 1, 1, this.rotation);
    }



    public void dispose() {this.image.dispose();}
}
