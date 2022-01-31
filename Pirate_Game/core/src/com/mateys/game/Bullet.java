package com.mateys.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends Entity{
    private Vector2 velocity;
    private int damage;
    private float timeElapsed = 0f;
    private float bulletLifeSpan;
    public static final int moveSpeed = 1000;
    public boolean isDead = false;


    /**
     * Contructs a bullet at the given position (with a preset image and the velocity initialised to 0)
     * @param x the x position of the bullet
     * @param y the y position of the bullet
     */
    public Bullet(float x, float y, float bulletLifeSpan){
        super(x, y);
        this.bulletLifeSpan = bulletLifeSpan;
        this.image = new Texture(Gdx.files.internal("cannonBall.png"));
        this.textureRegion = new TextureRegion(image);
        this.velocity = new Vector2(0, 0);
        this.damage = 50;
    }

    /** updates the bullet's position each frame by its velocity. Also checks if the bullet has existed for too long */
    public void update(){
        this.position.add(velocity);
        super.update();
        if(timer(bulletLifeSpan)){
            this.isDead = true;
        }
    }

    /** @return the damage of the bullet */
    public int getDamage(){return this.damage;}

    /**
     * Sets the damage value of the bullet
     * @param damage the damage as an integer
     */
    public void setDamage(int damage){this.damage = damage;}


    /** @return boolean value whether the bullet has existed for too long */
    public boolean isDead() { return isDead; }


    /**
     * sets the velocity of the bullet
     * @param x the x component of the bullet's velocity
     * @param y the y component of the bullet's velocity
     */
    public void setVelocity(float x, float y){this.velocity = new Vector2(x, y);}

    /** sets the velocity of the bullet to the left */
    public void shootLeft(){
        this.setVelocity(-moveSpeed * Gdx.graphics.getDeltaTime(), 0);
    }
    /** sets the velocity of the bullet to the right */
    public void shootRight(){
        this.setVelocity(moveSpeed * Gdx.graphics.getDeltaTime(), 0);
    }
    /** sets the velocity of the bullet upwards */
    public void shootUp(){
        this.setVelocity(0, moveSpeed * Gdx.graphics.getDeltaTime());
    }
    /** sets the velocity of the bullet downwards*/
    public void shootDown(){
        this.setVelocity(0, -moveSpeed * Gdx.graphics.getDeltaTime());
    }

    private boolean timer(float period){
        if (timeElapsed > period){
            timeElapsed = 0f;
            return true;
        }
        else{
            timeElapsed += Gdx.graphics.getDeltaTime();
            return false;
        }
    }

    public void dispose() {
        this.image.dispose();
    }

}
