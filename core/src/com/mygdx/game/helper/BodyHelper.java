package com.mygdx.game.helper;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.mygdx.game.helper.Constants.PPM;
import static com.mygdx.game.helper.ContactType.SENSOR;

public class BodyHelper {

    public static Body createBox(float x, float y, float width, float height, boolean isStatic, float density, World world, ContactType type) {
        BodyDef bodyDef = new BodyDef();
        if (isStatic) {
            bodyDef.type = BodyDef.BodyType.StaticBody;
        } else {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        }

        bodyDef.fixedRotation = true;
        bodyDef.position.set(new Vector2(x / PPM, y / PPM));
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        fixtureDef.density = density;
        body.createFixture(fixtureDef).setUserData(type);

        shape.dispose();
        return body;
    }

    public static Body createSensorBox(float x, float y, float width, float height, World world, ContactType type) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(new Vector2(x / PPM, y / PPM));
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM);

        FixtureDef fixSensor = new FixtureDef();
        fixSensor.shape = shape;
        fixSensor.isSensor = true;
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        body.createFixture(fixtureDef);
        body.createFixture(fixSensor).setUserData(type);

        shape.dispose();
        return body;
    }

    public static Body createCircle(float x, float y, float diameter, boolean isStatic, float density, World world, ContactType type, FixtureDef fixSensor) {
        BodyDef bodyDef = new BodyDef();
        if (isStatic) {
            bodyDef.type = BodyDef.BodyType.StaticBody;
        } else {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        }

        bodyDef.fixedRotation = true;
        bodyDef.position.set(new Vector2(x / PPM, y / PPM));
        Body body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(diameter / 2 / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0.5f;
        fixtureDef.density = density;

        if (fixSensor != null) {
            CircleShape shape2 = new CircleShape();
            shape2.setRadius(diameter * 8 / 2 / PPM);
            fixSensor.shape = shape2;
            fixSensor.isSensor = true;
            body.createFixture(fixSensor).setUserData(SENSOR);
        }
        body.createFixture(fixtureDef).setUserData(type);

        shape.dispose();
        return body;
    }

}
