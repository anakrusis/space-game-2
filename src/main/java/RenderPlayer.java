import entity.Entity;
import render.Camera;
import util.MathHelper;
import util.Reference;

import static org.lwjgl.opengl.GL11.*;

public class RenderPlayer {

    public static void renderPlayer(Entity entity){
        Camera camera = SpaceGame.camera;
        double camX = camera.getX();
        double camY = camera.getY();
        double camZoom = camera.getZoom();

        double[] abspoints = entity.getAbsolutePoints();

        glBegin(GL_LINE_LOOP);
        glColor3d(0d,1d,1d);

        double cx;
        double cy;

        for (int i = 0; i < abspoints.length; i += 2){
            cx = camZoom * (abspoints[i] - camX);
            cy = camZoom * (abspoints[i + 1] - camY);
            if (camZoom < Reference.MAP_SCREEN_THRESHOLD){
                cx *= 40;
                cy *= 40;
            }
            glVertex2d(cx , cy);
        }

        glEnd();
    }
}
