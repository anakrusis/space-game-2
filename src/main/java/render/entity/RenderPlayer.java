package render.entity;

import entity.Entity;
import render.Camera;
import util.MathHelper;
import util.Reference;

import static org.lwjgl.opengl.GL11.*;

public class RenderPlayer {

    public static void renderPlayer(Entity entity, Camera camera){

        double camX = camera.getX();
        double camY = camera.getY();
        double camZoom = camera.getZoom();

        double[] abspoints = entity.getAbsolutePoints();
        float[] vbo_vertices = new float[abspoints.length];
        double cx;
        double cy;

        glBegin(GL_POLYGON);
        glColor3d(0d,1d,1d);

        for (int i = 0; i < abspoints.length; i += 2){
            cx = camZoom * (abspoints[i] - camX);
            cy = camZoom * (abspoints[i + 1] - camY);
            if (camZoom < Reference.MAP_SCREEN_THRESHOLD){
                cx *= 80;
                cy *= 80;
            }
            vbo_vertices[i] = (float) cx;
            vbo_vertices[i + 1] = (float) cy;

            if (entity.ticksExisted % 20 > 10 || camZoom > Reference.MAP_SCREEN_THRESHOLD) {
                glVertex2d(cx, cy);
            }
        }

        glEnd();
        // Culling when blinking on the map screen
        //if (entity.ticksExisted % 20 > 10 || camZoom > Reference.MAP_SCREEN_THRESHOLD){
            //Model playerModel = new Model(vbo_vertices, new float[]{});

            //playerModel.render();
        //}
    }
}
