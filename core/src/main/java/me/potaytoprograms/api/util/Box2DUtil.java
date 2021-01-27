package me.potaytoprograms.api.util;

import com.badlogic.gdx.physics.box2d.*;

public class Box2DUtil {

    public static Body createBody(Shape shape, float x, float y, World world, BDef def){
        BodyDef bdef = new BodyDef();
        bdef.position.set(x, y);
        bdef.type = def.type;
        bdef.fixedRotation = def.fixedRotation;
        bdef.angle = def.angle;
        bdef.bullet = def.bullet;
        bdef.gravityScale = def.gravityScale;

        Body body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = def.density;
        fdef.isSensor = def.isSensor;
        fdef.restitution = def.restitution;
        fdef.filter.categoryBits = def.category;
        fdef.filter.maskBits = def.mask;
        fdef.filter.groupIndex = def.group;
        fdef.friction = def.friction;

        body.createFixture(fdef).setUserData(def.userData);

        return body;
    }

    public static class BDef{
        private BodyDef.BodyType type = BodyDef.BodyType.StaticBody;
        private boolean fixedRotation = false, bullet = false, isSensor = false;
        private float angle = 0f, gravityScale = 1, density = 1, restitution = 0, friction = 0.2f;
        private short category = 0x0001, mask = -1, group = 0;
        private Object userData = null;

        public BDef type(BodyDef.BodyType type){
            this.type = type;
            return this;
        }

        public BDef fixedRotation(boolean b){
            fixedRotation = b;
            return this;
        }

        public BDef bullet(boolean b){
            bullet = b;
            return this;
        }

        public BDef isSensor(boolean b){
            isSensor = b;
            return this;
        }

        public BDef angle(float f){
            angle = f;
            return this;
        }

        public BDef gravScale(float f){
            gravityScale = f;
            return this;
        }

        public BDef density(float f){
            density = f;
            return this;
        }


        public BDef restitution(float f){
            restitution = f;
            return this;
        }

        public BDef category(short s){
            category = s;
            return this;
        }

        public BDef mask(short s){
            mask = s;
            return this;
        }

        public BDef group(short s){
            group = s;
            return this;
        }

        public BDef friction(float f){
            friction = f;
            return this;
        }
    }
}
