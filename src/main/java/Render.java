import static org.lwjgl.opengl.GL11.*;

public class Render {
     public static void renderMain(){
         Camera camera = Main.camera;
         double camX = camera.getX();
         double camY = camera.getY();

         for (Chunk[] xarray : Main.map.getChunks()){

             for (Chunk chunk : xarray){

                 glBegin(GL_LINE_LOOP);

                 glVertex2d(16 * (0d + chunk.getX()) - camX, 16 * (0d + chunk.getY()) - camY);
                 glVertex2d(16 * (0d + chunk.getX()) - camX, 16 * (1d + chunk.getY()) - camY);
                 glVertex2d(16 * (1d + chunk.getX()) - camX, 16 * (1d + chunk.getY()) - camY);
                 glVertex2d(16 * (1d + chunk.getX()) - camX, 16 * (0d + chunk.getY()) - camY);
                 glEnd();
             }
         }
    }
}
