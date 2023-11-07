package com.mygdx.game.helper;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.mygdx.game.helper.Constants.PPM;
import static com.mygdx.game.helper.ContactType.SENSOR;

/**
 * Classe que auxilia na criação de corpos em um mundo.
 */
public class BodyHelper {

    /**
     * Método que cria um corbo no formato de uma caixa, recebendo os valores necessários para isso acontecer.
     *
     * @param x        (float) posição horizontal do corpo
     * @param y        (float) posição vertical do corpo
     * @param width    (float) largura do corpo
     * @param height   (float) altura do corpo
     * @param isStatic (boolean) booleano que representa se o corpo será do tipo estático (imóvel)
     * @param density  (float) densidade do corpo, auxiliando em cálculos de física
     * @param world    (World) o mundo no qual o corpo será criado
     * @param type     (ContactType) o tipo de contato que o corpo representa
     * @return um corpo em formato de caixa com todos os valores necessários definidos
     */
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

    /**
     * Método para criar uma sensor em formato de caixa
     *
     * @param x      (float) posição horizontal do sensor
     * @param y      (float) posição vertical do sensor
     * @param width  (float) largura do sensor
     * @param height (float) altura do sensor
     * @param world  (World) o mundo no qual o sensor será criado
     * @param type   (ContactType) o tipo de contato que o sensor representa
     * @return um corpo em formato retangular que também é um sensor, um tipo diferente de corpo
     */
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

        body.createFixture(fixSensor).setUserData(type);

        shape.dispose();
        return body;
    }

    /**
     * Método paraa criação de corpos em formatos circulares, recebendo um diâmetro em vez de dois valores de comprimento
     *
     * @param x         (float) posição horizontal do corpo
     * @param y         (float) posição vertical do corpo
     * @param diameter  (float) diâmetro do corpo
     * @param isStatic  (boolean) booleano que representa se o corpo será do tipo estático (imóvel)
     * @param density   (float) densidade do corpo, auxiliando em cálculos de física
     * @param world     (World) o mundo no qual o corpo será criado
     * @param type      (ContactType) o tipo de contato que o corpo representa
     * @param fixSensor (FixtureDef) o sensor que body terá. Caso nulo, não será um sensor
     * @return um corpo no formato circular
     */
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
        fixtureDef.restitution = 0.25f;
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
