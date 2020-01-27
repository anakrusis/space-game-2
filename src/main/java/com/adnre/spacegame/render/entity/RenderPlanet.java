package com.adnre.spacegame.render.entity;

import com.adnre.spacegame.entity.body.Body;
import com.adnre.spacegame.entity.body.BodyPlanet;
import com.adnre.spacegame.render.Camera;
import com.adnre.spacegame.render.Textures;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;

public class RenderPlanet {
    public static void renderPlanet(Body body, Camera camera){
        BodyPlanet planet = (BodyPlanet)body;
        double camX = camera.getX();
        double camY = camera.getY();
        double camZoom = camera.getZoom();

        double[] absPoints = planet.getAbsolutePoints();
        float[] texpoints = new float[]{
                0, 0,
                1f, 0,
                1f, 1f,
                0, 1f
        };

        Textures.grass.bind();

        glColor3d(body.getColor()[0], body.getColor()[1], body.getColor()[2]);
        double pointx, pointy;
        double next_x, next_y;

        glBegin(GL_TRIANGLES);
        for (int i = 0; i < absPoints.length; i += 2){

            pointx = absPoints[i];
            pointy = absPoints[i + 1];
            next_x = absPoints[(i + 2) % absPoints.length];
            next_y = absPoints[(i + 3) % absPoints.length];

            glVertex2d(camZoom * (pointx - camX), camZoom * (pointy - camY));
            glTexCoord2f(texpoints[i % texpoints.length], texpoints[ (i + 1) % texpoints.length ]);

            glVertex2d(camZoom * (next_x - camX), camZoom * (next_y - camY));
            glTexCoord2f(texpoints[i % texpoints.length], texpoints[ (i + 1) % texpoints.length ]);

            glVertex2d(camZoom * (planet.getX() - camX), camZoom * (planet.getY() - camY));
            glTexCoord2f(texpoints[i % texpoints.length], texpoints[ (i + 1) % texpoints.length ]);

        }
        glEnd();
    }
}
