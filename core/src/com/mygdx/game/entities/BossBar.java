package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.GameScreen;
import com.mygdx.game.HyperShapes;

import static com.mygdx.game.helper.BodyHelper.createSensorBox;
import static com.mygdx.game.helper.Constants.PPM;
import static com.mygdx.game.helper.ContactType.BOSSBAR;

public class BossBar {

    private GameScreen gameScreen;
    private Body body;
    private float x, y;
    private float velY, speed;
    private int width, height;
    private boolean destroy;

    public BossBar(float x, float y, float velY, int width, int height, GameScreen gameScreen) {
        this.x = x;
        this.y = y;
        this.speed = 1000;
        this.velY = velY;
        this.width = width;
        this.height = height;
        this.gameScreen = gameScreen;

        this.body = createSensorBox(x, y, width, height, gameScreen.getWorld(), BOSSBAR);

        this.destroy = false;
    }

    public void update(float delta) {
        x = body.getPosition().x * PPM;
        y = body.getPosition().y * PPM;

        if (y > HyperShapes.INSTANCE.getScreenHeight() || y < 0) {
            this.destroy = true;
        }

        body.setLinearVelocity(0, velY * speed * delta);
    }

    public void render(ShapeRenderer shape) {
        shape.setColor(Color.RED);
        shape.rect(x - (width / 2), y - (height / 2), width, height);
    }

    public boolean destroy(){
        return destroy;
    }

    public Body getBody() {
        return body;
    }
}
