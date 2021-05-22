package me.potaytoprograms.api.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class SceneManager {

    //running data
    private final Array<Scene> scenes = new Array<>();
    private int updateOrder[] = {};
    private int renderOrder[] = {};

    //queued data
    private int uOrderQueue[] = {};
    private int rOrderQueue[] = {};
    private Scene sceneQueue[] = {};


    public void update(float delta, SpriteBatch batch){
        int x = 0;
        if(updateOrder.length > 0){
            x = updateOrder.length;

            for (int i = 0; i < x; i++) {
                scenes.get(updateOrder[i]).update(delta);
            }
        }
        else{
            x = scenes.size;

            for (int i = 0; i < x; i++) {
                scenes.get(i).update(delta);
            }
        }

        if(renderOrder.length > 0){
            x = renderOrder.length;

            for(int i = 0; i < x; i++){
                scenes.get(renderOrder[i]).render(batch);
            }
        }
        else{
            x = scenes.size;

            for(int i = 0; i < x; i++){
                scenes.get(i).render(batch);
            }
        }

        if(sceneQueue.length > 0){{
            clearScenes();

            setScenes(sceneQueue, uOrderQueue, rOrderQueue);

            sceneQueue = new Scene[] {};
            uOrderQueue = new int[] {};
            rOrderQueue = new int[] {};
        }}
    }

    public void addScene(Scene scene){
        scenes.add(scene);
    }

    public void clearScenes(){
        scenes.clear();
    }

    public void removeScene(int i){
        scenes.removeIndex(i);
    }

    public void setScenes(Scene[] scenes, int[] uOrder, int[] rOrder){
        dispose(false);
        clearScenes();
        for (Scene scene : scenes) {
            addScene(scene);
        }
        updateOrder = uOrder;
        renderOrder = rOrder;
    }

    public void setScenes(Scene[] scenes){
        setScenes(scenes, new int[] {}, new int[] {});
    }

    public void changeScenes(Scene[] scenes, int[] uOrder, int[] rOrder){
        sceneQueue = scenes;
        uOrderQueue = uOrder;
        rOrderQueue = rOrder;
    }

    public void changeScenes(Scene[] scenes){
        changeScenes(scenes, new int[] {}, new int[] {});
    }

    public void dispose(boolean close) {
        for(int i = 0; i < scenes.size; i++){
            scenes.get(i).dispose();
        }
        if(close){
            for(int i = 0; i < sceneQueue.length; i++){
                sceneQueue[i].dispose();
            }
        }
    }
}
