package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.HyperShapes;
import com.mygdx.game.helper.AnimationHelper;

import static com.mygdx.game.helper.BodyHelper.createCircle;
import static com.mygdx.game.helper.Constants.PPM;
import static com.mygdx.game.helper.ContactType.BOSS;
import static com.mygdx.game.helper.ContactType.PLAYERPROJECTILE;
import static com.mygdx.game.helper.DifficultyHelper.defineDifficulty;

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
    private boolean secondPattern;

    private float secondStageTime;
    private int barsCounter;
    private int[] prevPattern;
    private int iPattern;


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
        this.secondPattern = false;

        this.prevPattern = new int[2];
        this.iPattern = 0;
        this.secondStageTime = 0;

        this.barsCounter = 0;

    }

    public void update(float deltaTime) {
        x = body.getPosition().x * PPM;
        y = body.getPosition().y * PPM;
        time += deltaTime;

        if (stage == 1 && time >= defineDifficulty(0.75f)) {
            int pattern;
            if (!secondPattern) {
                pattern = 1;
                secondPattern = true;
            } else {
                pattern = 2;
                secondPattern = false;
            }
            spawnProjectilesInCircle(pattern, x, y, 17, diameter - 50, gameScreen);
            time = 0;
        }

        if (stage == 2) {
            body.setTransform(x, y, 0);
            secondStageTime += deltaTime;

            if (barsCounter == 8) {
                gameScreen.getPlayer().score();
                barsCounter = 0;
            }

            if (time >= defineDifficulty(1.5f) && secondStageTime >= 2 && secondStageTime <= defineDifficulty(1.5f)*25+2) {
                int newPattern;
                do {
                    newPattern = (int) Math.floor((Math.random() * 4 + 1));
                } while (newPattern == prevPattern[0] || newPattern == prevPattern[1]);
                prevPattern[iPattern] = newPattern;
                if (iPattern < prevPattern.length-1) {
                    iPattern++;
                } else {
                    iPattern = 0;
                }
                spawnBars(newPattern);
                barsCounter++;
                time = 0;
            }

            if (secondStageTime >= defineDifficulty(1.5f)*25+5) {
                this.setStage(1);
                body.setTransform(HyperShapes.INSTANCE.getScreenWidth()/2/PPM, HyperShapes.INSTANCE.getScreenHeight()/2/PPM, 0);
                barsCounter = 0;
                secondStageTime = 0;
                time = 0;
            }

        }

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

    public static void spawnProjectilesInCircle(int pattern, float centerX, float centerY, int numProjectiles, int circleRadius, GameScreen gameScreen) {
        float angleStep =  360f / numProjectiles;

        float startingAngle =  0;
        if (pattern == 2) {
            startingAngle = 360f / (numProjectiles*2);
        }

        for (int i = 0; i < numProjectiles; i++) {
            float x = centerX + circleRadius * MathUtils.cosDeg(startingAngle);
            float y = centerY + circleRadius * MathUtils.sinDeg(startingAngle);

            gameScreen.getBossProjectiles().add(new BossProjectile(x, y, MathUtils.cosDeg(startingAngle), MathUtils.sinDeg(startingAngle), gameScreen));

            startingAngle += angleStep;
        }
    }

    public void spawnBars(int pattern) {
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

    public int getStage() {
        return this.stage;
    }

    public double getSecondStageTime() {
        return this.secondStageTime;
    }
}
