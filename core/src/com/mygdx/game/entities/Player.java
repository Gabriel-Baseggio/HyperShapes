package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.HyperShapes;

import static com.mygdx.game.helper.BodyHelper.createBox;
import static com.mygdx.game.helper.Constants.PPM;
import static com.mygdx.game.helper.ContactType.PLAYER;

/**
 * A classe que representa o jogador no jogo.
 */
public class Player {

    /**
     * O corpo do jogador.
     */
    private Body body;
    /**
     * A posição do jogador como um vetor 2D, com x e y.
     */
    private Vector2 position;
    /**
     * A velocidade do jogador.
     */
    private float speed;
    /**
     * A velocidade na direção vertical.
     */
    private float velY;
    /**
     * A velocidade na direção horizontal.
     */
    private float velX;
    /**
     * A largura do jogador.
     */
    private int width;
    /**
     * A altura do jogador.
     */
    private int height;
    /**
     * A pontuação do jogador.
     */
    private int score;
    /**
     * A textura que representa o jogador.
     */
    private Texture texture;
    /**
     * Uma sinalização indicando se o jogador perdeu o jogo.
     */
    private boolean lost;

    /**
     * Inicializa um novo objeto de jogador com base na tela de jogo fornecida.
     *
     * @param gameScreen A tela do jogo à qual o jogador pertence.
     */
    public Player(GameScreen gameScreen) {
        this.position = randomSpawn();
        this.speed = 1200;
        this.width = 48;
        this.height = width;
        this.texture = new Texture("patinho.png");
        this.body = createBox(position.x, position.y, width, height, false, 10000, gameScreen.getWorld(), PLAYER);

        this.score = 0;

        this.lost = false;

    }

    /**
     * Atualiza a posição e a velocidade do jogador com base nas entradas do teclado.
     *
     * @param deltaTime O tempo decorrido desde a última atualização.
     */
    public void update(float deltaTime) {
        position.x = body.getPosition().x * PPM - (width / 2);
        position.y = body.getPosition().y * PPM - (height / 2);
        velX = 0;
        velY = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            velY = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            velY = -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velX = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velX = -1;
        }
        body.setLinearVelocity(velX * speed * deltaTime, velY * speed * deltaTime);
    }

    /**
     * Renderiza o jogador na tela usando um SpriteBatch.
     *
     * @param batch O SpriteBatch usado para renderização.
     */
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, width, height);
    }

    /**
     * Gera uma posição de spawn aleatória para o jogador.
     *
     * @return Um vetor 2D representando a posição de spawn aleatória.
     */
    public Vector2 randomSpawn() {
        int x, y;

        if (Math.floor(Math.random() * 2 + 1) == 1) {
            x = HyperShapes.INSTANCE.getScreenWidth() / 4;
        } else {
            x = (HyperShapes.INSTANCE.getScreenWidth() / 4) * 3;
        }

        if (Math.floor(Math.random() * 2 + 1) == 1) {
            y = HyperShapes.INSTANCE.getScreenHeight() / 4;
        } else {
            y = (HyperShapes.INSTANCE.getScreenHeight() / 4) * 3;
        }

        return new Vector2(x, y);
    }

    /**
     * Obtém a pontuação atual do jogador.
     *
     * @return A pontuação do jogador.
     */
    public int getScore() {
        return score;
    }

    /**
     * Incrementa a pontuação do jogador por um.
     */
    public void score() {
        this.score++;
    }

    /**
     * Define a pontuação do jogador para um valor específico.
     *
     * @param score A nova pontuação do jogador.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Define que o jogador perdeu e atualiza a pontuação final, considerando a dificuldade.
     */
    public void lost() {
        int temporaryScore = (int) Math.pow(this.score, HyperShapes.INSTANCE.getDifficulty());
        if (HyperShapes.INSTANCE.getHighscore() < temporaryScore) {
            HyperShapes.INSTANCE.setHighscore(temporaryScore);
        }
        this.setScore(temporaryScore);
        this.lost = true;
    }

    /**
     * Verifica se o jogador perdeu o jogo.
     *
     * @return Verdadeiro se o jogador perdeu; falso caso contrário.
     */
    public boolean getLost() {
        return lost;
    }
}
