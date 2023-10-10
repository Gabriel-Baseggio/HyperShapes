package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.GameScreen;
import com.mygdx.game.helper.AnimationHelper;

import static com.mygdx.game.helper.BodyHelper.createCircle;
import static com.mygdx.game.helper.Constants.PPM;
import static com.mygdx.game.helper.ContactType.BOSS;
import static com.mygdx.game.helper.ContactType.PLAYERPROJECTILE;

public class Boss {

    private Body body;
    private float x, y;
    private int diameter;
    private AnimationHelper animationHit;

    private Texture texture;
    private GameScreen gameScreen;

    private boolean wasHit;
    private float hitDelay;
    private int stage;
    private float time;
    private boolean alreadySpawned;


    public Boss(float x, float y, int diameter, String texture, GameScreen gameScreen) {
        this.x = x;
        this.y = y;
        this.gameScreen = gameScreen;
        this.diameter = diameter;
        this.texture = new Texture(texture);
        this.body = createCircle(x, y, diameter, true, 1000000, gameScreen.getWorld(), BOSS, null);

        this.animationHit = new AnimationHelper(gameScreen, new Texture("BolinhaPiscando.png"), 7, 2);
        this.wasHit = false;
        this.hitDelay = 0;

        this.stage = 1;
        this.time = 0;
        this.alreadySpawned = false;

    }

    public void update(float deltaTime) {
        time += deltaTime;

        if (stage == 1 && time >= 2 && !alreadySpawned) {
            BossProjectile.spawnInCircle(x, y, 10, 0, diameter / 2, "patinho.png", gameScreen);
            alreadySpawned = true;
        }
        if (stage == 1 && time >= 4 && alreadySpawned) {
            BossProjectile.spawnInCircle(x, y, 10, 360/20, diameter / 2, "patinho.png", gameScreen);
            time = 0;
            alreadySpawned = false;
        }

        if (stage == 2) {
            // Lógica para as "barras" do boss de undertale (novo objeto parecido com o wall, porém se move em uma direção)
        }

    }

    public void render(SpriteBatch batch) {
        if (wasHit) {
            animationHit.render(batch, x, y);
            hitDelay += 1/60f;
            if (hitDelay >= 1) {
                wasHit = false;
                hitDelay = 0;
            }
        } else {
            batch.draw(texture, x - (diameter / 2), y - (diameter / 2), diameter, diameter);
        }
    }

    public void setWasHit(boolean wasHit) {
        this.wasHit = wasHit;
    }

    public boolean getWasHit() {
        return wasHit;
    }

    public double getHitDelay() {
        return hitDelay;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public Body getBody() {
        return body;
    }
}
