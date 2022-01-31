package com.mateys.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class EnemyBullet extends Bullet {
    public Vector2 playerDirection;
    private static final int MOVE_SPEED = 1000;
    private float magnitude;


    public EnemyBullet(float x, float y, float period) {
        super(x, y, period);
        playerDirection = new Vector2(); // The vector that points in the direction of the player from island's position
        this.damage = 20;

    }

    /**
     * A method to shoot in the direction of the player
     * @param playerPos the Vector2 position of the player
     * @param islandPos the Vector2 position of the island
     */
    public void targetPlayer(Vector2 playerPos, Vector2 islandPos) {
        playerDirection.set(playerPos.x - islandPos.x, playerPos.y - islandPos.y); // calculate the vector
        magnitude = (float) Math.sqrt(Math.pow(playerDirection.x, 2) + Math.pow(playerDirection.y, 2)); // calculate the magnitude of the vector
        playerDirection.scl(1/magnitude); // calculate the unit vector by dividing by the magnitude
        this.setVelocity(playerDirection.x * MOVE_SPEED * Gdx.graphics.getDeltaTime(), playerDirection.y * MOVE_SPEED * Gdx.graphics.getDeltaTime()); // set the velocity to the unit vector multiplied by the bullet's move speed
    }
}