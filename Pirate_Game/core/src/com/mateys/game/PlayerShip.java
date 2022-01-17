package com.mateys.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class PlayerShip {

    private final Rectangle playerShip;
    public Texture playerShipImage;
    private Vector2 playerPosition;
    public Vector2 playerMovement;

    public PlayerShip(float x, float y) {

        playerShip = new Rectangle();

        // load the ship image
        playerShipImage = new Texture(Gdx.files.internal("PlayerShip.png"));

        // set position
        playerShip.x = x - (playerShip.width / 2);
        playerShip.y = y - (playerShip.height / 2);
        playerPosition = new Vector2(playerShip.x, playerShip.y);

        // initialise movement vector
        playerMovement = new Vector2(0, 0);

    }

    public void render() {
        // set ship position at 'position' + (normalised 'movement' * 'MOVE_SPEED') * DeltaTime
        float MOVE_SPEED = 500;
        playerShip.setPosition(playerPosition.mulAdd(((playerMovement.nor()).scl(MOVE_SPEED)), Gdx.graphics.getDeltaTime()));

    }

    public float getX() {
        return playerPosition.x;
    }

    public float getY() {
        return playerPosition.y;
    }

    public void dispose() {
        playerShipImage.dispose();
    }
}
