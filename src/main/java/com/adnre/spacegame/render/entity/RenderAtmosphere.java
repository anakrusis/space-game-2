package com.adnre.spacegame.render.entity;

import com.adnre.spacegame.entity.body.Body;
import com.adnre.spacegame.entity.body.BodyAtmosphere;
import com.adnre.spacegame.entity.body.BodyPlanet;
import com.adnre.spacegame.render.Camera;
import com.adnre.spacegame.render.Textures;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;

public class RenderAtmosphere {
    public static void renderAtmosphere(Body body, Camera camera){
        double camX = camera.getX();
        double camY = camera.getY();
        double camZoom = camera.getZoom();

        double[] absPoints = body.getAbsolutePoints();
        BodyPlanet planet = (BodyPlanet)((BodyAtmosphere)body).getDependentBody();
        float density = planet.getAtmosphericDensity();

        double pointx, pointy;
        double next_x, next_y;

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA);

        glBegin(GL_TRIANGLES);
        for (int i = 0; i < absPoints.length; i += 2){

            pointx = absPoints[i];
            pointy = absPoints[i + 1];
            next_x = absPoints[(i + 2) % absPoints.length];
            next_y = absPoints[(i + 3) % absPoints.length];

            glColor4d(body.getColor()[0], body.getColor()[1], body.getColor()[2], 0d);
            glVertex2d(camZoom * (pointx - camX), camZoom * (pointy - camY));

            glVertex2d(camZoom * (next_x - camX), camZoom * (next_y - camY));

            glColor4d(body.getColor()[0], body.getColor()[1], body.getColor()[2], density);
            glVertex2d(camZoom * (body.getX() - camX), camZoom * (body.getY() - camY));

        }
        glEnd();
        glDisable(GL_BLEND);
    }
}
