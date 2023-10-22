package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class GameOverScreen extends ScreenAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont bitmap;

    public GameOverScreen(OrthographicCamera camera) {
        this.camera = camera;
        this.batch = new SpriteBatch();

        this.generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        this.parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.parameter.size = 60;
        this.parameter.borderWidth = 1;
        this.parameter.color = Color.WHITE;
        this.bitmap = generator.generateFont(parameter);
    }

    public void update() {

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER) || Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            HyperShapes.INSTANCE.setScreen(new GameScreen(this.camera));
        }

    }

    @Override
    public void render(float delta) {
        update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        bitmap.draw(batch, "Game Over!", HyperShapes.INSTANCE.getScreenWidth() / 2 - ("Game Over!").length() * 20, HyperShapes.INSTANCE.getScreenHeight() / 2);

        batch.end();
    }

    @Override
    public void dispose() {
        bitmap.dispose();
        generator.dispose();
        batch.dispose();
    }
}
