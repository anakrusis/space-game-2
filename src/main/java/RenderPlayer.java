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

        double entx = entity.getX();
        double enty = entity.getY();
        float entdir = entity.getDir();

        glBegin(GL_LINE_LOOP);
        glColor3d(0d,1d,1d);

        double point1x = MathHelper.rotX(entdir,-0.5d,0.4d) + entx;
        double point1y = MathHelper.rotY(entdir,-0.5d,0.4d) + enty;

        double point2x = MathHelper.rotX(entdir,0.8d,0.0d) + entx;
        double point2y = MathHelper.rotY(entdir,0.8d,0.0d) + enty;

        double point3x = MathHelper.rotX(entdir,-0.5d,-0.4d) + entx;
        double point3y = MathHelper.rotY(entdir,-0.5d,-0.4d) + enty;

        glVertex2d( camZoom * (point1x - camX), camZoom * (point1y - camY));
        glVertex2d( camZoom * (point2x - camX), camZoom * (point2y - camY));
        glVertex2d( camZoom * (point3x - camX), camZoom * (point3y - camY));

        glEnd();
    }
}
