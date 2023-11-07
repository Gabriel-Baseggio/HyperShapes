package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.HyperShapes;
import com.mygdx.game.entities.*;
import com.mygdx.game.helper.GameContactListener;
import com.mygdx.game.helper.TimeHelper;

import java.util.ArrayList;
import java.util.Iterator;

import static com.mygdx.game.helper.Constants.PPM;

/**
 * Classe que representa a tela do jogo.
 * Extende a classe ScreenAdapter da biblioteca LibGDX para poder utilizar de seus recursos.
 */
public class GameScreen extends ScreenAdapter {
    /**
     * Propriedade do tipo OrthographicCamera que representa a câmera do jogo.
     */
    private OrthographicCamera camera;
    /**
     * Propriedade do tipo SpriteBatch que renderiza os sprites utilizados no jogo.
     */
    private SpriteBatch batch;
    /**
     * Propriedade do tipo ShapeRenderer que renderiza formas geométricas na tela.
     */
    private ShapeRenderer shapeRenderer;
    /**
     * Propriedade do tipo World que representa o mundo onde os corpos estarão.
     */
    private World world;
    /**
     * Propriedade do tipo GameContactListener que é utilizada para tratar colisões.
     */
    private GameContactListener gameContactListener;

    /**
     * Propriedade do tipo Player que representa o jogador.
     */
    private Player player;
    /**
     * Propriedade do tipo PlayerProjectile que representa o projétil do jogador.
     */
    private PlayerProjectile playerProjectile;

    /**
     * Propriedade do tipo Boss que representar o chefe do jogo.
     */
    private Boss boss;
    /**
     * Propriedade que é um lista do tipo BossProjectile que armazena todos os projéteis do chefe.
     */
    private ArrayList<BossProjectile> bossProjectiles;
    /**
     * Propriedade que é um lista do tipo BossBar que armazena todos as barras da segunda fase do chefe.
     */
    private ArrayList<BossBar> bossBars;

    /**
     * Propriedade do tipo Wall que representa a parede superior da sala.
     */
    private Wall wallTop;
    /**
     * Propriedade do tipo Wall que representa a parede do canto direito da sala.
     */
    private Wall wallRight;
    /**
     * Propriedade do tipo Wall que representa a parede inferior da sala.
     */
    private Wall wallBottom;
    /**
     * Propriedade do tipo Wall que representa a parede do canto esquerdo da sala.
     */
    private Wall wallLeft;

    /**
     * Propriedade do tipo FreeTypeFontGenerator que auxilia na criação de fontes.
     */
    private FreeTypeFontGenerator generator;
    /**
     * Propriedade do tipo FreeTypeFontGenerator.FreeTypeFontParameter que auxilia na criação de fontes.
     */
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    /**
     * Propriedade do tipo BitmapFont que auxilia na criação de fontes.
     */
    private BitmapFont bitmap;

    /**
     * Propriedade do tipo boolean que indica se o efeito de slow motion está ativo.
     */
    private boolean slowEffect;
    /**
     * Propriedade do tipo TimeHelper que é utilizada para auxiliar no cálculo de tempo real passado no mundo.
     */
    private TimeHelper timeHelper;

