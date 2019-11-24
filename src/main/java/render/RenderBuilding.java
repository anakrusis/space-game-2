package render;

import entity.*;
import util.Reference;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;

public class RenderBuilding{
    public static void renderBuilding(Entity entity, Camera camera){
        double camX = camera.getX();
        double camY = camera.getY();
        double camZoom = camera.getZoom();

        if (camZoom > Reference.MAP_SCREEN_THRESHOLD){
            double[] abspoints = entity.getAbsolutePoints();

            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA);
            glColor3d(1d,1d,1d);
            if (entity instanceof BuildingFactory) {
                Textures.factory.bind();
            }

            glBegin(GL_QUADS);

            for (int i = 0; i < abspoints.length; i += 2){
                glVertex2d( camZoom * (abspoints[i] - camX), camZoom * (abspoints[i + 1] - camY));
                glTexCoord2f(i == 2 || i == 4 ? 1 : 0, i > 2 ? 1 : 0);

            }

            glEnd();
            glDisable(GL_BLEND);
        }
    }

}
