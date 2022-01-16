package com.mateys.game;

import com.badlogic.gdx.Game;

public class Mateys extends Game {

    @Override
    public void create() {
        setScreen(new GameScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

}
