package com.posedanto.kubikgame;

import com.badlogic.gdx.Game;
import com.posedanto.helpers.AssetLoader;
import com.posedanto.screens.StartScreen;

public class kGame extends Game {

    @Override
    public void create() {
        AssetLoader.load();
        setScreen(new StartScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
}