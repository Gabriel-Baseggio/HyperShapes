package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.HyperShapes;
import com.mygdx.game.helper.FontHelper;

public class GameOverScreen extends ScreenAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;

    private GameScreen gameScreen;

    public GameOverScreen(OrthographicCamera camera, GameScreen gameScreen) {
        this.camera = camera;
        this.batch = new SpriteBatch();

        this.gameScreen = gameScreen;
    }

    public void update() {

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            HyperShapes.INSTANCE.setScreen(new TitleScreen(this.camera));
        }

    }

    @Override
    public void render(float delta) {
        update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        FontHelper.write(batch, "Pressione SPACE para voltar à tela inicial", new Vector2(10, HyperShapes.INSTANCE.getScreenHeight() - 30), 30);

        FontHelper.write(batch, "Game Over!", new Vector2(HyperShapes.INSTANCE.getScreenWidth() / 2 - 200 , HyperShapes.INSTANCE.getScreenHeight() / 2), 50);

        FontHelper.write(batch, ("Highscore: " + HyperShapes.INSTANCE.getHighscore()), new Vector2(HyperShapes.INSTANCE.getScreenWidth() / 2 - 200, HyperShapes.INSTANCE.getScreenHeight() / 2 - 100), 30);
        FontHelper.write(batch, ("Seus pontos: " + gameScreen.getPlayer().getScore()), new Vector2(HyperShapes.INSTANCE.getScreenWidth() / 2 - 200, HyperShapes.INSTANCE.getScreenHeight() / 2 - 200), 30);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
