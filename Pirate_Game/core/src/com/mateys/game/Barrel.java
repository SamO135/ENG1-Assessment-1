package com.mateys.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Barrel extends Entity{

    /**
     * Contructs a barrel at the given position (with a preset image)
     * @param x the x position of the barrel
     * @param y the y position of the barrel
     * @param rotation the rotation of the barrel in degrees (0 -360)
     */
    public Barrel(float x, float y, int rotation){
        super(x, y);
        this.rotation = rotation;
        this.image = new Texture(Gdx.files.internal("barrel.png"));
        this.textureRegion = new TextureRegion(image);
    }


    public void dispose(){
        this.image.dispose();
    }
}
