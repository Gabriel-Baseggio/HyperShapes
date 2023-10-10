package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.GameScreen;

import static com.mygdx.game.helper.BodyHelper.createBox;
import static com.mygdx.game.helper.Constants.PPM;
import static com.mygdx.game.helper.ContactType.PLAYER;

public class Player {

    private Body body;
    private float x, y, speed, velY, velX;
    private int width, height, score;
    private Texture texture;
    private GameScreen gameScreen;

    public Player(float x, float y, int width, int height, String texture, GameScreen gameScreen) {
        this.x = x;
        this.y = y;
        this.gameScreen = gameScreen;
        this.speed = 600;
        this.width = width;
        this.height = height;
        this.texture = new Texture(texture);
        this.body = createBox(x, y, width, height, false, 10000, gameScreen.getWorld(), PLAYER);

        this.score = 0;

    }

    public void update(float deltaTime) {
        x = body.getPosition().x * PPM - (width / 2);
        y = body.getPosition().y * PPM - (height / 2);
        velX = 0;
        velY = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            velY = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            velY = -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velX = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velX = -1;
        }
        body.setLinearVelocity(velX * speed * deltaTime, velY * speed * deltaTime);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    public int getScore() {
        return score;
    }

    public void score() {
        this.score++;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

}
