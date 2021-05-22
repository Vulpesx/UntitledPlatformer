package me.potaytoprograms.api.util;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RenderUtil {

    public static TextureRegion[] getRegions(TextureRegion img, int sX, int sY, int width, int height, int fX, int fY){
        TextureRegion[] regions = new TextureRegion[fX + fY - 1];

        for(int y = 0; y < fY; y++){
            for(int x = 0; x < fX; x++){
                if(x+y >= fX + fY - 1) break;
                regions[y+x] = new TextureRegion(img, x * width + sX, y * height + sY, width, height);
            }
        }
        return regions;
    }
}
