package me.potaytoprograms.game.scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;
import me.potaytoprograms.api.scene.Renderable;
import me.potaytoprograms.api.scene.Scene;
import me.potaytoprograms.api.scene.Updateable;
import me.potaytoprograms.api.util.Box2DUtil;
import me.potaytoprograms.api.util.RaycastRenderer;
import me.potaytoprograms.game.Constants;
import me.potaytoprograms.game.Game;
import me.potaytoprograms.game.entities.Player;

public class GameScene extends Scene {

    private final Box2DDebugRenderer b2dr;
    private final OrthographicCamera cam;
    private final RaycastRenderer rayRenderer;

    public GameScene(){
        b2dr = new Box2DDebugRenderer();
        rayRenderer = new RaycastRenderer();

        cam = new OrthographicCamera();
        cam.setToOrtho(false, Game.WIDTH  / Game.PPM, Game.HEIGHT  / Game.PPM);

        addToAll(new Player(10,10,Game.world, rayRenderer));
        
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Game.WIDTH / Game.PPM / 2f, 10f / Game.PPM);

        Box2DUtil.createBody(shape, Game.WIDTH/Game.PPM/2f, 0.5f, Game.world, new Box2DUtil.BDef()
        .category(Constants.WORLD)
        .mask(Constants.PLAYER));
        
        shape.setAsBox(10/Game.PPM, Game.HEIGHT/Game.PPM/2);
        Box2DUtil.createBody(shape, Game.WIDTH/Game.PPM, Game.HEIGHT/Game.PPM/2, Game.world, new Box2DUtil.BDef()
        .category(Constants.WORLD)
        .mask(Constants.PLAYER));

        Box2DUtil.createBody(shape, 0, Game.HEIGHT/Game.PPM/2, Game.world, new Box2DUtil.BDef()
        .category(Constants.WORLD)
        .mask(Constants.PLAYER));
        shape.dispose();
    }

    private void addToAll(Object o){
        updateables.add((Updateable) o);
        renderables.add((Renderable) o);
        disposables.add((Disposable) o);
    }

    @Override
    public void render(SpriteBatch batch) {
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        super.render(batch);
        b2dr.render(Game.world, cam.combined);
        rayRenderer.render(cam.combined);
    }

    @Override
    public void dispose() {
        super.dispose();
        rayRenderer.dispose();
        b2dr.dispose();
    }
}
