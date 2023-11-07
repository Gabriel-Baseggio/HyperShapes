package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.screens.TitleScreen;

/**
 * Classe que representa o jogo por inteiro, fazendo o gerenciamento de mudança de telas.
 * Extende a classe game da biblioteca LibGDX.
 */
public class HyperShapes extends Game {

    /**
     * Propriedade do tipo HyperShapes que representa a instância universal do jogo, sendo utilizada por outras classes para obter valores da classe.
     * Para isso, ele é um objeto estático.
     */
    public static HyperShapes INSTANCE;
    /**
     * Propriedade do tipo int que representa a largura da tela.
     */
    private int screenWidth;
    /**
     * Propriedade do tipo int que representa a altura da tela.
     */
    private int screenHeight;
    /**
     * Propriedade do tipo int que representa o highscore do jogador.
     */
    private int highscore;
    /**
     * Propriedade do tipo int que representa a deficuldade atual do jogo.
     */
    private int difficulty;
    /**
     * Propriedade do tipo OrthographicCamera que representa a camera que o jogo utiliza.
     */
    private OrthographicCamera camera;

    /**
     * Construtor da classe que atribui que a variável INSTANCE é o novo objeto criado.
     */
    public HyperShapes() {
        INSTANCE = this;
    }

    /**
     * Método que inicia o jogo, iniciando as variáves e iniciando em uma ela específica.
     * Método que foi sobreescrito da classe Game.
     */
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

    /**
     * Método que retorna a câmera do jogo.
     * @return camera (OrthographicCamera)
     */
    public OrthographicCamera getCamera() {
        return this.camera;
    }

    /**
     * Método que retorna a largura da tela.
     * @return screenWidth (int)
     */
    public int getScreenWidth() {
        return this.screenWidth;
    }

    /**
     * Método que retorna a altura da tela.
     * @return screenHeight (int)
     */
    public int getScreenHeight() {
        return this.screenHeight;
    }

    /**
     * Método que retorna a dificuldade do jogo.
     * @return difficulty (int)
     */
    public int getDifficulty() {
        return this.difficulty;
    }

    /**
     * Método que atribui um novo valor a variavel difficulty.
     * @param difficulty (int)
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Método que retorna o highscore do jogo.
     * @return highscore (int)
     */
    public int getHighscore() {
        return this.highscore;
    }

    /**
     * Método que atribui um novo valor a variavel highscore.
     * @param highscore (int)
     */
    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

}
