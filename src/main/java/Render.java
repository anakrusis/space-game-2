import static org.lwjgl.opengl.GL11.*;

public class Render {
     public static void renderMain(){
         Camera camera = Main.camera;
         camera.setX(Main.map.getPlayer().getX());
         camera.setY(Main.map.getPlayer().getY());

         double camX = camera.getX();
         double camY = camera.getY();

         // chunk box size, entity box size
         int cbs = Main.CHUNK_SIZE;
         int ebs = 1;

         // Drawing chunk grid
         for (Chunk[] xarray : Main.map.getChunks()){

             for (Chunk chunk : xarray){

                 glBegin(GL_LINE_LOOP);

                 glVertex2d(cbs * (0d + chunk.getX()) - camX, cbs * (0d + chunk.getY()) - camY);
                 glVertex2d(cbs * (0d + chunk.getX()) - camX, cbs * (1d + chunk.getY()) - camY);
                 glVertex2d(cbs * (1d + chunk.getX()) - camX, cbs * (1d + chunk.getY()) - camY);
                 glVertex2d(cbs * (1d + chunk.getX()) - camX, cbs * (0d + chunk.getY()) - camY);
                 glEnd();

                 for (Body body : chunk.getBodies()){
                     RenderPlayer.renderPlayer(body);
                 }
             }
         }

         //Drawing entities
         for (Entity entity : Main.map.getEntities()){
             RenderPlayer.renderPlayer(entity);
         }
    }
}
