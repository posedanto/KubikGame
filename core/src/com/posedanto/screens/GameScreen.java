package com.posedanto.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.posedanto.gameobjects.FigureForms;
import com.posedanto.gameworld.GameRenderer;
import com.posedanto.gameworld.GameWorld;
import com.posedanto.helpers.InputHandler;

/**
 * Created by Agitatore on 25.03.2017.
 */

public class GameScreen implements Screen {
    GameWorld world;
    GameRenderer renderer;

    public GameScreen() {
        FigureForms.initRet();
        world = new GameWorld();
        Gdx.input.setInputProcessor(new InputHandler(world));
        renderer = new GameRenderer(world);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        world.update(delta);
        renderer.render(delta);

        //Gdx.app.log("GameScreen FPS", (1/delta) + "");
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
