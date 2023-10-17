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
import com.mygdx.game.HyperShapes;
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

        if (stage == 1 && time >= 0.5 && !alreadySpawned) {
            spawnProjectilesInCircle(x, y, 16, 0, diameter / 2, "patinho.png", gameScreen);
            alreadySpawned = true;
        }
        if (stage == 1 && time >= 1 && alreadySpawned) {
            spawnProjectilesInCircle(x, y, 16, 360 / 32, diameter / 2, "patinho.png", gameScreen);
            time = 0;
            alreadySpawned = false;
        }

        if (stage == 2) {
            x = -200;
            y = 0;
            body.setTransform(-200, 0, 0);

            if (time >= 4) {
                spawnBars(generatePatternSequence());
                time = 0;
            }
        }

    }

    private int[] generatePatternSequence() {
        int[] patterns = new int[4];
        int[] positions = new int[4];
        for (int i = 1; i <= patterns.length; i++){
            int pos = (int) Math.floor(Math.random() * 4);
            System.out.println(pos);
            boolean cont = false;
            for (int j = 0; j < positions.length; j++) {
                if (pos == positions[j]) {
                    i--;
                    cont = true;
                }
            }
            if (cont){
                continue;
            }
            patterns[pos] = i;
        }
        return patterns;
    }

    public void render(SpriteBatch batch) {
        if (wasHit) {
            animationHit.render(batch, x, y);
            hitDelay += 1 / 60f;
            if (hitDelay >= 1) {
                wasHit = false;
                hitDelay = 0;
            }
        } else {
            batch.draw(texture, x - (diameter / 2), y - (diameter / 2), diameter, diameter);
        }
    }

    public static void spawnProjectilesInCircle(float centerX, float centerY, int numProjectiles, float startingAngle, int circleRadius, String texture, GameScreen gameScreen) {
        float angleStep = 360f / numProjectiles;

        for (int i = 0; i < numProjectiles; i++) {
            float x = centerX + circleRadius * MathUtils.cosDeg(startingAngle);
            float y = centerY + circleRadius * MathUtils.sinDeg(startingAngle);

            gameScreen.getBossProjectiles().add(new BossProjectile(x, y, MathUtils.cosDeg(startingAngle), MathUtils.sinDeg(startingAngle), gameScreen));

            startingAngle += angleStep;
        }
    }

    public void spawnBars(int[] patterns) {
        for (int i = 0; i < patterns.length; i++) {
            int pattern = patterns[i];
//            System.out.println(pattern);

            int w1 = Math.round(HyperShapes.INSTANCE.getScreenWidth() * 0.6f);
            int w2 = Math.round(HyperShapes.INSTANCE.getScreenWidth() * 0.4f);

            if (pattern > 2) {
                w1 = Math.round(HyperShapes.INSTANCE.getScreenWidth() * 0.52f);
                w2 = Math.round(HyperShapes.INSTANCE.getScreenWidth() * 0.52f);
            }

            switch (pattern) {
                case 1:
                    gameScreen.getBossBars().add(new BossBar(w1 / 2, -80 * 3, 1, w1, 80, gameScreen));
                    gameScreen.getBossBars().add(new BossBar(HyperShapes.INSTANCE.getScreenWidth() - w2 / 2, HyperShapes.INSTANCE.getScreenHeight() + 80 * 3, -1, w2, 80, gameScreen));
                    break;
                case 2:
                    gameScreen.getBossBars().add(new BossBar(w1 / 2, HyperShapes.INSTANCE.getScreenHeight() + 80 * 3, -1, w1, 80, gameScreen));
                    gameScreen.getBossBars().add(new BossBar(HyperShapes.INSTANCE.getScreenWidth() - w2 / 2, -80 * 3, 1, w2, 80, gameScreen));
                    break;
                case 3:
                    gameScreen.getBossBars().add(new BossBar(w1 / 2, -80 * 3, 1, w1, 80, gameScreen));
                    gameScreen.getBossBars().add(new BossBar(w2 / 2, HyperShapes.INSTANCE.getScreenHeight() + 80 * 3, -1, w2, 80, gameScreen));
                    break;
                case 4:
                    gameScreen.getBossBars().add(new BossBar(HyperShapes.INSTANCE.getScreenWidth() - w1 / 2, -80 * 3, 1, w1, 80, gameScreen));
                    gameScreen.getBossBars().add(new BossBar(HyperShapes.INSTANCE.getScreenWidth() - w2 / 2, HyperShapes.INSTANCE.getScreenHeight() + 80 * 3, -1, w2, 80, gameScreen));
                    break;
            }
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
