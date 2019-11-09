import static org.lwjgl.opengl.GL11.*;

public class RenderPlayer {

    public static void renderPlayer(Entity entity){
        Camera camera = Main.camera;
        double camX = camera.getX();
        double camY = camera.getY();
        double camZoom = camera.getZoom();

        double entx = entity.getX();
        double enty = entity.getY();
        float entdir = entity.getDir();

        glBegin(GL_LINE_LOOP);

        double point1x = camera.rotX(entdir,-0.5d,0.4d) + entx;
        double point1y = camera.rotY(entdir,-0.5d,0.4d) + enty;

        double point2x = camera.rotX(entdir,0.8d,0.0d) + entx;
        double point2y = camera.rotY(entdir,0.8d,0.0d) + enty;

        double point3x = camera.rotX(entdir,-0.5d,-0.4d) + entx;
        double point3y = camera.rotY(entdir,-0.5d,-0.4d) + enty;

        glVertex2d( camZoom * (point1x - camX), camZoom * (point1y - camY));
        glVertex2d( camZoom * (point2x - camX), camZoom * (point2y - camY));
        glVertex2d( camZoom * (point3x - camX), camZoom * (point3y - camY));

        glEnd();
    }
}
