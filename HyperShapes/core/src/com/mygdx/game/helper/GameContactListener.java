package com.mygdx.game.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.GameScreen;
import com.mygdx.game.HyperShapes;
import com.mygdx.game.entities.BossProjectile;

import static com.mygdx.game.helper.Constants.PPM;
import static com.mygdx.game.helper.ContactType.*;

public class GameContactListener implements ContactListener {

    private GameScreen gameScreen;

    public GameContactListener(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a == null || b == null) {
            if (a.getUserData() == null || b.getUserData() == null) {
                return;
            }
        }

        if (a.getUserData() == PLAYER || b.getUserData() == PLAYER) {
            if (a.getUserData() == BOSS || b.getUserData() == BOSS) {
//                Gdx.app.exit();
            }
        }

        if (a.getUserData() == PLAYER || b.getUserData() == PLAYER) {
            if (a.getUserData() == BOSSPROJECTILE || b.getUserData() == BOSSPROJECTILE) {
                float x = a.getUserData() == BOSSPROJECTILE ? a.getBody().getPosition().x : b.getBody().getPosition().x;

                float y = a.getUserData() == BOSSPROJECTILE ? a.getBody().getPosition().y : b.getBody().getPosition().y;

                for (BossProjectile bossProjectile : gameScreen.getBossProjectiles()) {
                    if (bossProjectile.getX() == x && bossProjectile.getY() == y) {
                        bossProjectile.setDestroy(true);
                        break;
                    }
                }
//                Gdx.app.exit();
            }
        }

        if (a.getUserData() == PLAYER || b.getUserData() == PLAYER) {
            if (a.getUserData() == SENSOR || a.getUserData() == PLAYERPROJECTILE || b.getUserData() == SENSOR || b.getUserData() == PLAYERPROJECTILE) {
                gameScreen.getPlayerProjectile().setCanShoot(true);
            }
        }

        if (a.getUserData() == PLAYERPROJECTILE || b.getUserData() == PLAYERPROJECTILE) {
            if (a.getUserData() == BOSS || b.getUserData() == BOSS) {
                if (!gameScreen.getBoss().getWasHit() && gameScreen.getBoss().getHitDelay() <= 0.5) {
                    gameScreen.getBoss().setWasHit(true);
                    gameScreen.getPlayer().score();
                }
            }
        }

        if (a.getUserData() == BOSSPROJECTILE || b.getUserData() == BOSSPROJECTILE) {
            if (a.getUserData() == WALL || b.getUserData() == WALL || a.getUserData() == PLAYERPROJECTILE || b.getUserData() == PLAYERPROJECTILE) {

                float x = a.getUserData() == BOSSPROJECTILE ? a.getBody().getPosition().x : b.getBody().getPosition().x;

                float y = a.getUserData() == BOSSPROJECTILE ? a.getBody().getPosition().y : b.getBody().getPosition().y;

                for (BossProjectile bossProjectile : gameScreen.getBossProjectiles()) {
                    if (bossProjectile.getX() == x && bossProjectile.getY() == y) {
                        bossProjectile.setDestroy(true);
                        break;
                    }
                }
            }
        }

    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a == null || b == null) {
            if (a.getUserData() == null || b.getUserData() == null) {
                return;
            }
        }

        if (a.getUserData() == PLAYER || b.getUserData() == PLAYER) {
            if (a.getUserData() == SENSOR || b.getUserData() == SENSOR) {
                gameScreen.getPlayerProjectile().setCanShoot(false);
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
