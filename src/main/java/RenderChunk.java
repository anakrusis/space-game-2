import entity.Body;
import util.Reference;
import world.Chunk;

import static org.lwjgl.opengl.GL11.*;

public class RenderChunk {
    public static void renderChunk(Chunk chunk){
        double camX = SpaceGame.camera.getX();
        double camY = SpaceGame.camera.getY();
        double camZoom = SpaceGame.camera.getZoom();

        int cbs = Reference.CHUNK_SIZE;

        // Drawing the main chunk grid
        glBegin(GL_LINE_LOOP);
        glColor3d(0.5d,0.5d,0.5d);
        glVertex2d(camZoom * (cbs * (0d + chunk.getX()) - camX), camZoom * (cbs * (0d + chunk.getY()) - camY));
        glVertex2d(camZoom * (cbs * (0d + chunk.getX()) - camX), camZoom * (cbs * (1d + chunk.getY()) - camY));
        glVertex2d(camZoom * (cbs * (1d + chunk.getX()) - camX), camZoom * (cbs * (1d + chunk.getY()) - camY));
        glVertex2d(camZoom * (cbs * (1d + chunk.getX()) - camX), camZoom * (cbs * (0d + chunk.getY()) - camY));
        glEnd();

        // Drawing all the bodies inside the chunk
        for (Body body : chunk.getBodies()){
            RenderStar.renderStar(body, true);
        }
    }
}
