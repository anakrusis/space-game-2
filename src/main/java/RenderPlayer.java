import entity.Entity;
import render.Camera;
import util.MathHelper;

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

        for (int i = 0; i < abspoints.length; i += 2){
            glVertex2d( camZoom * (abspoints[i] - camX), camZoom * (abspoints[i + 1] - camY));
        }

        glEnd();
    }
}
