package com.mateys.game;

import com.badlogic.gdx.Gdx;


public class PlayerShip extends Ship{
    private boolean canShoot = false;

    /**
     * Constructs a new player object
     * @param x the x position of the player
     * @param y the y position of the player
     */
    public PlayerShip(float x, float y) {
        super(x, y);
        this.setHealth(100);
    }

    /** Updates the position and rect of the player ship */
    public void update() {
        if (!isDead) {
            this.position.mulAdd(this.direction.nor(), moveSpeed * Gdx.graphics.getDeltaTime()); //calculates and sets new player position
            super.update();
        }
    }


    /** Enables shooting with the cannon */
    public void enableShooting(){this.canShoot = true;}

    /** Disables shooting with the cannon */
    public void disableShooting(){this.canShoot = false;}

    /** @return true if the player is able to shoot its cannon, false otherwise. */
    public boolean canShoot(){return this.canShoot;}

    public void dispose() {
        this.image.dispose();
        this.isDead = true;
        //this.rect.setPosition(-10000, -10000);
    }
}
