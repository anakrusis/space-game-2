package com.adnre.spacegame.render.entity;

import com.adnre.spacegame.entity.*;
import com.adnre.spacegame.entity.building.BuildingApartment;
import com.adnre.spacegame.entity.building.EntityBuilding;
import com.adnre.spacegame.render.Camera;
import com.adnre.spacegame.render.Textures;
import com.adnre.spacegame.util.RandomUtil;
import com.adnre.spacegame.util.Reference;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;

public class RenderBuilding{
    public static void renderBuilding(Entity entity, Camera camera){
        double camX = camera.getX();
        double camY = camera.getY();
        double camZoom = camera.getZoom();

        if (camZoom > Reference.MAP_SCREEN_THRESHOLD){
            double[] abspoints = entity.getAbsolutePoints();
            float[] texpoints = new float[]{
                0, 0,
                1f, 0,
                1f, 1f,
                0, 1f
            };

            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA);

            if (entity instanceof EntityBomb){
                glColor4d(RandomUtil.fromRangeF(0, 1),RandomUtil.fromRangeF(0, 1),RandomUtil.fromRangeF(0, 1),1d);
            }else{
                glColor4f(entity.getColor()[0],entity.getColor()[1],entity.getColor()[2],1f);
            }

            entity.getTexture().bind();

            glBegin(GL_QUADS);

            for (int i = 0; i < abspoints.length; i += 2){
                glVertex2d( camZoom * (abspoints[i] - camX), camZoom * (abspoints[i + 1] - camY));
                glTexCoord2f(texpoints[i], texpoints[i + 1]);
            }

            glEnd();
            if (entity instanceof EntityBuilding){
                if (((EntityBuilding) entity).getWindowTexture() != null && ((EntityBuilding) entity).isActive()){
                    glColor4f(1f,1f,1f,1f);
                    ((EntityBuilding) entity).getWindowTexture().bind();

                    glBegin(GL_QUADS);

                    for (int i = 0; i < abspoints.length; i += 2){
                        glVertex2d( camZoom * (abspoints[i] - camX), camZoom * (abspoints[i + 1] - camY));
                        glTexCoord2f(texpoints[i], texpoints[i + 1]);
                    }

                    glEnd();
                }
            }

            glDisable(GL_BLEND);
        }
    }

}
