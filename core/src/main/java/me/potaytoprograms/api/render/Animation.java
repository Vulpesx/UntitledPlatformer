package me.potaytoprograms.api.render;

import com.badlogic.gdx.math.Vector2;
import me.potaytoprograms.api.scene.Renderable;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import me.potaytoprograms.api.scene.Updateable;

public class Animation implements Renderable, Updateable {

  private final TextureRegion[] frames;
  private final Vector2 pos;
  private TextureRegion frame;
  private int index = 0;
  private final int fps;
  public AnimDef def;
  public boolean flip = false;
  public boolean play = true;


  public Animation(int fps, Vector2 pos, AnimDef def, TextureRegion... frames){
    this.frames = frames;
    this.pos = pos;
    this.def = def;
    this.frame = frames[0];
    this.fps = fps;
  }

  @Override
  public void render(SpriteBatch batch) {
    batch.draw(frame, pos.x - frame.getRegionWidth() * (flip ? -def.scaleX : def.scaleX) / 2 + (flip ? -def.offsetX : def.offsetX), pos.y - frame.getRegionHeight() * def.scaleY / 2 + def.offsetY, def.originX, def.originY, frame.getRegionWidth(), frame.getRegionHeight(), (flip ? -def.scaleX : def.scaleX), def.scaleY, def.rotation);
  }

  private float lastUpdate = 0f;
  @Override
  public void update(float delta) {
    lastUpdate += delta;
    if(lastUpdate >= 1f/fps){
      lastUpdate = 0f;
      if(index == frames.length - 1 && def.loop) index = 0;
      else if(index == frames.length - 1 && !def.loop) index = frames.length - 1;
      else index++;
      if(play) frame = frames[index];
      else frame = frames[0];
    }
  }

  public TextureRegion getFrame(){
    return frame;
  }

  public static class AnimDef{

    private boolean loop = true;
    private boolean bounce = false;
    private float scaleX = 1f;
    private float scaleY = 1f;
    private float rotation = 0f;
    private float offsetX = 0f;
    private float offsetY = 0f;
    private float originX = 0f;
    private float originY = 0f;


    public AnimDef loop(boolean b){
      loop = b;
      return this;
    }

    public AnimDef bounce(boolean b){
      bounce = b;
      return this;
    }

    public AnimDef scale(float x, float y){
      scaleX = x;
      scaleY = y;
      return this;
    }

    public AnimDef scaleX(float f){
      scaleX = f;
      return this;
    }

    public AnimDef scaleY(float f){
      scaleY = f;
      return this;
    }

    public AnimDef rotation(float f){
      rotation = f;
      return this;
    }

    public AnimDef offset(float x, float y){
      offsetX = x;
      offsetY = y;
      return this;
    }

    public AnimDef offsetX(float f){
      offsetX = f;
      return this;
    }

    public AnimDef offsetY(float f){
      offsetY = f;
      return this;
    }

    public AnimDef origin(float x, float y){
      originX = x;
      originY = y;
      return this;
    }

    public AnimDef originX(float f){
      originX = f;
      return this;
    }

    public AnimDef originY(float f){
      originY = f;
      return this;
    }
  }
}
