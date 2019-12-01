package render;

import entity.*;
import util.Reference;

import static org.lwjgl.opengl.GL11.*;

public class RenderParticle {
    public static void renderParticle(Entity entity, Camera camera){

        double camX = camera.getX();
        double camY = camera.getY();
        double camZoom = camera.getZoom();

        if (camZoom > Reference.MAP_SCREEN_THRESHOLD){
            double[] abspoints = entity.getAbsolutePoints();

            glBegin(GL_LINE_LOOP);
            // Todo: Inherent particle color property, just like Bodies have (Why didn't I do this earlier?)
            if (entity instanceof ParticleExplosion){
                glColor3d(1d,0.5d,0d);
            } else if (entity instanceof ParticleSmoke){
                glColor3d(0.35d,0.25d,0.40d);
            } else if (entity instanceof ParticleOrbit){
                glColor3d(0d,0.45d,0d);
            }

            for (int i = 0; i < abspoints.length; i += 2){
                glVertex2d( camZoom * (abspoints[i] - camX), camZoom * (abspoints[i + 1] - camY));
            }

            glEnd();
        }
    }
}
