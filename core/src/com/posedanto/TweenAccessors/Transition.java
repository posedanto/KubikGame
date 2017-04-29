package com.posedanto.TweenAccessors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by Agitatore on 29.04.2017.
 */

public class Transition {
    private static TweenManager manager;
    private static Value alpha = new Value();
    private static Color transitionColor = new Color();
    private static boolean rev;

    public static void prepareTransition(int r, int g, int b, float duration, TweenCallback cb, boolean reverse) {
        transitionColor.set(r / 255.0f, g / 255.0f, b / 255.0f, 1);
        rev = reverse;
        alpha.setValue(1);
        Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();
        Tween.to(alpha, -1, duration).target(0)
                .ease(TweenEquations.easeOutQuad).setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE).start(manager);
    }

    public static void drawTransition(ShapeRenderer shapeRenderer, float delta) {
        if (alpha.getValue() > 0) {
            manager.update(delta);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            if (rev) shapeRenderer.setColor(transitionColor.r, transitionColor.g,
                    transitionColor.b, 1 - alpha.getValue());
            else shapeRenderer.setColor(transitionColor.r, transitionColor.g,
                    transitionColor.b, alpha.getValue());
            shapeRenderer.rect(0, 0, 1080, 1920);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

        }
    }
}
