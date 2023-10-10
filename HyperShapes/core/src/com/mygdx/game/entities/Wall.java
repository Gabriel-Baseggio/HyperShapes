package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.GameScreen;
import com.mygdx.game.HyperShapes;

import static com.mygdx.game.helper.BodyHelper.createBox;
import static com.mygdx.game.helper.ContactType.WALL;

public class Wall {

    private Body body;
    private float x, y;
    private int width, height;
    private Texture texture;

    public Wall(float x, float y, int width, int height, String texture, GameScreen gameScreen) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.texture = new Texture(texture);
        this.body = createBox(x, y, width, height, true, 0, gameScreen.getWorld(), WALL);
    }

    public void render(ShapeRenderer shape) {
        shape.setColor(Color.ORANGE);
        shape.rect(x - (width / 2), y - (height / 2), width, height);
    }

}
