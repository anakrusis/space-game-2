package render;

import entity.Body;
import entity.BodyPlanet;
import entity.BodyStar;
import render.Camera;
import util.MathHelper;
import util.Reference;

import static org.lwjgl.opengl.GL11.*;

public class RenderStar {
    public static void renderStar(Body star, boolean filled, Camera camera){
        double camX = camera.getX();
        double camY = camera.getY();
        double camZoom = camera.getZoom();

        double[] absPoints = star.getAbsolutePoints();

        if (filled) {
            glBegin(GL_POLYGON);
        }else{
            glBegin(GL_LINE_LOOP);
        }
        glColor3d(star.getColor()[0], star.getColor()[1], star.getColor()[2]);

        for (int i = 0; i < absPoints.length; i += 2){

            double pointx = absPoints[i];
            double pointy = absPoints[i + 1];

            glVertex2d(camZoom * (pointx - camX), camZoom * (pointy - camY));
        }
        glEnd();

        // Map rendering with names of stars
        if (camZoom < Reference.MAP_SCREEN_THRESHOLD && (star instanceof BodyStar)){
            double x = camZoom * (star.getX() - camX);
            double y = camZoom * (star.getY() - camY) + 0.5;
            RenderText.renderText(star.getName(), (float)x, (float)y, 0.25f);
        }
    }
}
