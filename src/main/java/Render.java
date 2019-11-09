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
             }
         }

         //Drawing entities
         for (Entity entity : Main.map.getEntities()){
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

             glVertex2d(point1x - camX, point1y - camY);
             glVertex2d(point2x - camX, point2y - camY);
             glVertex2d(point3x - camX, point3y - camY);

//             glVertex2d(ebs * (0d + entity.getX()) - camX, ebs * (0d + entity.getY()) - camY);
//             glVertex2d(ebs * (0d + entity.getX()) - camX, ebs * (1d + entity.getY()) - camY);
//             glVertex2d(ebs * (1d + entity.getX()) - camX, ebs * (1d + entity.getY()) - camY);
//             glVertex2d(ebs * (1d + entity.getX()) - camX, ebs * (0d + entity.getY()) - camY);
             glEnd();
         }
    }
}
