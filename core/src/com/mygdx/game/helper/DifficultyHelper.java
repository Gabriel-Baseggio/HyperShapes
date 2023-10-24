package com.mygdx.game.helper;

import com.mygdx.game.HyperShapes;

public class DifficultyHelper {

    public static int defineDifficulty(int value) {
        int difficulty = HyperShapes.INSTANCE.getDifficulty();

        return (value + (int) (difficulty * Math.pow(("" + value).length(), ("" + value).length()) * Integer.parseInt(String.valueOf(("" + value).charAt(0)))));
    }

    public static float defineDifficulty(float value){
        int difficulty = HyperShapes.INSTANCE.getDifficulty();

        return (float) (Math.pow(value, difficulty));
    }

}
