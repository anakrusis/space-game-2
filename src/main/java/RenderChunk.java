import static org.lwjgl.opengl.GL11.*;

public class RenderChunk {
    public static void renderChunk(Chunk chunk){
        double camX = Main.camera.getX();
        double camY = Main.camera.getY();
        double camZoom = Main.camera.getZoom();

        int cbs = Main.CHUNK_SIZE;

        glBegin(GL_LINE_LOOP);

        // Drawing the main chunk
        glColor3d(0.5d,0.5d,0.5d);
        glVertex2d(camZoom * (cbs * (0d + chunk.getX()) - camX), camZoom * (cbs * (0d + chunk.getY()) - camY));
        glVertex2d(camZoom * (cbs * (0d + chunk.getX()) - camX), camZoom * (cbs * (1d + chunk.getY()) - camY));
        glVertex2d(camZoom * (cbs * (1d + chunk.getX()) - camX), camZoom * (cbs * (1d + chunk.getY()) - camY));
        glVertex2d(camZoom * (cbs * (1d + chunk.getX()) - camX), camZoom * (cbs * (0d + chunk.getY()) - camY));
        glEnd();

        // Drawing all the bodies inside the chunk
        for (Body body : chunk.getBodies()){
            RenderStar.renderStar(body, body instanceof BodyStar);
        }
    }
}
