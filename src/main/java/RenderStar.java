import static org.lwjgl.opengl.GL11.*;

public class RenderStar {
    public static void renderStar(Body star, boolean filled){
        Camera camera = Main.camera;
        double camX = camera.getX();
        double camY = camera.getY();
        double camZoom = camera.getZoom();

        double entx = star.getX();
        double enty = star.getY();
        float entdir = star.getDir();
        float entrad = star.getRadius();
        float[] terrain = star.getTerrain();

        if (filled) {
            glBegin(GL_POLYGON);
        }else{
            glBegin(GL_LINE_LOOP);
        }
        glColor3d(1d,1d,1d);

        for (int i = 0; i < terrain.length; i++){
            double angle = entdir + (i * (2 * Math.PI) / terrain.length);

            double pointx = camera.rotX((float) angle, entrad + terrain[i], 0.0d) + entx;
            double pointy = camera.rotY((float) angle, entrad + terrain[i], 0.0d) + enty;

            glVertex2d(camZoom * (pointx - camX), camZoom * (pointy - camY));
        }
        glEnd();
    }
}
