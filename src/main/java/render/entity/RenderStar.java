package render.entity;

import entity.Body;
import entity.body.BodyStar;
import render.Camera;
import render.RenderText;
import util.Reference;

import static org.lwjgl.opengl.GL11.*;

public class RenderStar {
    public static void renderStar(Body star, boolean filled, Camera camera){
        double camX = camera.getX();
        double camY = camera.getY();
        double camZoom = camera.getZoom();

        double[] abspoints = star.getAbsolutePoints();
        //Model starmodel = star.getModel();

        if (filled) {
//            float[] vbo_vertices = new float[abspoints.length * 3];
//
//            // Center x, this x, next x (used to construct tris)
//            float cx, cy;
//            float tx, ty;
//            float nx, ny;
//
//            for (int i = 0; i < abspoints.length; i += 2) {
//                tx = (float) (camZoom * (abspoints[i] - camX));
//                ty = (float) (camZoom * (abspoints[i + 1] - camY));
//
//                nx = (float) (camZoom * (abspoints[ (i + 2) % abspoints.length ] - camX));
//                ny = (float) (camZoom * (abspoints[ (i + 3) % abspoints.length ] - camY));
//
//                cx = (float) (camZoom * (star.getX() - camX));
//                cy = (float) (camZoom * (star.getY() - camY));
//
//                vbo_vertices[3 * i] = tx;
//                vbo_vertices[3 * i + 1] = ty;
//
//                vbo_vertices[3 * i + 2] = nx;
//                vbo_vertices[3 * i + 3] = ny;
//
//                vbo_vertices[3 * i + 4] = cx;
//                vbo_vertices[3 * i + 5] = cy;
//            }
//
//            starmodel.setVertices(vbo_vertices);
//            glColor3d(star.getColor()[0], star.getColor()[1], star.getColor()[2]);
//            starmodel.render();
            glBegin(GL_POLYGON);
        // Old fashioned gl lines
        }else {
            glBegin(GL_LINE_LOOP);
        }
        glColor3d(star.getColor()[0], star.getColor()[1], star.getColor()[2]);

        for (int i = 0; i < abspoints.length; i += 2){

            double pointx = abspoints[i];
            double pointy = abspoints[i + 1];

            glVertex2d(camZoom * (pointx - camX), camZoom * (pointy - camY));
        }
        glEnd();


        // Map rendering with names of stars
        if (camZoom < Reference.MAP_SCREEN_THRESHOLD && (star instanceof BodyStar)){
            double x = camZoom * (star.getX() - camX);
            double y = camZoom * (star.getY() - camY) + 0.5;
            if (star == star.getMap().getHomeStar()){
                RenderText.renderText((char) 0x7f + " " + star.getName(), (float)x, (float)y, 0.25f);
            }else{
                RenderText.renderText(star.getName(), (float)x, (float)y, 0.25f);
            }
        }
    }
}
