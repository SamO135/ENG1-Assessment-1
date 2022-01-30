package com.mateys.game;

import com.badlogic.gdx.math.Vector2;

import com.mateys.game.Bullet;

public class EnemyBullet extends Bullet {


    public EnemyBullet(float x, float y, float period) {
        super(x, y, period);
    }

    public void targetPlayer(Vector2 playerPos, Vector2 islandPos) {

        Vector2 playerDirection = new Vector2( playerPos.x - islandPos.x, playerPos.y - islandPos.y);
        float magnitude = (float) (Math.pow(islandPos.x - playerPos.x, 2) + Math.pow(islandPos.y - playerPos.y, 2));
        playerDirection.x = playerDirection.x / magnitude;
        playerDirection.y = playerDirection.y / magnitude;

        this.setVelocity(playerDirection.x * 15000, playerDirection.y * 15000);
    }
    
}
