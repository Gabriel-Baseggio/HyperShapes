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

/**
 * Classe que define as informações e ações do Boss no jogo.
 */
public class Boss {

    /**
     * Propriedade do tipo Body que representa o corpo físico no mundo do jogo.
     */
    private Body body;
    /**
     * Propriedade do tipo float que representa a posição horizontal do corpo.
     */
    private float x;
    /**
     * Propriedade do tipo float que representa a posição vertical do corpo.
     */
    private float y;
    /**
     * Propriedade do tipo int que representa o diâmetro do corpo.
     */
    private int diameter;
    /**
     * Propriedade do tipo AnimationHelper para fazer uma animação de acerto.
     */
    private AnimationHelper animationHit;

    /**
     * Propriedade do tipo Texture que define a imagem a ser desenhada na tela do jogo.
     */
    private Texture texture;
    /**
     * Propriedade do tipo GameScreen que representa a tela de jogo, permitindo que seja utilizada seus métodos.
     */
    private GameScreen gameScreen;

    /**
     * Propriedade do tipo boolean que define se o Boss foi acertado ou não.
     */
    private boolean wasHit;
    /**
     * Propriedade do tipo float que define o tempo desde que o Boss foi acertado, para que ele não possa ser acertado.
     */
    private float hitDelay;
    /**
     * Propriedade do tipo int que define estágio que o Boss está.
     */
    private int stage;
    /**
     * Propriedade do tipo float que define o tempo decorrido com o Boss no jogo.
     */
    private float time;
    /**
     * Propriedade do tipo boolean que define qual padrão de ataque será usado pelo Boss no estágio 1.
     */
    private boolean secondPattern;

    /**
     * Propriedade do tipo float que define o tempo decorrido no estágio 2 do Boss.
     */
    private float secondStageTime;
    /**
     * Propriedade do tipo int que define quantas barras já foram spawnadas.
     */
    private int barsCounter;
    /**
     * Propriedade do tipo int[] que define os padrões de barras já spawnados anteriormente.
     */
    private int[] prevPattern;
    /**
     * Propriedade do tipo int que define o índice que deve ser alterado no vetor de padrões anteriores.
     */
    private int iPattern;


    /**
     * Construtor da classe para poder inicializar as propriedades do Boss e usar os métodos iniciais.
     *
     * @param x (float) Posição horizontal do corpo do Boss.
     * @param y (float) Posição vertical do corpo do Boss.
     * @param diameter (int) O diâmetro do corpo do Boss.
     * @param texture (String) O nome do arquivo da textura do corpo do Boss.
     * @param gameScreen (GameScreen) A tela onde o Boss será spawnado.
     */
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

    /**
     * Faz as atualizações das propriedades do Boss e as definições de acordo com estado e tempo de jogo.
     *
     * @param deltaTime (float) O tempo decorrido desde a última atualização.
     */
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
            spawnProjectilesInCircle(pattern, 17, diameter - 50);
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

    /**
     * Faz as renderizações de imagens no batch, dependendo se foi acertado ou não.
     *
     * @param batch (SpriteBatch) O SpriteBatch usado para renderizar as imagens.
     */
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

    /**
     * Spawna projéteis em um padrão circular ao redor do Boss.
     *
     * @param pattern (int) O padrão que deve ser spawnado de projéteis.
     * @param numProjectiles (int) O número de projéteis a serem spawnados.
     * @param circleRadius (int) O raio do padrão circular.
     */
    public void spawnProjectilesInCircle(int pattern, int numProjectiles, int circleRadius) {
        float angleStep =  360f / numProjectiles;

        float startingAngle =  0;
        if (pattern == 2) {
            startingAngle = 360f / (numProjectiles*2);
        }

        for (int i = 0; i < numProjectiles; i++) {
            float x = this.x + circleRadius * MathUtils.cosDeg(startingAngle);
            float y = this.y + circleRadius * MathUtils.sinDeg(startingAngle);

            this.gameScreen.getBossProjectiles().add(new BossProjectile(x, y, MathUtils.cosDeg(startingAngle), MathUtils.sinDeg(startingAngle), this.gameScreen));

            startingAngle += angleStep;
        }
    }

    /**
     * Spawna barras de acordo com os padrões da segunda fase do Boss.
     *
     * @param pattern (int) O padrão de barras que deve ser spawnado.
     */
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

    /**
     * Define a propriedade que indica se o Boss foi atingido.
     *
     * @param wasHit (boolean)
     */
    public void setWasHit(boolean wasHit) {
        this.wasHit = wasHit;
    }

    /**
     * Método utilizado para retornar se o Boss foi atingido.
     *
     * @return wasHit (boolean)
     */
    public boolean getWasHit() {
        return wasHit;
    }

    /**
     * Método utilizado para retornar o delay para atingir o Boss novamente.
     *
     * @return hitDelay (double)
     */
    public double getHitDelay() {
        return hitDelay;
    }

    /**
     * Define a propriedade que indica o estágio do Boss.
     *
     * @param stage (int)
     */
    public void setStage(int stage) {
        this.stage = stage;
    }

    /**
     * Método utilizado para retornar o corpo do Boss.
     *
     * @return body (Body)
     */
    public Body getBody() {
        return body;
    }

    /**
     * Método utilizado para retornar o estágio do Boss.
     *
     * @return stage (int)
     */
    public int getStage() {
        return this.stage;
    }

    /**
     * Método utilizado para retornar o tempo decorrido no segundo estágio do Boss.
     *
     * @return secondStageTime (double)
     */
    public double getSecondStageTime() {
        return this.secondStageTime;
    }
}
