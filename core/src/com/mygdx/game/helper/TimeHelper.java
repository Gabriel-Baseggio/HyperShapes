package com.mygdx.game.helper;

import com.badlogic.gdx.Gdx;

public class TimeHelper {
    private float timeScale = 1.0f;

    public void setTimeScale(float scale) {
        this.timeScale = scale;
    }

    public float getDeltaTime() {
        return Gdx.graphics.getDeltaTime() * timeScale;
    }
}
