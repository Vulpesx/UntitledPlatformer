package me.potaytoprograms.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import me.potaytoprograms.api.event.Event;
import me.potaytoprograms.api.event.EventManager;
import me.potaytoprograms.api.scene.SceneManager;

import java.util.HashMap;

public class Game implements Disposable {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;
    public static final String TITLE = "Untitled Platformer";
    public static final String VERSION = "INDEV";
    public static final int PPM = 16;

    public static SceneManager sceneManager;
    public static EventManager eventManager;
    public static SpriteBatch batch;
    public static World world;

    public static void init(){
        sceneManager = new SceneManager();
        eventManager = new EventManager();
        batch = new SpriteBatch();
        world = new World(new Vector2(), true);
    }

    public static void update(){
        world.step(1f/60f, 6, 2);
        sceneManager.update(Gdx.graphics.getDeltaTime(), batch);
    }

    public void registerEvent(Event e){
        eventManager.registerEvent(e);
    }

    public void registerEventHandler(Object o){
        eventManager.registerEventHandler(o);
    }

    public static void callEvent(Event e){
        eventManager.callEvent(e);
    }

    public static void callEvent(String id, HashMap<String, Object> data){
        eventManager.callEvent(id, data);
    }

    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
        sceneManager.dispose(true);
    }
}
