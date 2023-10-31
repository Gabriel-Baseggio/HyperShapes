package com.mygdx.game.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;

public class FontHelper {
    private static FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
    private static FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

    public static void write(SpriteBatch batch, String text, Vector2 position, int size) {
        parameter.size = size;
        parameter.borderWidth = 1;
        parameter.color = Color.WHITE;
        BitmapFont bitmap = generator.generateFont(parameter);

        bitmap.draw(batch, text, position.x, position.y);
    }
}