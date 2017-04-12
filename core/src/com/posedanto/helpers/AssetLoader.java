package com.posedanto.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by Agitatore on 25.03.2017.
 */

public class AssetLoader {
    public static Texture field, brick, brick2, scoreboard, next;
    public static Texture[] rainbowBricks = new Texture[7];
    public static BitmapFont font;

    public static void load() {
        field = new Texture(Gdx.files.internal("field.png"));
        brick = new Texture(Gdx.files.internal("brick.png"));
        brick2 = new Texture(Gdx.files.internal("brick2.png"));
        scoreboard = new Texture(Gdx.files.internal("scoreboard.png"));
        next = new Texture(Gdx.files.internal("next.png"));
        rainbowBricks[0] = new Texture(Gdx.files.internal("brickRED.png"));
        rainbowBricks[1] = new Texture(Gdx.files.internal("brickORANGE.png"));
        rainbowBricks[2] = new Texture(Gdx.files.internal("brickYELLOW.png"));
        rainbowBricks[3] = new Texture(Gdx.files.internal("brickGREEN.png"));
        rainbowBricks[4] = new Texture(Gdx.files.internal("brickBLUE.png"));
        rainbowBricks[5] = new Texture(Gdx.files.internal("brickINDIGO.png"));
        rainbowBricks[6] = new Texture(Gdx.files.internal("brickVIOLET.png"));

        font = new BitmapFont(Gdx.files.internal("font.fnt"));
    }

    public static void dispose() {
        field.dispose();
        brick.dispose();
        brick2.dispose();
        scoreboard.dispose();
        next.dispose();
        for(Texture b : rainbowBricks)
            b.dispose();
        font.dispose();
    }
}
