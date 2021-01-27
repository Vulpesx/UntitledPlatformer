package me.potaytoprograms.api.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public abstract class Scene implements Disposable {

    public Array<Updateable> updateables = new Array<>();
    public Array<Renderable> renderables = new Array<>();
    public Array<Disposable> disposables = new Array<>();

    public void update(float delta){
        for(int i = 0; i < updateables.size; i++){
            updateables.get(i).update(delta);
        }
    }

    public void render(SpriteBatch batch){
        batch.begin();
        for(int i = 0; i < renderables.size; i++){
            renderables.get(i).render(batch);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        for(int i = 0; i < disposables.size; i++){
            disposables.get(i);
        }
        disposables.clear();
        updateables.clear();
        renderables.clear();
    }
}
