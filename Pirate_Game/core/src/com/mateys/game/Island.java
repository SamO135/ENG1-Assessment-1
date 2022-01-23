package com.mateys.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class Island {

    private int health;
    private final ArrayList<Rectangle> hitBoxes = new ArrayList<Rectangle>();
    private final String name;
    private int state = 0;
    TiledMap tiledMap;

    public Island(String ObjectLayerName, TiledMap tiledMap, int health) {

        this.name = ObjectLayerName;
        this.health = health;
        this.tiledMap = tiledMap;

        for (MapObject object: this.tiledMap.getLayers().get(name).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            hitBoxes.add(rect);
        }

    }

    public void takeDamage() {
        if (this.state == 0) {
            health -= 50;
            if (health <= 0) {
                this.state = 1;
            }
        }
    }

    public int getState() {
        if (this.state == 1) {
            this.state = 2;
            return 1;
        }
        return this.state;
    }

    public String getName() {
        return this.name;
    }

    public int getHealth() {
        return this.health;
    }

    public ArrayList<Rectangle> getHitBoxes() {
        return hitBoxes;
    }
}
