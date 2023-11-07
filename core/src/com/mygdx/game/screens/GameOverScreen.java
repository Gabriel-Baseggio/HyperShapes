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
import com.mygdx.game.HyperShapes;

public class GameOverScreen extends ScreenAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;

    private GameScreen gameScreen;

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont bitmapTitle;
    private BitmapFont bitmap;

    public GameOverScreen(OrthographicCamera camera, GameScreen gameScreen) {
        this.camera = camera;
        this.batch = new SpriteBatch();

        this.gameScreen = gameScreen;

        this.generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        this.parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.parameter.borderWidth = 1;
        this.parameter.color = Color.WHITE;

        this.parameter.size = 60;
        this.bitmapTitle = generator.generateFont(parameter);

        this.parameter.size = 30;
        this.bitmap = generator.generateFont(parameter);
    }

    public void update() {

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER) || Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            HyperShapes.INSTANCE.setScreen(new TitleScreen(this.camera));
        }

    }

    @Override
    public void render(float delta) {
        update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        bitmap.draw(batch, "Pressione SPACE para voltar à tela inicial", 10, HyperShapes.INSTANCE.getScreenHeight() - parameter.size);

        bitmapTitle.draw(batch, "Game Over!", HyperShapes.INSTANCE.getScreenWidth() / 2 - 210, HyperShapes.INSTANCE.getScreenHeight() / 2);

        bitmapTitle.draw(batch, "Seus pontos: " + gameScreen.getPlayer().getScore(), 10, 50 + bitmapTitle.getCapHeight()*2);
        bitmapTitle.draw(batch, "Highscore: " + HyperShapes.INSTANCE.getHighscore(), 10, 20 + bitmapTitle.getCapHeight());

        batch.end();
    }

    @Override
    public void dispose() {
        bitmap.dispose();
        bitmapTitle.dispose();
        generator.dispose();
        batch.dispose();
    }
}
