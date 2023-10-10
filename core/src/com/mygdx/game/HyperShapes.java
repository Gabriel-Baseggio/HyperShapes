package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class HyperShapes extends Game {

    public static HyperShapes INSTANCE;
    private int screenWidth, screenHeight;
    private OrthographicCamera camera;

    public HyperShapes() {
        INSTANCE = this;
    }

    @Override
    public void create() {
        this.screenWidth = Gdx.graphics.getWidth();
        this.screenHeight = Gdx.graphics.getHeight();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, screenWidth, screenHeight);

        setScreen(new GameScreen(camera));
    }

    public OrthographicCamera getCamera() {
        return this.camera;
    }

    public int getScreenWidth() {
        return this.screenWidth;
    }

    public int getScreenHeight() {
        return this.screenHeight;
    }

}
