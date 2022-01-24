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
    private int state = 0; // 1 if island is destroyed/captured, 2 otherwise (i think)
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

    /**
     * Subtracts the provided number from the island's health
     * @param damage an integer value of the damage the island will take
     */
    public void takeDamage(int damage) {
        if (this.state == 0) {
            health -= damage;
            if (health <= 0) {
                this.state = 1;
            }
        }
    }

    /** @return 1 if the island has been captured, 2 otherwise */
    public int getState() {
        if (this.state == 1) {
            this.state = 2;
            return 1;
        }
        return this.state;
    }

    /** @return the name of the island */
    public String getName() {
        return this.name;
    }

    /** @return the current health of the island */
    public int getHealth() {
        return this.health;
    }

    /** @return a list of all the hitboxes of the island */
    public ArrayList<Rectangle> getHitBoxes() {
        return hitBoxes;
    }
}
