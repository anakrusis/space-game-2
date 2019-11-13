import entity.Body;
import render.Camera;
import util.MathHelper;

import static org.lwjgl.opengl.GL11.*;

public class RenderStar {
    public static void renderStar(Body star, boolean filled){
        Camera camera = SpaceGame.camera;
        double camX = camera.getX();
        double camY = camera.getY();
        double camZoom = camera.getZoom();

//        double entx = star.getX();
//        double enty = star.getY();
//        float entdir = star.getDir();
//        float entrad = star.getRadius();
        double[] absPoints = star.getAbsolutePoints();

        if (filled) {
            glBegin(GL_POLYGON);
        }else{
            glBegin(GL_LINE_LOOP);
        }
        glColor3d(star.getColor()[0], star.getColor()[1], star.getColor()[2]);

        for (int i = 0; i < absPoints.length; i += 2){
//            double angle = entdir + (i * (2 * Math.PI) / terrain.length);
//
//            double pointx = MathHelper.rotX((float) angle, entrad + terrain[i], 0.0d) + entx;
//            double pointy = MathHelper.rotY((float) angle, entrad + terrain[i], 0.0d) + enty;
            double pointx = absPoints[i];
            double pointy = absPoints[i + 1];

            glVertex2d(camZoom * (pointx - camX), camZoom * (pointy - camY));
        }
        glEnd();
    }
}
