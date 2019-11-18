import entity.Entity;
import entity.EntityParticle;
import entity.EntityPlayer;
import render.Camera;
import util.MathHelper;
import util.Reference;
import world.Chunk;

public class Render {
     public static void renderMain(){
         Camera camera = SpaceGame.camera;
         if (SpaceGame.map.getPlayer() != null){
             camera.setX(SpaceGame.map.getPlayer().getX());
             camera.setY(SpaceGame.map.getPlayer().getY());
         }

         //Drawing entities
         for (Entity entity : SpaceGame.map.getEntities()){
             if (entity instanceof EntityPlayer){
                 RenderPlayer.renderPlayer(entity);
             }else if (entity instanceof EntityParticle){
                 RenderParticle.renderParticle(entity);
             }

         }

         // Drawing chunks
         for (Chunk[] xarray : SpaceGame.map.getChunks()){

             for (Chunk chunk : xarray){

                 if (SpaceGame.map.getPlayer() != null){
                     if (SpaceGame.map.getPlayer().getChunk() != null){

                         int playerChunkX = SpaceGame.map.getPlayer().getChunk().getX();
                         int playerChunkY = SpaceGame.map.getPlayer().getChunk().getY();
                         int chebyschev = MathHelper.chebyshev(chunk.getX(), chunk.getY(),playerChunkX,playerChunkY);

                         if (chebyschev <= 1){
                             RenderChunk.renderChunk(chunk);
                         }
                     }
                 }else{
                     RenderChunk.renderChunk(chunk);
                 }
             }
         }

         //Drawing text
         RenderText.renderText(Reference.GAME_NAME + " " + Reference.VERSION,-12,8,0.6f);
         RenderText.renderText("WASD to move - QE to zoom - P to self-destruct",-12,-8.5f,0.45f);

         if (SpaceGame.map.getPlayer() != null){
             if (SpaceGame.map.getPlayer().isGrounded()){
                 RenderText.renderText(SpaceGame.map.getPlayer().getGroundedBody().getName(),
                          10, 5, 0.3f);
             }
             if (SpaceGame.map.getPlayer().getChunk() != null){
                 RenderText.renderText(SpaceGame.map.getPlayer().getChunk().getX() + "X", 11, 3, 1.0f);
                 RenderText.renderText(SpaceGame.map.getPlayer().getChunk().getY() + "Y", 11, 2, 1.0f);
             }
         }
     }
}
