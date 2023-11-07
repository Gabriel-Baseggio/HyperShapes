package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.screens.GameScreen;

import static com.mygdx.game.helper.BodyHelper.createBox;
import static com.mygdx.game.helper.ContactType.WALL;

/**
 * Classe que representa uma parede no jogo.
 */
public class Wall {

    /**
     * O corpo associado à parede.
     */
    private Body body;

    /**
     * A coordenada X da posição da parede.
     */
    private float x;
    /**
     * A coordenada Y da posição da parede.
     */
    private float y;

    /**
     * A largura da parede.
     */
    private int width;
    /**
     * A altura da parede.
     */
    private int height;

    /**
     * A textura que representa a parede.
     */
    private Texture texture;

    /**
     * Inicializa um novo objeto Wall com os parâmetros especificados.
     *
     * @param x          A coordenada X da posição da parede.
     * @param y          A coordenada Y da posição da parede.
     * @param width      A largura da parede.
     * @param height     A altura da parede.
     * @param texture    O nome do arquivo de textura para a parede.
     * @param gameScreen A tela do jogo à qual a parede pertence.
     */
    public Wall(float x, float y, int width, int height, String texture, GameScreen gameScreen) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.texture = new Texture(texture);
        this.body = createBox(x, y, width, height, true, 0, gameScreen.getWorld(), WALL);
    }

    /**
     * Renderiza a parede usando um ShapeRenderer para representá-la como um retângulo.
     *
     * @param shape O ShapeRenderer usado para renderização.
     */
    public void render(ShapeRenderer shape) {
        shape.setColor(Color.ORANGE);
        shape.rect(x - (width / 2), y - (height / 2), width, height);
    }

}
