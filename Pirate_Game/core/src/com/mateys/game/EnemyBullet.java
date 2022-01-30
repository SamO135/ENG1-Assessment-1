package com.mateys.game;

import com.badlogic.gdx.math.Vector2;

import com.mateys.game.Bullet;

public class EnemyBullet extends Bullet {
    public Vector2 playerDirection;
    private float scaling;
    private static final int MOVE_SPEED = 3;
    private float magnitude;


    public EnemyBullet(float x, float y, float period) {
        super(x, y, period);
        playerDirection = new Vector2();
        this.damage = 20;

    }

    /**
     * A method to shoot in the direction of the player
     * @param playerPos the Vector2 position of the player
     * @param islandPos the Vector2 position of the island
     */
    public void targetPlayer(Vector2 playerPos, Vector2 islandPos) {
        playerDirection.set(playerPos.x - islandPos.x, playerPos.y - islandPos.y);
        magnitude = (float) Math.sqrt(Math.pow(playerDirection.x, 2) + Math.pow(playerDirection.y, 2));
        scaling = magnitude/MOVE_SPEED;
        playerDirection.scl(1/scaling);
        this.setVelocity(playerDirection.x * MOVE_SPEED, playerDirection.y * MOVE_SPEED);
    }
    
}
