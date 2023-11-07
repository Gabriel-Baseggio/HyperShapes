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

/**
 * Classe que representa a tela de game over do jogo.
 * Extende a classe ScreenAdapter da biblioteca LibGDX para poder utilizar de seus recursos.
 */
public class GameOverScreen extends ScreenAdapter {
    /**
     * Propriedade do tipo OrthographicCamera que representa a câmera do jogo.
     */
    private OrthographicCamera camera;
    /**
     * Propriedade do tipo SpriteBatch que renderiza os sprites utilizados no jogo.
     */
    private SpriteBatch batch;

    /**
     * Propriedade do tipo GameScreen que representa a tela de jogo que o jogador perdeu.
     */
    private GameScreen gameScreen;

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
     * @param camera (OrthographicCamera)
     * @param gameScreen (GameScreen)
     */
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

    /**
     * Método que será chamado a cada frame do jogo para fazer uma atualização do que é necessário e verificar inputs
     */
    public void update() {

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER) || Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            HyperShapes.INSTANCE.setScreen(new TitleScreen(this.camera));
        }

    }

    /**
     * Método que é chamado a cada frame do jogo, chamando o método update como também fazendo as renderizações na tela.
     * @param delta (float) o tempo decorrido desde a última atualização
     */
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
