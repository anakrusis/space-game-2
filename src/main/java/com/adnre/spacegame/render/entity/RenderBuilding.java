package com.adnre.spacegame.render.entity;

import com.adnre.spacegame.entity.*;
import com.adnre.spacegame.render.Camera;
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
            glColor4d(1d,1d,1d,1d);

            if (entity instanceof EntityBuilding){
                ((EntityBuilding) entity).getTexture().bind();
            }

            glBegin(GL_QUADS);

            for (int i = 0; i < abspoints.length; i += 2){
                glVertex2d( camZoom * (abspoints[i] - camX), camZoom * (abspoints[i + 1] - camY));
                glTexCoord2f(texpoints[i], texpoints[i + 1]);
            }

            glEnd();
            glDisable(GL_BLEND);
        }
    }

}
