package com.mygdx.game;

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
import com.mygdx.game.entities.*;
import com.mygdx.game.helper.GameContactListener;
import com.mygdx.game.helper.TimeHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static com.mygdx.game.helper.Constants.PPM;

public class GameScreen extends ScreenAdapter {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private GameContactListener gameContactListener;

    private Player player;
    private PlayerProjectile playerProjectile;

    private Boss boss;
    private ArrayList<BossProjectile> bossProjectiles;
    private ArrayList<BossBar> bossBars;

    private Wall wallTop, wallRight, wallBottom, wallLeft;

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont bitmap;

    private boolean slowEffect;
    private TimeHelper timeHelper;

    public GameScreen(OrthographicCamera camera) {
        this.camera = camera;
        this.camera.position.set(new Vector3(HyperShapes.INSTANCE.getScreenWidth() / 2, HyperShapes.INSTANCE.getScreenHeight() / 2, 0));
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0, 0), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.gameContactListener = new GameContactListener(this);
        this.world.setContactListener(this.gameContactListener);

        this.player = new Player(HyperShapes.INSTANCE.getScreenWidth() / 2 - 300, HyperShapes.INSTANCE.getScreenHeight() / 2 - 200, 48, 48, "patinho.png", this);
        this.playerProjectile = new PlayerProjectile(HyperShapes.INSTANCE.getScreenWidth() / 2 + 100, HyperShapes.INSTANCE.getScreenHeight() / 2 - 200, 28, "SaboneteRosa.png", this);

        this.slowEffect = false;
        this.timeHelper = new TimeHelper();

        this.wallTop = new Wall(HyperShapes.INSTANCE.getScreenWidth()/2, HyperShapes.INSTANCE.getScreenHeight() - 16, HyperShapes.INSTANCE.getScreenWidth(), 32, "patinho.png", this);
        this.wallRight = new Wall(HyperShapes.INSTANCE.getScreenWidth() - 16, HyperShapes.INSTANCE.getScreenHeight()/2, 32, HyperShapes.INSTANCE.getScreenHeight(), "patinho.png", this);
        this.wallBottom = new Wall(HyperShapes.INSTANCE.getScreenWidth()/2, 16, HyperShapes.INSTANCE.getScreenWidth(), 32, "patinho.png", this);
        this.wallLeft = new Wall(16, HyperShapes.INSTANCE.getScreenHeight()/2, 32, HyperShapes.INSTANCE.getScreenHeight(), "patinho.png", this);

        this.boss = new Boss(HyperShapes.INSTANCE.getScreenWidth() / 2, HyperShapes.INSTANCE.getScreenHeight() / 2, 192, "PrimeiroBoss.png", this);
        this.bossProjectiles = new ArrayList<BossProjectile>();
        this.bossBars = new ArrayList<BossBar>();

        this.shapeRenderer = new ShapeRenderer();

        this.generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        this.parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.parameter.size = 30;
        this.parameter.borderWidth = 1;
        this.parameter.borderColor = Color.BLACK;
        this.parameter.color = Color.WHITE;
        this.bitmap = generator.generateFont(parameter);

    }

    public void update() {
        world.step(1 / 60f, 6, 2);

        this.camera.update();

        if (this.player.getScore() == 3) {
            this.boss.setStage(2);
        }

        if (slowEffect && playerProjectile.getCanShoot()) {
            timeHelper.setTimeScale(0.1f);
        } else {
            timeHelper.setTimeScale(1f);
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

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

    }

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
            this.playerProjectile.render(batch,new Texture("SaboneteRosa.png"));
        }

        this.boss.render(batch);

        bitmap.draw(batch, "" + (3f - playerProjectile.getDelayShoot() <= 0 ? "Ready!" : Math.ceil(3f - playerProjectile.getDelayShoot()) + " s"), 100, HyperShapes.INSTANCE.getScreenHeight() - 100);
        bitmap.draw(batch, "Pontos: " + player.getScore(), HyperShapes.INSTANCE.getScreenWidth() - 225, HyperShapes.INSTANCE.getScreenHeight() - 100);

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

        this.box2DDebugRenderer.render(world, camera.combined.scl(PPM));
    }

    public World getWorld() {
        return this.world;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerProjectile getPlayerProjectile() {
        return playerProjectile;
    }

    public Boss getBoss() {
        return boss;
    }

    public ArrayList<BossProjectile> getBossProjectiles() {
        return bossProjectiles;
    }

    public void setSlowEffect(boolean slowEffect) {
        this.slowEffect = slowEffect;
    }

    public TimeHelper getTimeHelper() {
        return timeHelper;
    }

    public ArrayList<BossBar> getBossBars() {
        return bossBars;
    }
}
