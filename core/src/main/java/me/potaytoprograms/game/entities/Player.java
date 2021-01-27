package me.potaytoprograms.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;
import me.potaytoprograms.api.scene.Renderable;
import me.potaytoprograms.api.scene.Updateable;
import me.potaytoprograms.api.util.Box2DUtil;
import me.potaytoprograms.api.util.RaycastRenderer;
import me.potaytoprograms.game.Constants;
import me.potaytoprograms.game.Game;

public class Player implements Updateable, Renderable, Disposable {

    public Vector2 position;

    private final Body body;
    private final World world;

    private final Vector2 velocity;
    private final float SPEED = 25;
    private final float ACCEL = 2;
    private final float JUMP_POWER = 20;
    private final float JUMP_MULTI = 0.4f;
    private final float GRAVITY = -0.98f;

    private boolean onFloor = false;
    private boolean onWall = false;
    private int wallDir = 0;
    private int jumpCount = 0;
    private int dashCount = 0;

    private final Vector2 raycastTo = new Vector2();
    private final Vector2 raycastFrom = new Vector2();
    private float rayDist = 0.25f;

    private final RaycastRenderer rayRenderer;
    private float width;
    private float height;

    public Player(float x, float y, World world, RaycastRenderer rayRenderer){
        this.world = world;
        this.rayRenderer = rayRenderer;

        velocity = new Vector2();
        position = new Vector2(x, y);

        PolygonShape shape = new PolygonShape();
        width = 10f / Game.PPM * 2;
        height = 10f / Game.PPM * 2;
        shape.setAsBox(width/2f,height/2f);

        body = Box2DUtil.createBody(shape, position.x, position.y, world, new Box2DUtil.BDef()
                .category(Constants.PLAYER)
                .mask(Constants.WORLD)
                .fixedRotation(true)
                .type(BodyDef.BodyType.DynamicBody));
    }

    @Override
    public void update(float delta) {
        updateVars();

        if((onFloor && velocity.y <= 0)) velocity.y = GRAVITY;
        else velocity.y += GRAVITY;
        if(onWall && velocity.y == 0 && (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.LEFT))){
            velocity.y = GRAVITY * 2;
            velocity.x = 0.01f * wallDir;
        }

        if(onFloor || onWall){
            jumpCount = 2;
            dashCount = 2;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)  && !Gdx.input.isKeyPressed(Input.Keys.DOWN))
            velocity.x -= ACCEL;
        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.DOWN))
            velocity.x += ACCEL;
        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            velocity.x *= 0.98f;
        else{
            velocity.x *= 0.9f;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && dashCount > 0){
            velocity.x *= 100;
            dashCount--;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP) && jumpCount > 0){
            if(onWall) velocity.x = 50 * wallDir;
            velocity.y = JUMP_POWER;
            jumpCount--;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP) && !onFloor && velocity.y > 0)
            velocity.y += JUMP_MULTI;
        if(!Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            velocity.x = MathUtils.clamp(velocity.x, -SPEED, SPEED);

        body.setLinearVelocity(velocity);
    }

    private void updateVars(){
        position.set(body.getPosition());
        onFloor = false;
        onWall = false;
        wallDir = 0;

        RayCastCallback floor = (fixture, point, normal, fraction) -> {
            if(fixture.getFilterData().categoryBits != Constants.WORLD) return 0;
            onFloor = true;
            rayRenderer.addRay(new RaycastRenderer.Raycast(raycastTo, raycastFrom, normal, point));
            return 0;
        };

        raycastFrom.set(position.x - width/2, position.y - height/2);
        raycastTo.set(raycastFrom.x, raycastFrom.y - rayDist);
        world.rayCast(floor, raycastFrom, raycastTo);

        if(!onFloor) rayRenderer.addRay(new RaycastRenderer.Raycast(raycastTo, raycastFrom, null, null));

        raycastFrom.set(position.x + width/2, position.y - height/2);
        raycastTo.set(raycastFrom.x, raycastFrom.y - rayDist);
        world.rayCast(floor, raycastFrom, raycastTo);
        if(!onFloor) rayRenderer.addRay(new RaycastRenderer.Raycast(raycastTo, raycastFrom, null, null));

        RayCastCallback wall = (fixture, point, normal, fraction) -> {
            if(fixture.getFilterData().categoryBits != Constants.WORLD) return 0;
            onWall = true;
            wallDir = (int) normal.x;
            rayRenderer.addRay(new RaycastRenderer.Raycast(raycastTo, raycastFrom, normal, point));
            return 0;
        };

        raycastFrom.set(position.x + width/2, position.y);
        raycastTo.set(raycastFrom.x + rayDist, raycastFrom.y);
        world.rayCast(wall, raycastFrom, raycastTo);
        if(!onWall) rayRenderer.addRay(new RaycastRenderer.Raycast(raycastTo, raycastFrom, null, null));

        raycastFrom.set(position.x - width/2, position.y);
        raycastTo.set(raycastFrom.x - rayDist, raycastFrom.y);
        world.rayCast(wall, raycastFrom, raycastTo);
        if(!onWall) rayRenderer.addRay(new RaycastRenderer.Raycast(raycastTo, raycastFrom, null, null));
    }

    @Override
    public void render(SpriteBatch batch) {
        
    }

    @Override
    public void dispose() {
        
    }
}
