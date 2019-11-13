import entity.Entity;
import render.Camera;
import world.Chunk;

public class Render {
     public static void renderMain(){
         Camera camera = SpaceGame.camera;
         camera.setX(SpaceGame.map.getPlayer().getX());
         camera.setY(SpaceGame.map.getPlayer().getY());

         // Drawing chunks
         for (Chunk[] xarray : SpaceGame.map.getChunks()){

             for (Chunk chunk : xarray){
                 RenderChunk.renderChunk(chunk);
             }
         }

         //Drawing entities
         for (Entity entity : SpaceGame.map.getEntities()){
             RenderPlayer.renderPlayer(entity);
         }

         //Drawing text
         RenderText.renderText("SPACE GAME 0.0.1",-12,8,0.5f);
    }
}
