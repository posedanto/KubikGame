package com.posedanto.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Agitatore on 23.04.2017.
 */

public class SimpleButton {
    private int x, y, width, height;
    private String id;
    private Texture button, buttonPressed;
    private boolean isPressed = false;
    private Rectangle bounds;

    public SimpleButton(int x, int y, int width, int height, Texture button, Texture buttonPressed,
                        String id) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.button = button;
        this.buttonPressed = buttonPressed;
        bounds = new Rectangle(x, y, width, height);
        this.id = id;
    }

    public void draw(SpriteBatch batch) {
        if (isPressed) {
            batch.draw(buttonPressed, x, y, width, height);
        } else {
            batch.draw(button, x, y, width, height);
        }
    }

    public boolean isTouchDown(int screenX, int screenY) {
        if (bounds.contains(screenX, screenY)) {
            isPressed = true;
            return true;
        }
        return false;
    }

    public boolean isTouchUp(int screenX, int screenY) {
        if (bounds.contains(screenX, screenY) && isPressed) {
            isPressed = false;
            return true;
        }
        isPressed = false;
        return false;
    }

    public String getId() {
        return id;
    }
}
