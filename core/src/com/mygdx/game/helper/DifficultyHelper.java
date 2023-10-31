package com.mygdx.game.helper;

import com.mygdx.game.HyperShapes;

public abstract class DifficultyHelper {

    // Para aumentar o valor com base no valor inicial
    public static int defineDifficulty(int value) {
        int difficulty = HyperShapes.INSTANCE.getDifficulty();

        return (value + (int) ((difficulty - 1) * Math.pow(("" + value).length(), ("" + value).length()) * Integer.parseInt(String.valueOf(("" + value).charAt(0)))));
    }

    // Para diminuir o valor com base no valor inicial
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
