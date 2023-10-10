package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.GameScreen;

import static com.mygdx.game.helper.BodyHelper.createCircle;
import static com.mygdx.game.helper.Constants.PPM;
import static com.mygdx.game.helper.ContactType.BOSSPROJECTILE;

public class BossProjectile {

    private Body body;
    private float x, y, speed, angX, angY;
    private int diameter;
    private Texture texture;
    private GameScreen gameScreen;
    private boolean destroy;

    public BossProjectile(float x, float y, float angX, float angY, int diameter, String texture, GameScreen gameScreen) {
        this.x = x;
        this.y = y;
        this.angX = angX;
        this.angY = angY;
        this.gameScreen = gameScreen;
        this.speed = 100;
        this.diameter = diameter;
        this.texture = new Texture(texture);
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

    public static void spawnInCircle(float centerX, float centerY, int numProjectiles, float startingAngle, int circleRadius, String texture, GameScreen gameScreen) {
        float angleStep = 360f / numProjectiles;

        for (int i = 0; i < numProjectiles; i++) {
            float x = centerX + circleRadius * MathUtils.cosDeg(startingAngle);
            float y = centerY + circleRadius * MathUtils.sinDeg(startingAngle);

            BossProjectile bossProjectile = new BossProjectile(x, y, MathUtils.cosDeg(startingAngle), MathUtils.sinDeg(startingAngle), 32, texture, gameScreen);
            gameScreen.getBossProjectiles().add(bossProjectile);

            startingAngle += angleStep;
        }
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
