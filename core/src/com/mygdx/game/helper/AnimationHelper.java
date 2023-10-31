package com.mygdx.game.helper;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.screens.GameScreen;

public class AnimationHelper {

    GameScreen gameScreen;
    Animation<TextureRegion> animationTrack;
    Texture animationSheet;
    int FRAME_COLS;
    int FRAME_ROWS;
    float frameDuration, frame;

    public void updateAnimation() {
        TextureRegion[][] tmp = TextureRegion.split(animationSheet,
                animationSheet.getWidth() / FRAME_COLS,
                animationSheet.getHeight() / FRAME_ROWS);

        TextureRegion[] animationFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];

        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                animationFrames[index++] = tmp[i][j];
            }
        }

        animationTrack = new Animation<TextureRegion>(frameDuration, animationFrames);
    }

    public AnimationHelper(GameScreen gameScreen, Texture animationSheet, int FRAME_COLS, int FRAME_ROWS) {
        this.gameScreen = gameScreen;
        this.animationSheet = animationSheet;
        this.FRAME_COLS = FRAME_COLS;
        this.FRAME_ROWS = FRAME_ROWS;
        this.frameDuration = 1 / 60f;
        this.frame = 0f;
        this.updateAnimation();
    }

    public void render(SpriteBatch batch, float x, float y) {
        TextureRegion currentFrame = animationTrack.getKeyFrame(frame, false);
        frame += frameDuration;

        if (frame >= (frameDuration * 60)) {
            frame = 0;
        }

        batch.draw(currentFrame, x - currentFrame.getRegionWidth() / 2, y - currentFrame.getRegionHeight() / 2);
    }

}
