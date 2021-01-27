package me.potaytoprograms.api.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class RaycastRenderer implements Disposable {
    private ShapeRenderer shapeRenderer;
    private Array<Raycast> raycasts = new Array<>();

    public RaycastRenderer(){
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
    }

    public void addRay(Raycast ray){
        raycasts.add(ray);
    }

    public void render(Matrix4 combined){
        shapeRenderer.setProjectionMatrix(combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for(Raycast ray : raycasts){
            shapeRenderer.setColor(Color.CYAN);
            shapeRenderer.line(ray.from, ray.to);
            if(ray.point != null) {
                shapeRenderer.setColor(Color.RED);
                shapeRenderer.line(ray.point, ray.normal);
            }
        }
        raycasts.clear();
        shapeRenderer.end();
    }

    public static class Raycast{
        private Vector2 to = new Vector2();
        private Vector2 from = new Vector2();
        private Vector2 normal = new Vector2();
        private Vector2 point = new Vector2();

        public Raycast(Vector2 to, Vector2 from, Vector2 normal, Vector2 point){
            this.to.set(to);
            this.from.set(from);
            if(point != null) {
                this.normal.set(point.x + normal.x, point.y + normal.y);
                this.point.set(point);
            }
        }
    }
    
    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
