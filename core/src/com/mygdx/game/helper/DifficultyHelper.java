package com.mygdx.game.helper;

import com.mygdx.game.HyperShapes;

/**
 * Classe que é utilizada pra realizar cálculos, retornando valores para variáves como velocidade e tempo de acordo com a dificuldade escolhida.
 */
public class DifficultyHelper {

    // Para aumentar o valor com base no valor inicial

    /**
     * Método que calcula um novo valor a uma variável, fazendo ela crescer de acordo com a dificuldade, sendo utilizada para números inteiros
     * @param value (int) representa o valor base de uma variável
     * @return um novo valor que condiz com a dificuldade escolhida
     */
    public static int defineDifficulty(int value) {
        int difficulty = HyperShapes.INSTANCE.getDifficulty();

        return (value + (int) ((difficulty - 1) * Math.pow(("" + value).length(), ("" + value).length()) * Integer.parseInt(String.valueOf(("" + value).charAt(0)))));
    }

    // Para diminuir o valor com base no valor inicial

    /**
     * Parecido com o defineDifficulty que recebe inteiros, este calcula um novo valor, porém ele decrementa.
     * Utilizado principalmete para calcular intervalos
     * @param value (float) representa o valor base de uma variável
     * @return um novo valor que condiz com a dificuldade escolhida
     */
    public static float defineDifficulty(float value){
        int difficulty = HyperShapes.INSTANCE.getDifficulty();

        float finalResult;
        if (value < 1) {
            finalResult = (float) (Math.pow(value, difficulty));
        } else {
            finalResult = (float) (Math.pow(value, 1/difficulty));
        }

        return finalResult;
    }

}
