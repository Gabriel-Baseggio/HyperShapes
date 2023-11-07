package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.screens.GameScreen;

import static com.mygdx.game.helper.BodyHelper.createCircle;
import static com.mygdx.game.helper.Constants.PPM;
import static com.mygdx.game.helper.ContactType.BOSSPROJECTILE;
import static com.mygdx.game.helper.DifficultyHelper.defineDifficulty;

/**
 * Uma classe que representa um projétil lançado pelo chefe em um jogo.
 */
public class BossProjectile {

    /**
     * O corpo associado ao projétil.
     */
    private Body body;
    /**
     * A coordenada X da posição do projétil.
     */
    private float x;
    /**
     * A coordenada Y da posição do projétil.
     */
    private float y;
    /**
     * A velocidade do projétil.
     */
    private float speed;
    /**
     * O ângulo de direção X do projétil.
     */
    private float angX;
    /**
     * O ângulo de direção Y do projétil.
     */
    private float angY;
    /**
     * O diâmetro do projétil.
     */
    private int diameter;
    /**
     * A tela do jogo à qual o projétil pertence.
     */
    private GameScreen gameScreen;
    /**
     * Uma sinalização indicando se o projétil deve ser destruído.
     */
    private boolean destroy;

    /**
     * Inicializa um novo objeto BossProjectile com os parâmetros especificados.
     *
     * @param x          A coordenada X da posição inicial do projétil.
     * @param y          A coordenada Y da posição inicial do projétil.
     * @param angX       O ângulo de direção X do projétil.
     * @param angY       O ângulo de direção Y do projétil.
     * @param gameScreen A GameScreen à qual o projétil pertence.
     */
    public BossProjectile(float x, float y, float angX, float angY, GameScreen gameScreen) {
        this.x = x;
        this.y = y;
        this.angX = angX;
        this.angY = angY;
        this.gameScreen = gameScreen;
        this.speed = defineDifficulty(800);
        this.diameter = 24;
        this.body = createCircle(x, y, diameter, false, 10000, gameScreen.getWorld(), BOSSPROJECTILE, null);

        this.destroy = false;

    }

    /**
     * Atualiza a posição do projétil com base em sua velocidade e direção.
     *
     * @param deltaTime O tempo decorrido desde a última atualização.
     */
    public void update(float deltaTime) {
        x = body.getPosition().x * PPM - diameter;
        y = body.getPosition().y * PPM - diameter;

        body.setLinearVelocity(angX * speed * deltaTime, angY * speed * deltaTime);
    }

    /**
     * Renderiza o projétil usando um ShapeRenderer.
     *
     * @param shape O ShapeRenderer usado para renderização.
     */
    public void render(ShapeRenderer shape) {
        shape.set(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.GREEN);
        shape.ellipse(x + diameter / 2, y + diameter / 2, diameter, diameter);
    }

    /**
     * Método utilizado para definir se o projétil deve ser destruído ou não.
     *
     * @param destroy (boolean)
     */
    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
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
     * Método utilizado para retornar o corpo.
     *
     * @return body (Body)
     */
    public Body getBody() {
        return this.body;
    }

}
