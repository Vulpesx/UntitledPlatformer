package me.potaytoprograms.api.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.potaytoprograms.api.scene.Renderable;
import me.potaytoprograms.api.scene.Updateable;

import java.util.HashMap;

public class AnimationManager implements Renderable, Updateable{

    private final HashMap<String, Animation> animations;
    private Animation currAnim;

    public AnimationManager(){
        animations = new HashMap<>();
    }

    public AnimationManager(String key, Animation anim){
        this();
        animations.put(key, anim);
        currAnim = animations.get(key);
    }

    public void addAnim(String key, Animation anim){
        animations.put(key, anim);
    }

    public void removeAnim(String key){
        animations.remove(key);
    }

    public Animation getAnim(String key){
        return animations.get(key);
    }

    public Animation getCurrAnim(){
        return currAnim;
    }

    public void setAnim(String key){
        currAnim = animations.get(key);
    }


    @Override
    public void render(SpriteBatch batch) {
        if(currAnim != null) currAnim.render(batch);
    }

    @Override
    public void update(float delta) {
        if(currAnim != null) currAnim.update(delta);
    }
}
