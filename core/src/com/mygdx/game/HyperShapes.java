package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.screens.TitleScreen;

public class HyperShapes extends Game {

    public static HyperShapes INSTANCE;
    private int screenWidth, screenHeight, highscore, difficulty;
    private OrthographicCamera camera;
    public HyperShapes() {
        INSTANCE = this;
    }

    @Override
    public void create() {
        this.screenWidth = Gdx.graphics.getWidth();
        this.screenHeight = Gdx.graphics.getHeight();
        this.difficulty = 1;
        this.highscore = 0;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, screenWidth, screenHeight);

        setScreen(new TitleScreen(camera));
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

    public int getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getHighscore() {
        return this.highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

}
