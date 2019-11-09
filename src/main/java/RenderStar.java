import static org.lwjgl.opengl.GL11.*;

public class RenderStar {
    public static void renderStar(BodyStar star){
        Camera camera = Main.camera;
        double camX = camera.getX();
        double camY = camera.getY();
        double camZoom = camera.getZoom();

        double entx = star.getX();
        double enty = star.getY();
        float entdir = star.getDir();
        float entrad = star.getRadius();

        glBegin(GL_POLYGON);

        for (int i = 0; i < 8; i++){
            double pointx = camera.rotX((float)(entdir + (i * (2 * Math.PI) / 8f)), entrad, 0.0d) + entx;
            double pointy = camera.rotY((float)(entdir + (i * (2 * Math.PI) / 8f)), entrad, 0.0d) + enty;

            glVertex2d(camZoom * (pointx - camX), camZoom * (pointy - camY));
        }
        glEnd();
    }
}
