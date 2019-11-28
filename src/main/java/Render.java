import entity.Entity;
import entity.EntityBuilding;
import entity.EntityPlayer;
import render.*;
import util.CollisionUtil;
import util.MathHelper;
import util.Reference;
import world.Chunk;
import world.Map;

public class Render {
     public static void renderMain(Map map){
         Camera camera = SpaceGame.camera;

         if (SpaceGame.map.getPlayer() != null){
             camera.setX(SpaceGame.map.getPlayer().getX());
             camera.setY(SpaceGame.map.getPlayer().getY());
         }

         //Drawing entities
         for (Entity entity : SpaceGame.map.getEntities()){
             if (entity instanceof EntityPlayer) {
                 RenderPlayer.renderPlayer(entity, camera);
             }else if (entity instanceof EntityBuilding){
                 RenderBuilding.renderBuilding(entity,camera);
             }else{
                 RenderParticle.renderParticle(entity, camera);
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

                         if (chebyschev <= Reference.RENDER_DISTANCE){
                             RenderChunk.renderChunk(chunk, camera);
                         }
                     }
                 }else{
                     RenderChunk.renderChunk(chunk, camera);
                 }
             }
         }

         //Drawing text
         String renderstring = Reference.GAME_NAME + " " + Reference.VERSION;

         if (Reference.DEASTL_MODE){
             renderstring += " (DEASTL MODE)";
         }else if (Reference.DEBUG_MODE) {
             renderstring += " (DEBUG)";
         }

         RenderText.renderText(renderstring,-12,8,0.6f);
         RenderText.renderText("WASD to move - QE to zoom - P to self-destruct",-12,-8.5f,0.45f);
         RenderText.renderText("Click to place factories ($50)",-12,-9.5f,0.45f);

         if (SpaceGame.map.getPlayer() != null){
             EntityPlayer player = SpaceGame.map.getPlayer();
             if (player.isGrounded()){
                 RenderText.renderText(player.getGroundedBody().getName(),
                          10, 5, 0.3f);
             }
             if (player.getChunk() != null){
                 RenderText.renderText(player.getChunk().getX() + "X", 11, 3, 1.0f);
                 RenderText.renderText(player.getChunk().getY() + "Y", 11, 2, 1.0f);
             }
             RenderText.renderText(map.getCursor().getX() + "",
                     10, 8, 0.5f);
             RenderText.renderText(map.getCursor().getY() + "",
                     10, 7.5f, 0.5f);

             RenderText.renderText("$" + player.getMoney(), -12, 6, 0.45f);
         }

         RenderPlayer.renderPlayer(SpaceGame.map.getCursor(), camera);
     }
}
