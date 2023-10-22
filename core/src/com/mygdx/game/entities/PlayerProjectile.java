package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.GameScreen;
import com.mygdx.game.HyperShapes;

import static com.mygdx.game.helper.BodyHelper.createCircle;
import static com.mygdx.game.helper.Constants.PPM;
import static com.mygdx.game.helper.ContactType.PLAYERPROJECTILE;

public class PlayerProjectile extends InputAdapter {

    private Body body;
    private Vector2 position;
    private float speed;
    private int diameter;
    private Texture texture;
    private GameScreen gameScreen;
    private boolean canShoot;
    private boolean drawLine;
    private boolean touchedDown;
    private float shootDelay;
    private float deltaTime;

    public PlayerProjectile(GameScreen gameScreen) {
        this.position = randomSpawn();
        this.gameScreen = gameScreen;
        this.speed = 1000000;
        this.diameter = 28;
        this.texture = new Texture("SaboneteRosa.png");
        this.body = createCircle(position.x, position.y, diameter, false, 1, gameScreen.getWorld(), PLAYERPROJECTILE, new FixtureDef());

        this.canShoot = false;
        this.shootDelay = 3f;
        this.deltaTime = 0;
        this.drawLine = false;
        this.touchedDown = false;

        Gdx.input.setInputProcessor(this);

    }

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

    public void renderLine(ShapeRenderer s) {
        if (drawLine) {
//            Vector2 p1 = new Vector2(Gdx.input.getX(), (Gdx.graphics.getHeight() - Gdx.input.getY()));
//            Vector2 p2 = new Vector2(x + diameter, y + diameter);
//            Vector2 p3 = new Vector2(p2).sub(p1).scl(500f).add(p2);

            Vector2 p1 = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            Vector2 p2 = new Vector2(position.x + diameter, position.y + diameter);
            Vector2 mouseToP2 = p2.cpy().sub(p1);
            Vector2 extendedP1 = p1.cpy().sub(mouseToP2.nor().scl(10000f));

            s.setColor(Color.WHITE);
            s.rectLine(extendedP1, p2, 2);
//            s.rectLine(p2, p3, 2);
        }
    }

    public void render(SpriteBatch batch, Texture textura) {
        batch.draw(textura, position.x + (diameter/2), position.y + (diameter/2), diameter, diameter);
    }

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

    public Body getBody() {
        return body;
    }

    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }

    public boolean getCanShoot() {
        return canShoot;
    }

    public void applyForceToFollowMouse(float mouseX, float mouseY, float deltaTime) {
        if (canShoot && shootDelay >= 3) {
            float angle = MathUtils.atan2(mouseY - body.getPosition().y, mouseX - body.getPosition().x);

//            Vector2 p1 = new Vector2(mouseX, mouseY);
//            Vector2 p2 = new Vector2(body.getPosition().x, body.getPosition().y);
//            Vector2 p3 = new Vector2(p2).sub(p1).scl(500f).add(p2);
//            float angle = MathUtils.atan2(p3.y - body.getPosition().y, p3.x - body.getPosition().x);

            float forceX = MathUtils.cos(angle) * speed * deltaTime;
            float forceY = MathUtils.sin(angle) * speed * deltaTime;

            shootDelay = 0;
            body.applyForceToCenter(forceX, forceY, true);
        } else {
            canShoot = false;
        }

    }

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

    public float getDelayShoot() {
        return shootDelay;
    }

}
