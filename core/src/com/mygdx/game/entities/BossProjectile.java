package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.screens.GameScreen;

import static com.mygdx.game.helper.BodyHelper.createCircle;
import static com.mygdx.game.helper.Constants.PPM;
import static com.mygdx.game.helper.ContactType.BOSSPROJECTILE;
import static com.mygdx.game.helper.DifficultyHelper.defineDifficulty;

public class BossProjectile {

    private Body body;
    private float x, y, speed, angX, angY;
    private int diameter;
    private GameScreen gameScreen;
    private boolean destroy;

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

    public void update(float deltaTime) {
        x = body.getPosition().x * PPM - diameter;
        y = body.getPosition().y * PPM - diameter;

        body.setLinearVelocity(angX * speed * deltaTime, angY * speed * deltaTime);
    }

    public void render(ShapeRenderer s) {
        s.set(ShapeRenderer.ShapeType.Filled);
        s.setColor(Color.GREEN);
        s.ellipse(x + diameter/2, y + diameter/2, diameter, diameter);
    }

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }

    public boolean destroy() {
        return destroy;
    }

    public float getX() {
        return body.getPosition().x;
    }

    public float getY() {
        return body.getPosition().y;
    }

    public Body getBody() {
        return this.body;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

}
