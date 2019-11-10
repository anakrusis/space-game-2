import static org.lwjgl.opengl.GL11.*;

public class Render {
     public static void renderMain(){
         Camera camera = Main.camera;
         camera.setX(Main.map.getPlayer().getX());
         camera.setY(Main.map.getPlayer().getY());

         // Drawing chunks
         for (Chunk[] xarray : Main.map.getChunks()){

             for (Chunk chunk : xarray){
                 RenderChunk.renderChunk(chunk);
             }
         }

         //Drawing entities
         for (Entity entity : Main.map.getEntities()){
             RenderPlayer.renderPlayer(entity);
         }
    }
}
