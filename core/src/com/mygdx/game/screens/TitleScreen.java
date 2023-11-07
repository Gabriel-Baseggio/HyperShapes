package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.game.HyperShapes;

/**
 * Classe que representa a tela de título do jogo.
 * Extende a classe ScreenAdapter da biblioteca LibGDX para poder utilizar de seus recursos.
 */
public class TitleScreen extends ScreenAdapter {
    /**
     * Propriedade do tipo OrthographicCamera que representa a câmera do jogo.
     */
    private OrthographicCamera camera;
    /**
     * Propriedade do tipo SpriteBatch que renderiza os sprites utilizados no jogo.
     */
    private SpriteBatch batch;

    /**
     * Propriedade do tipo FreeTypeFontGenerator que auxilia na criação de fontes.
     */
    private FreeTypeFontGenerator generator;
    /**
     * Propriedade do tipo FreeTypeFontGenerator.FreeTypeFontParameter que auxilia na criação de fontes.
     */
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    /**
     * Propriedade do tipo BitmapFont que auxilia na criação de fontes de título.
     */
    private BitmapFont bitmapTitle;
    /**
     * Propriedade do tipo BitmapFont que auxilia na criação de fontes de texto normal.
     */
    private BitmapFont bitmap;

    /**
     * Construtor da classe que atribui os valores inicias para suas variáveis.
     *
     * @param camera (OrthographicCamera)
     */
    public TitleScreen(OrthographicCamera camera) {
        this.camera = camera;
        this.batch = new SpriteBatch();

        this.generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        this.parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.parameter.borderWidth = 1;
        this.parameter.color = Color.WHITE;

        this.parameter.size = 60;
        this.bitmapTitle = generator.generateFont(parameter);

        this.parameter.size = 30;
        this.bitmap = generator.generateFont(parameter);

    }

    /**
     * Método que será chamado a cada frame do jogo para fazer uma atualização do que é necessário e verificar inputs
     */
    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            HyperShapes.INSTANCE.setScreen(new GameScreen(this.camera));
            HyperShapes.INSTANCE.setDifficulty(1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            HyperShapes.INSTANCE.setScreen(new GameScreen(this.camera));
            HyperShapes.INSTANCE.setDifficulty(2);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
            HyperShapes.INSTANCE.setScreen(new GameScreen(this.camera));
            HyperShapes.INSTANCE.setDifficulty(4);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    /**
     * Método que é chamado a cada frame do jogo, chamando o método update como também fazendo as renderizações na tela.
     *
     * @param delta (float) o tempo decorrido desde a última atualização
     */
    @Override
    public void render(float delta) {
        update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(new Texture("patinho.png"), HyperShapes.INSTANCE.getScreenWidth() / 2, HyperShapes.INSTANCE.getScreenHeight() / 2, 64, 64);
        batch.draw(new Texture("patinho.png"), HyperShapes.INSTANCE.getScreenWidth() / 2 - 96, HyperShapes.INSTANCE.getScreenHeight() / 2, 64, 64);
        batch.draw(new Texture("patinho.png"), HyperShapes.INSTANCE.getScreenWidth() / 2 - 80, HyperShapes.INSTANCE.getScreenHeight() / 2 - 200, 128, 128);

        bitmapTitle.draw(batch, "Hyper Shapes", HyperShapes.INSTANCE.getScreenWidth() / 2 - 210, HyperShapes.INSTANCE.getScreenHeight() / 2 - bitmap.getCapHeight());

        bitmap.draw(batch, "Pressione ESC para sair", 10, HyperShapes.INSTANCE.getScreenHeight() - bitmap.getCapHeight());

        bitmap.draw(batch, "Pressione 1 para jogar na dificuldade muito fácil", 10, 30 + bitmap.getCapHeight() * 5);
        bitmap.draw(batch, "Pressione 2 para jogar na dificuldade levemente fácil", 10, 20 + bitmap.getCapHeight() * 3);
        bitmap.draw(batch, "Pressione 3 para jogar na dificuldade fácil", 10, 10 + bitmap.getCapHeight());

        batch.end();
    }

    /**
     * Chamado quando esta tela deveria liberar todos os recursos.
     */
    @Override
    public void dispose() {
        bitmap.dispose();
        bitmapTitle.dispose();
        generator.dispose();
        batch.dispose();
    }
}
