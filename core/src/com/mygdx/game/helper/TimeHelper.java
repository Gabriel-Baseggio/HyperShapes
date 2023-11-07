package com.mygdx.game.helper;

import com.badlogic.gdx.Gdx;

/**
 * Classe de auxílio para realizar o manuseio no tempo do mundo criado para o jogo.
 */
public class TimeHelper {
    /**
     * Propriedade do tipo float que representa a escala de tempo no mundo, sendo utilizada para multiplicar. Um número menor que 1 representa um slow motion.
     */
    private float timeScale = 1.0f;

    /**
     * Método que é utilizado para alcançar o encapsulamento da propriedade timeScale, mudando o valor dela conforme o parâmetro.
     * @param scale (float)
     */
    public void setTimeScale(float scale) {
        this.timeScale = scale;
    }

    /**
     * Método que é utilizado para retornar o deltaTime vezes o timeScale, permitindo que seja possível criar uma sensação de slow motion.
     * @return deltaTime (float)
     */
    public float getDeltaTime() {
        return Gdx.graphics.getDeltaTime() * timeScale;
    }
}
