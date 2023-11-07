package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.HyperShapes;

import static com.mygdx.game.helper.BodyHelper.createCircle;
import static com.mygdx.game.helper.Constants.PPM;
import static com.mygdx.game.helper.ContactType.PLAYERPROJECTILE;

/**
 * Classe que representa o projétil disparado pelo jogador no jogo.
 */
public class PlayerProjectile extends InputAdapter {

    /**
     * O corpo associado do projétil.
     */
    private Body body;
    /**
     * A posição do projétil como um vetor 2D, com x e y.
     */
    private Vector2 position;
    /**
     * A velocidade do projétil.
     */
    private float speed;

    /**
     * O diâmetro do projétil.
     */
    private int diameter;

    /**
     * A tela do jogo à qual o projétil pertence.
     */
    private GameScreen gameScreen;

    /**
     * Uma sinalização indicando se o jogador pode disparar o projétil.
     */
    private boolean canShoot;

    /**
     * Uma sinalização indicando se a linha de direção do tiro deve ser desenhada.
     */
    private boolean drawLine;

    /**
     * Uma sinalização indicando se o jogador tocou na tela (touch down).
     */
    private boolean touchedDown;

    /**
     * O atraso entre os disparos do projétil.
     */
    private float shootDelay;

    /**
     * O tempo decorrido desde a última atualização.
     */
    private float deltaTime;

    /**
     * Inicializa um novo objeto PlayerProjectile com base na tela de jogo fornecida.
     *
     * @param gameScreen A tela do jogo à qual o projétil pertence.
     */
    public PlayerProjectile(GameScreen gameScreen) {
        this.position = randomSpawn();
        this.gameScreen = gameScreen;
        this.speed = 1000000;
        this.diameter = 28;
        this.body = createCircle(position.x, position.y, diameter, false, 1, gameScreen.getWorld(), PLAYERPROJECTILE, new FixtureDef());

        this.canShoot = false;
        this.shootDelay = 3f;
        this.deltaTime = 0;
        this.drawLine = false;
        this.touchedDown = false;

        Gdx.input.setInputProcessor(this);
    }

    /**
     * Atualiza a posição, o atraso entre disparos e outras propriedades do projétil.
     *
     * @param deltaTime O tempo decorrido desde a última atualização.
     */
    public void update(float deltaTime) {
        this.deltaTime = deltaTime;
        position.x = body.getPosition().x * PPM - diameter;
        position.y = body.getPosition().y * PPM - diameter;

        shootDelay += deltaTime;

        if (!canShoot || shootDelay < 3) {
            drawLine = false;
            gameScreen.setSlowEffect(false);
        }

    }

    /**
     * Renderiza a linha de direção do tiro (se ativada) usando um ShapeRenderer.
     *
     * @param shape O ShapeRenderer usado para renderizar a linha de direção.
     */
    public void renderLine(ShapeRenderer shape) {
        if (drawLine) {
            Vector2 p1 = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            Vector2 p2 = new Vector2(position.x + diameter, position.y + diameter);
            Vector2 mouseToP2 = p2.cpy().sub(p1);
            Vector2 extendedP1 = p1.cpy().sub(mouseToP2.nor().scl(10000f));

            shape.setColor(Color.WHITE);
            shape.rectLine(extendedP1, p2, 2);
        }
    }

    /**
     * Renderiza o projétil na tela usando um SpriteBatch e a textura fornecida.
     *
     * @param batch   O SpriteBatch usado para renderização.
     * @param textura A textura usada para representar o projétil.
     */
    public void render(SpriteBatch batch, Texture textura) {
        batch.draw(textura, position.x + (diameter / 2), position.y + (diameter / 2), diameter, diameter);
    }

    /**
     * Gera uma posição de spawn aleatória para o projétil.
     *
     * @return Um vetor 2D representando a posição de spawn aleatória.
     */
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

    /**
     * Define se o jogador pode disparar o projétil.
     *
     * @param canShoot Verdadeiro se o jogador pode disparar; falso caso contrário.
     */
    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }

    /**
     * Verifica se o jogador pode disparar o projétil.
     *
     * @return Verdadeiro se o jogador pode disparar; falso caso contrário.
     */
    public boolean getCanShoot() {
        return canShoot;
    }

    /**
     * Aplica uma força para direcionar o projétil na direção do mouse.
     *
     * @param mouseX    A coordenada X da posição do mouse.
     * @param mouseY    A coordenada Y da posição do mouse.
     * @param deltaTime O tempo decorrido desde a última atualização.
     */
    public void applyForceToFollowMouse(float mouseX, float mouseY, float deltaTime) {
        if (canShoot && shootDelay >= 3) {
            float angle = MathUtils.atan2(mouseY - body.getPosition().y, mouseX - body.getPosition().x);

            float forceX = MathUtils.cos(angle) * speed * deltaTime;
            float forceY = MathUtils.sin(angle) * speed * deltaTime;

            shootDelay = 0;
            body.applyForceToCenter(forceX, forceY, true);
        } else {
            canShoot = false;
        }

    }

    /**
     * Manipula o evento de toque (touch down) na tela.
     *
     * @param screenX A coordenada X do toque na tela.
     * @param screenY A coordenada Y do toque na tela.
     * @param pointer O ponteiro associado ao evento de toque.
     * @param button  O botão do mouse associado ao evento de toque.
     * @return Verdadeiro se o evento de toque foi manipulado com sucesso; falso caso contrário.
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (canShoot && shootDelay >= 3) {
            gameScreen.setSlowEffect(true);
            touchedDown = true;
            drawLine = true;
            body.setLinearVelocity(0, 0);
        }
        return true;
    }

    /**
     * Manipula o evento de liberação do toque na tela.
     *
     * @param screenX A coordenada X do toque liberado na tela.
     * @param screenY A coordenada Y do toque liberado na tela.
     * @param pointer O ponteiro associado ao evento de liberação de toque.
     * @param button  O botão do mouse associado ao evento de liberação de toque.
     * @return Verdadeiro se o evento de liberação de toque foi manipulado com sucesso; falso caso contrário.
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (canShoot && shootDelay >= 3 && touchedDown) {
            touchedDown = false;
            gameScreen.setSlowEffect(false);
            gameScreen.getTimeHelper().setTimeScale(1f);
            body.setLinearVelocity(0, 0);
            applyForceToFollowMouse(screenX / PPM, (Gdx.graphics.getHeight() - screenY) / PPM, deltaTime);
        }
        drawLine = false;
        return true;
    }

    /**
     * Manipula o evento de arrastar (drag) na tela.
     *
     * @param screenX A coordenada X do arrasto na tela.
     * @param screenY A coordenada Y do arrasto na tela.
     * @param pointer O ponteiro associado ao evento de arrastar.
     * @return Verdadeiro se o evento de arrastar na tela foi manipulado com sucesso; falso caso contrário.
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (canShoot && shootDelay >= 3) {
            touchedDown = true;
            body.setLinearVelocity(0, 0);
            gameScreen.setSlowEffect(true);
            drawLine = true;
        }
        return true;
    }

    /**
     * Obtém o atraso entre os disparos do projétil.
     *
     * @return O atraso entre os disparos do projétil.
     */
    public float getDelayShoot() {
        return shootDelay;
    }

}