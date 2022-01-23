package com.mateys.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends Entity{
    private Vector2 velocity;
    public static final int MOVE_SPEED = 1000;
    private float size;
    private int damage;
    private Rectangle bulletRect;
    public boolean dead = false;
    public float timeAlive = 0f;
    private float period = 1f;

    public Bullet(float x, float y){
        this.image = new Texture(Gdx.files.internal("cannonBall.png"));
        this.textureRegion = new TextureRegion(image);
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);
        this.bulletRect = new Rectangle(this.position.x, this.position.y, 64, 64);
    }

    public void update(){
        this.position.add(velocity);
        this.bulletRect.setPosition(this.position);

        if (timeAlive > period) {
            dead = true;
        } else {
            timeAlive += Gdx.graphics.getDeltaTime();
        }
    }


    public int getDamage(){return this.damage;}
    public float getSize(){return this.size;}
    public Rectangle getBulletRect() {
        return bulletRect;
    }
    public boolean isDead() { return dead; }

    public void setVelocity(float x, float y){this.velocity = new Vector2(x, y);}

    public void shootLeft(){
        this.setVelocity(-MOVE_SPEED * Gdx.graphics.getDeltaTime(), 0);
    }
    public void shootRight(){
        this.setVelocity(MOVE_SPEED * Gdx.graphics.getDeltaTime(), 0);
    }
    public void shootUp(){
        this.setVelocity(0, MOVE_SPEED * Gdx.graphics.getDeltaTime());
    }
    public void shootDown(){
        this.setVelocity(0, -MOVE_SPEED * Gdx.graphics.getDeltaTime());
    }

    public void dispose() {
        this.image.dispose();

        // TODO find a better way remove bullet from screen
        this.position = new Vector2(10000, 10000);
    }

}