    /**
     * Construtor da classe que atribui os valores inicias para suas variáveis.
     *
     * @param camera (OrthographicCamera)
     */
    public GameScreen(OrthographicCamera camera) {
        this.camera = camera;
        this.camera.position.set(new Vector3(HyperShapes.INSTANCE.getScreenWidth() / 2, HyperShapes.INSTANCE.getScreenHeight() / 2, 0));
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0, 0), false);
        this.gameContactListener = new GameContactListener(this);
        this.world.setContactListener(this.gameContactListener);

        this.player = new Player(this);
        this.playerProjectile = new PlayerProjectile(this);

        this.slowEffect = false;
        this.timeHelper = new TimeHelper();

        this.wallTop = new Wall(HyperShapes.INSTANCE.getScreenWidth() / 2, HyperShapes.INSTANCE.getScreenHeight() - 16, HyperShapes.INSTANCE.getScreenWidth(), 32, "patinho.png", this);
        this.wallRight = new Wall(HyperShapes.INSTANCE.getScreenWidth() - 16, HyperShapes.INSTANCE.getScreenHeight() / 2, 32, HyperShapes.INSTANCE.getScreenHeight(), "patinho.png", this);
        this.wallBottom = new Wall(HyperShapes.INSTANCE.getScreenWidth() / 2, 16, HyperShapes.INSTANCE.getScreenWidth(), 32, "patinho.png", this);
        this.wallLeft = new Wall(16, HyperShapes.INSTANCE.getScreenHeight() / 2, 32, HyperShapes.INSTANCE.getScreenHeight(), "patinho.png", this);

        this.boss = new Boss(HyperShapes.INSTANCE.getScreenWidth() / 2, HyperShapes.INSTANCE.getScreenHeight() / 2, 192, "PrimeiroBoss.png", this);
        this.bossProjectiles = new ArrayList<>();
        this.bossBars = new ArrayList<>();

        this.shapeRenderer = new ShapeRenderer();

        this.generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        this.parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.parameter.size = 30;
        this.parameter.borderWidth = 1;
        this.parameter.borderColor = Color.BLACK;
        this.parameter.color = Color.WHITE;
        this.bitmap = generator.generateFont(parameter);

    }

    /**
     * Método que será chamado a cada frame do jogo para fazer uma atualização do que é necessário e verificar inputs
     */
    public void update() {
        world.step(1 / 60f, 6, 2);

        this.camera.update();

        if (this.player.getScore() % 3 == 0 && this.player.getScore() % 6 != 0) {
            this.boss.setStage(2);
            Iterator<BossProjectile> iterator3 = bossProjectiles.iterator();
            while (iterator3.hasNext()) {
                BossProjectile bossProjectile = iterator3.next();
                world.destroyBody(bossProjectile.getBody());
                iterator3.remove();
            }
        }

        if (slowEffect && playerProjectile.getCanShoot()) {
            timeHelper.setTimeScale(0.1f);
        } else {
            timeHelper.setTimeScale(1f);
        }

        if (player.getLost() == true) {
            HyperShapes.INSTANCE.setScreen(new GameOverScreen(camera, this));
        }

        this.player.update(timeHelper.getDeltaTime());
        this.playerProjectile.update(timeHelper.getDeltaTime());

        this.boss.update(timeHelper.getDeltaTime());

        Iterator<BossProjectile> iterator = bossProjectiles.iterator();
        while (iterator.hasNext()) {
            BossProjectile bossProjectile = iterator.next();
            bossProjectile.update(timeHelper.getDeltaTime());
            if (bossProjectile.destroy()) {
                world.destroyBody(bossProjectile.getBody());
                iterator.remove();
            }
        }

        Iterator<BossBar> iterator2 = bossBars.iterator();
        while (iterator2.hasNext()) {
            BossBar bossBar = iterator2.next();
            bossBar.update(timeHelper.getDeltaTime());
            if (bossBar.destroy()) {
                world.destroyBody(bossBar.getBody());
                iterator2.remove();
            }
        }

        batch.setProjectionMatrix(camera.combined);

    }

    /**
     * Método que é chamado a cada frame do jogo, chamando o método update como também fazendo as renderizações na tela.
     *
     * @param delta (float) o tempo decorrido desde a última atualização
     */
    @Override
    public void render(float delta) {
        update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        this.player.render(batch);

        if (playerProjectile.getCanShoot() && playerProjectile.getDelayShoot() > 3f) {
            this.playerProjectile.render(batch, new Texture("Sabonete.png"));
        } else {
            this.playerProjectile.render(batch, new Texture("SaboneteRosa.png"));
        }

        this.boss.render(batch);

        bitmap.draw(batch, "" + (3f - playerProjectile.getDelayShoot() <= 0 ? "Ready!" : Math.ceil(3f - playerProjectile.getDelayShoot()) + " s"), 100, HyperShapes.INSTANCE.getScreenHeight() - 100);

        if (this.boss.getStage() == 2) {
            bitmap.draw(batch, "" + Math.round(boss.getSecondStageTime()) + " s", HyperShapes.INSTANCE.getScreenWidth() / 2, HyperShapes.INSTANCE.getScreenHeight() - 100);
        }

        bitmap.draw(batch, "Pontos: " + (int) Math.pow(player.getScore(), HyperShapes.INSTANCE.getDifficulty()), HyperShapes.INSTANCE.getScreenWidth() - 225, HyperShapes.INSTANCE.getScreenHeight() - 100);

        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (BossProjectile bossProjectile : bossProjectiles) {
            bossProjectile.render(shapeRenderer);
        }

        for (BossBar bossBar : bossBars) {
            bossBar.render(shapeRenderer);
        }

        this.playerProjectile.renderLine(shapeRenderer);

        this.wallTop.render(shapeRenderer);
        this.wallRight.render(shapeRenderer);
        this.wallBottom.render(shapeRenderer);
        this.wallLeft.render(shapeRenderer);

        shapeRenderer.end();
    }

    /**
     * Chamado quando esta tela deveria liberar todos os recursos.
     */
    @Override
    public void dispose() {
        world.dispose();
        shapeRenderer.dispose();
        bitmap.dispose();
        generator.dispose();
        batch.dispose();
    }

    /**
     * Método que retorna o mundo do jogo.
     *
     * @return world (World)
     */
    public World getWorld() {
        return this.world;
    }

    /**
     * Método que retorna o jogador.
     *
     * @return player (Player)
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Método que retorna o projétil do jogador.
     *
     * @return playerProjectile (PlayerProjectile)
     */
    public PlayerProjectile getPlayerProjectile() {
        return playerProjectile;
    }

    /**
     * Método que retorna o chefe do jogo.
     *
     * @return boss (Boss)
     */
    public Boss getBoss() {
        return boss;
    }

    /**
     * Método que retorna o mundo do jogo.
     *
     * @return bossProjectiles (lista do tipo BossProjectile)
     */
    public ArrayList<BossProjectile> getBossProjectiles() {
        return bossProjectiles;
    }

    /**
     * Método que atribui um novo valor a variavel slowEffect.
     *
     * @param slowEffect (boolean)
     */
    public void setSlowEffect(boolean slowEffect) {
        this.slowEffect = slowEffect;
    }

    /**
     * Método que retorna o timeHelper.
     *
     * @return timeHelper (TimeHelper)
     */
    public TimeHelper getTimeHelper() {
        return timeHelper;
    }

    /**
     * Método que retorna o mundo do jogo.
     *
     * @return world (World)
     */
    public ArrayList<BossBar> getBossBars() {
        return bossBars;
    }
}
