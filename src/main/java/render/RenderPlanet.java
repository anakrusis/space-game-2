package render;

import entity.Body;
import entity.body.BodyPlanet;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;

public class RenderPlanet {
    public static void renderPlanet(Body body, Camera camera){
        BodyPlanet planet = (BodyPlanet)body;
        double camX = camera.getX();
        double camY = camera.getY();
        double camZoom = camera.getZoom();

        double[] absPoints = planet.getAbsolutePoints();
        double[] stonePoints = planet.getStonePoints();

        double[] triSlice;

        // Surface
        glBegin(GL_POLYGON);
        glColor3d(planet.getSurfaceColor()[0], planet.getSurfaceColor()[1], planet.getSurfaceColor()[2]);

        for (int i = 0; i < absPoints.length; i += 2){

            double pointx = absPoints[i];
            double pointy = absPoints[i + 1];

            glVertex2d(camZoom * (pointx - camX), camZoom * (pointy - camY));
        }
        glEnd();

        glBegin(GL_POLYGON);
        // Stone underneath
        glColor3d(planet.getColor()[0], planet.getColor()[1], planet.getColor()[2]);
        for (int i = 0; i < stonePoints.length; i += 2){

            double pointx = stonePoints[i];
            double pointy = stonePoints[i + 1];

            glVertex2d(camZoom * (pointx - camX), camZoom * (pointy - camY));
        }
        glEnd();
    }
}
