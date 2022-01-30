package com.mateys.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class PlayerShip extends Ship{
    private boolean canShoot = false;


    public PlayerShip(float x, float y) {
        super(x, y);
    }

    /** Updates the position and rect of the player ship */
    public void update() {
        // set ship position at: 'position' + (normalised 'movement' * 'MOVE_SPEED') * DeltaTime
        this.position.mulAdd(this.movement.nor(), MOVE_SPEED * Gdx.graphics.getDeltaTime()); //calculates and sets new player position
        super.update();
        //this.rect.setPosition(this.position); //updates the player rect/hitbox
    }

    /** Renders the player ship at its position and rotation
     * @param batch a SpriteBatch instance
     */
    public void render(SpriteBatch batch) {
        //This is long and ugly but allows you to specify the rotation of the image.
        //parameters are: TextureRegion, x position on screen, y position on screen, x position within TextureRegion, y position within TextureRegion, image width, image height, image x scale, image y scale, image rotation
        batch.draw(textureRegion, this.position.x - this.image.getWidth()/2, this.position.y - this.image.getHeight()/2, this.textureRegion.getRegionWidth()/2, this.textureRegion.getRegionHeight()/2, this.image.getWidth(), this.image.getHeight(), 1, 1, this.rotation);
    }

    /** Enables shooting with the cannon */
    public void enableShooting(){this.canShoot = true;}

    /** Disables shooting with the cannon */
    public void disableShooting(){this.canShoot = false;}

    /** @return true if the player is able to shoot its cannon, false otherwise. */
    public boolean canShoot(){return this.canShoot;}


    public void dispose() {this.image.dispose();}
}
