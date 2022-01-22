package com.mateys.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends Entity{
    private Vector2 velocity;
    public static final int MOVE_SPEED = 1000;
    private int damage;


    /**
     * Contructs a bullet at the given position (with a preset image and the velocity initialised to 0)
     * @param x the x position of the bullet
     * @param y the y position of the bullet
     */
    public Bullet(float x, float y){
        this.image = new Texture(Gdx.files.internal("cannonBall.png"));
        this.textureRegion = new TextureRegion(image);
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);
    }

    /** updates the bullet's position each frame by its velocity */
    public void update(){
        this.position.add(velocity);
    }

    /** @return the damage of the bullet */
    public int getDamage(){return this.damage;}

    /** @return the rect/hitbox of the bullet */
    public Rectangle getRect(){return this.rect;}

    /**
     * sets the velocity of the bullet
     * @param x the x component of the bullet's velocity
     * @param y the y component of the bullet's velocity
     */
    public void setVelocity(float x, float y){this.velocity = new Vector2(x, y);}

    /** sets the velocity of the bullet to the left */
    public void shootLeft(){
        this.setVelocity(-MOVE_SPEED * Gdx.graphics.getDeltaTime(), 0);
    }
    /** sets the velocity of the bullet to the right */
    public void shootRight(){
        this.setVelocity(MOVE_SPEED * Gdx.graphics.getDeltaTime(), 0);
    }
    /** sets the velocity of the bullet upwards */
    public void shootUp(){
        this.setVelocity(0, MOVE_SPEED * Gdx.graphics.getDeltaTime());
    }
    /** sets the velocity of the bullet downwards*/
    public void shootDown(){
        this.setVelocity(0, -MOVE_SPEED * Gdx.graphics.getDeltaTime());
    }

    public void dispose(){this.image.dispose();}

}
