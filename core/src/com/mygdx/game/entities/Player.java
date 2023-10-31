package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.HyperShapes;

import static com.mygdx.game.helper.BodyHelper.createBox;
import static com.mygdx.game.helper.Constants.PPM;
import static com.mygdx.game.helper.ContactType.PLAYER;

public class Player {

    private Body body;
    private Vector2 position;
    private float speed, velY, velX;
    private int width, height, score;
    private Texture texture;
    private boolean lost;

    public Player(GameScreen gameScreen) {
        this.position = randomSpawn();
        this.speed = 1200;
        this.width = 48;
        this.height = width;
        this.texture = new Texture("patinho.png");
        this.body = createBox(position.x, position.y, width, height, false, 10000, gameScreen.getWorld(), PLAYER);

        this.score = 0;

        this.lost = false;

    }

    public void update(float deltaTime) {
        position.x = body.getPosition().x * PPM - (width / 2);
        position.y = body.getPosition().y * PPM - (height / 2);
        velX = 0;
        velY = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            velY = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            velY = -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velX = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velX = -1;
        }
        body.setLinearVelocity(velX * speed * deltaTime, velY * speed * deltaTime);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, width, height);
    }

    public Vector2 randomSpawn() {
        int x, y;

        if (Math.floor(Math.random() * 2 + 1) == 1) {
            x = HyperShapes.INSTANCE.getScreenWidth() / 4;
        } else {
            x = (HyperShapes.INSTANCE.getScreenWidth() / 4) * 3;
        }

        if (Math.floor(Math.random() * 2 + 1) == 1) {
            y = HyperShapes.INSTANCE.getScreenHeight() / 4;
        } else {
            y = (HyperShapes.INSTANCE.getScreenHeight() / 4) * 3;
        }

        return new Vector2(x, y);
    }

    public int getScore() {
        return score;
    }

    public void score() {
        this.score++;
    }

    public void setScore(int score) { this.score =score; }

    public void lost() {
        int temporaryScore = (int) Math.pow(this.score, HyperShapes.INSTANCE.getDifficulty());
        if (HyperShapes.INSTANCE.getHighscore() < temporaryScore) {
            HyperShapes.INSTANCE.setHighscore(temporaryScore);
        }
        this.setScore(temporaryScore);
        this.lost = true;
    }

    public boolean getLost() {
        return lost;
    }
}
