package me.potaytoprograms.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import me.potaytoprograms.api.event.EventHandler;
import me.potaytoprograms.api.scene.Scene;
import me.potaytoprograms.game.scenes.GameScene;

import java.util.HashMap;

public class Platformer extends ApplicationAdapter {

    @Override
    public void create() {
        Game.init();
        Game.sceneManager.changeScenes(new Scene[] {new GameScene()});
    }

    @Override
    public void render() {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Game.update();
    }
}