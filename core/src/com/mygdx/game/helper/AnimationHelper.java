package com.mygdx.game.helper;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.screens.GameScreen;

/**
 * Classe auxiliar para criar animações a partir de uma região de texturas.
 */
public class AnimationHelper {

    /**
     * A tela do jogo à qual a animação pertence.
     */
    private GameScreen gameScreen;

    /**
     * A animação de sprites.
     */
    private Animation<TextureRegion> animationTrack;

    /**
     * A sprite sheet que contém os frames da animação.
     */
    private Texture animationSheet;

    /**
     * O número de colunas (frames por linha) na sprite sheet.
     */
    private int FRAME_COLS;

    /**
     * O número de linhas (frames por coluna) na sprite sheet.
     */
    private int FRAME_ROWS;

    /**
     * A duração de um frame da animação em segundos.
     */
    private float frameDuration;

    /**
     * O frame atual da animação.
     */
    private float frame;

    /**
     * Inicializa uma nova instância de AnimationHelper com os parâmetros especificados.
     *
     * @param gameScreen     A tela do jogo à qual a animação pertence.
     * @param animationSheet A sprite sheet que contém os frames da animação.
     * @param FRAME_COLS     O número de colunas na sprite sheet.
     * @param FRAME_ROWS     O número de linhas na sprite sheet.
     */
    public AnimationHelper(GameScreen gameScreen, Texture animationSheet, int FRAME_COLS, int FRAME_ROWS) {
        this.gameScreen = gameScreen;
        this.animationSheet = animationSheet;
        this.FRAME_COLS = FRAME_COLS;
        this.FRAME_ROWS = FRAME_ROWS;
        this.frameDuration = 1 / 60f;
        this.frame = 0f;
        this.updateAnimation();
    }

    /**
     * Atualiza a animação com base nos frames da sprite sheet.
     */
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

        animationTrack = new Animation<>(frameDuration, animationFrames);
    }

    /**
     * Renderiza a animação na tela.
     *
     * @param batch O SpriteBatch usado para renderização.
     * @param x     A coordenada X onde a animação será renderizada.
     * @param y     A coordenada Y onde a animação será renderizada.
     */
    public void render(SpriteBatch batch, float x, float y) {
        TextureRegion currentFrame = animationTrack.getKeyFrame(frame, false);
        frame += frameDuration;

        if (frame >= (frameDuration * 60)) {
            frame = 0;
        }

        batch.draw(currentFrame, x - currentFrame.getRegionWidth() / 2, y - currentFrame.getRegionHeight() / 2);
    }

}
