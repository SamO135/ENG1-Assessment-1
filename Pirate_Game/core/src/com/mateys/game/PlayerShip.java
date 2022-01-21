package com.mateys.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class PlayerShip {

    public Texture image;
    private Vector2 position;
    public Vector2 movement;
    private Rectangle playerRect;
    TextureRegion textureRegion;
    int MOVE_SPEED = 500;
    int rotation;

    public PlayerShip(float x, float y) {
        this.image = new Texture(Gdx.files.internal("PlayerShip.png"));
        this.textureRegion = new TextureRegion(image);
        // this.position = new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        this.position = new Vector2(2500, 2500);
        this.movement = new Vector2(0, 0);
        this.playerRect = new Rectangle(this.position.x, this.position.y, 64, 64);

    }

    public void update() {
        // set ship position at 'position' + (normalised 'movement' * 'MOVE_SPEED') * DeltaTime
        this.position.mulAdd(this.movement.nor(), MOVE_SPEED * Gdx.graphics.getDeltaTime());
        this.playerRect.setPosition(this.position);
    }





    public void render(SpriteBatch batch) {
        batch.draw(textureRegion, position.x - image.getWidth()/2, position.y - image.getHeight()/2, textureRegion.getRegionWidth()/2, textureRegion.getRegionHeight()/2, image.getWidth(), image.getHeight(), 1, 1, this.rotation);
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public Rectangle getPlayerRect() { return playerRect; }

    public void dispose() {
        image.dispose();
    }
}
