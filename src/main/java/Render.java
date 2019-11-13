import entity.Entity;
import entity.EntityParticle;
import entity.EntityPlayer;
import render.Camera;
import world.Chunk;

public class Render {
     public static void renderMain(){
         Camera camera = SpaceGame.camera;
         if (SpaceGame.map.getPlayer() != null){
             camera.setX(SpaceGame.map.getPlayer().getX());
             camera.setY(SpaceGame.map.getPlayer().getY());
         }

         // Drawing chunks
         for (Chunk[] xarray : SpaceGame.map.getChunks()){

             for (Chunk chunk : xarray){
                 RenderChunk.renderChunk(chunk);
             }
         }

         //Drawing entities
         for (Entity entity : SpaceGame.map.getEntities()){
             if (entity instanceof EntityPlayer){
                 RenderPlayer.renderPlayer(entity);
             }else if (entity instanceof EntityParticle){
                 RenderParticle.renderParticle(entity);
             }

         }

         //Drawing text
         RenderText.renderText("Space Game 0.0.1",-12,8,0.5f);
         RenderText.renderText("WASD TO MOVE - QE TO ZOOM - P TO SELF-DESTRUCT",-12,-8.5f,0.5f);
     }
}
