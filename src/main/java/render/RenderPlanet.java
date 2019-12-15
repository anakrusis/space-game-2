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

        RenderStar.renderStar(body, true, camera);

        glBegin(GL_POLYGON);
        // Stone underneath
        glColor3d(planet.getStoneColor()[0], planet.getStoneColor()[1], planet.getStoneColor()[2]);
        for (int i = 0; i < stonePoints.length; i += 2){

            double pointx = stonePoints[i];
            double pointy = stonePoints[i + 1];

            glVertex2d(camZoom * (pointx - camX), camZoom * (pointy - camY));
        }
        glEnd();
    }
}
