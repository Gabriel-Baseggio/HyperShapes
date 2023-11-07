package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.HyperShapes;

import static com.mygdx.game.helper.BodyHelper.createSensorBox;
import static com.mygdx.game.helper.Constants.PPM;
import static com.mygdx.game.helper.ContactType.BOSSBAR;
import static com.mygdx.game.helper.DifficultyHelper.defineDifficulty;

/**
 * Classe que define as informações das barras spawnadas pelo Boss no jogo.
 */
public class BossBar {

    /**
     * Propriedade do tipo Body que representa o corpo físico no mundo do jogo.
     */
    private Body body;
    /**
     * Propriedade do tipo float que representa a posição horizontal do corpo.
     */
    private float x;
    /**
     * Propriedade do tipo float que representa a posição vertical do corpo.
     */
    private float y;
    /**
     * Propriedade do tipo float que representa a direção vertical do corpo.
     */
    private float velY;
    /**
     * Propriedade do tipo float que representa a velocidade do corpo.
     */
    private float speed;
    /**
     * Propriedade do tipo int que representa a largura do corpo.
     */
    private int width;
    /**
     * Propriedade do tipo int que representa a altura do corpo.
     */
    private int height;
    /**
     * Propriedade do tipo boolean que representa se o corpo deve ou não ser destruído.
     */
    private boolean destroy;

    /**
     * Construtor da classe para poder inicializar as propriedades.
     *
     * @param x          (float) Posição horizontal do corpo.
     * @param y          (float) Posição vertical do corpo.
     * @param velY       (float) Direção vertical do corpo.
     * @param width      (int) Largura do corpo.
     * @param height     (int) Altura do corpo.
     * @param gameScreen (GameScreen) Tela onde serão spawnadas as barras.
     */
    public BossBar(float x, float y, float velY, int width, int height, GameScreen gameScreen) {
        this.x = x;
        this.y = y;
        this.speed = defineDifficulty(600);
        this.velY = velY;
        this.width = width;
        this.height = height;

        this.body = createSensorBox(x, y, width, height, gameScreen.getWorld(), BOSSBAR);

        this.destroy = false;
    }

    /**
     * Faz as atualizações das propriedades da barra.
     *
     * @param delta (float) O tempo decorrido desde a última atualização.
     */
    public void update(float delta) {
        x = body.getPosition().x * PPM;
        y = body.getPosition().y * PPM;

        if (y > HyperShapes.INSTANCE.getScreenHeight() + 450 || y < -450) {
            this.destroy = true;
        }

        body.setLinearVelocity(0, velY * speed * delta);
    }

    /**
     * Faz as renderizações de formas no renderizador.
     *
     * @param shape (ShapeRenderer) O ShapeRenderer usado para renderizar as formas.
     */
    public void render(ShapeRenderer shape) {
        shape.setColor(Color.RED);
        shape.rect(x - (width / 2), y - (height / 2), width, height);
    }

    /**
     * Método utilizado para retornar se deve ser destruido.
     *
     * @return destroy (boolean)
     */
    public boolean destroy() {
        return destroy;
    }

    /**
     * Método utilizado para retornar o corpo.
     *
     * @return body (Body)
     */
    public Body getBody() {
        return body;
    }

    /**
     * Método utilizado para retornar a posição horizontal do corpo.
     *
     * @return x (float)
     */
    public float getX() {
        return body.getPosition().x;
    }

    /**
     * Método utilizado para retornar a posição vertical do corpo.
     *
     * @return y (float)
     */
    public float getY() {
        return body.getPosition().y;
    }

    /**
     * Método utilizado para definir se a barra deve ser destruída ou não.
     *
     * @param destroy (boolean)
     */
    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }
}
