package com.adnre.spacegame.render;

import com.adnre.spacegame.SpaceGame;
import com.adnre.spacegame.entity.Entity;
import com.adnre.spacegame.entity.EntityBomb;
import com.adnre.spacegame.entity.building.EntityBuilding;
import com.adnre.spacegame.entity.EntityPlayer;
import com.adnre.spacegame.gui.TextBox;
import com.adnre.spacegame.gui.TextBoxHotbarItem;
import com.adnre.spacegame.render.entity.RenderBuilding;
import com.adnre.spacegame.render.entity.RenderParticle;
import com.adnre.spacegame.render.entity.RenderPlayer;
import com.adnre.spacegame.render.gui.RenderHotbarItem;
import com.adnre.spacegame.render.gui.RenderOverlay;
import com.adnre.spacegame.render.gui.RenderText;
import com.adnre.spacegame.render.gui.RenderTextBox;
import com.adnre.spacegame.util.MathHelper;
import com.adnre.spacegame.util.Reference;
import com.adnre.spacegame.world.Chunk;
import com.adnre.spacegame.world.World;

public class Render {
     public static void renderMain(World world){
         Camera camera = SpaceGame.camera;

         // Camera follows player if it exists
         if (SpaceGame.world.getPlayer() != null){
             camera.setX(SpaceGame.world.getPlayer().getX());
             camera.setY(SpaceGame.world.getPlayer().getY());
         }

         // Drawing entities
         for (Entity entity : SpaceGame.world.getEntities().values()){
             if (entity instanceof EntityPlayer) {

             }else if (entity instanceof EntityBuilding || entity instanceof EntityBomb){
                 RenderBuilding.renderBuilding(entity,camera);
             }else{
                 RenderParticle.renderParticle(entity, camera);
             }
         }
         if (world.getPlayer() != null){
             RenderPlayer.renderPlayer(world.getPlayer(), camera);
         }

         // Drawing chunks
         for (Chunk[] xarray : SpaceGame.world.getChunks()){

             for (Chunk chunk : xarray){

                 if (camera.getChunk() != null && chunk != null){
                     int cameraChunkX = camera.getChunk().getX();
                     int cameraChunkY = camera.getChunk().getY();

                     // On the map screen the render distance is higher
                     if ( camera.getZoom() < Reference.MAP_SCREEN_THRESHOLD ){
                         int chebyschev = MathHelper.chebyshev(chunk.getX(), chunk.getY(),cameraChunkX,cameraChunkY);
                         if ((chebyschev <= Reference.RENDER_DISTANCE + 1)){
                             RenderChunk.renderChunk(chunk, camera);
                         }
                     }else{
                         if ((camera.getChunk().getX() == chunk.getX() && camera.getChunk().getY() == chunk.getY() )){
                             RenderChunk.renderChunk(chunk, camera);
                         }
                     }
                 }
             }
         }

         // Drawing pause overlay
         if (SpaceGame.isPaused()){
             RenderOverlay.renderOverlay();
             RenderText.renderText("Paused", -6, 4, 2, new float[]{0, 0, 0}, true);
         }

         // Drawing textBoxes, gui stuff
         for (int i = 0; i < SpaceGame.guiElements.size(); i++){
             TextBox tx = SpaceGame.guiElements.get(i);
             if (tx instanceof TextBoxHotbarItem && world.getPlayer() != null){
                RenderHotbarItem.renderHotbarItem(tx, world.getPlayer());
             }else{
                 RenderTextBox.renderTextBox(tx);
             }
         }

         // Drawing text
         String renderstring = Reference.GAME_NAME + " " + Reference.VERSION;

         if (Reference.DEASTL_MODE){
             renderstring += " (DEASTL MODE)";
         }else if (Reference.DEBUG_MODE) {
             renderstring += " (DEBUG)";
         }

         RenderText.renderText(renderstring,-12,8,0.6f);
         //RenderText.renderText("WASD to move - QE to zoom - P to self-destruct \nClick to place factories ($50)",-12,-8.5f,0.45f);

         if (SpaceGame.world.getPlayer() != null){
             EntityPlayer player = SpaceGame.world.getPlayer();

             if (player.getChunk() != null){
                 //RenderText.renderText(player.getChunk().getX() + "X", 15, -2, 1.0f);
                 //RenderText.renderText(player.getChunk().getY() + "Y", 15, -3, 1.0f);
             }
             RenderText.renderText("$" + player.getMoney(), -12, 6, 0.45f);

             String fueltext = "Fuel: " + Math.round(player.getFuel()) + "/" + Math.round(player.getFuelCapacity());
             RenderText.renderText(fueltext, -12, 5.5f, 0.45f);

             //if (player.isGrounded()){
                 RenderText.renderText(player.getToolProgress() + " ", 6, 6, 0.45f);
             //}
         }

         RenderText.renderText("Seed: " + world.getSeed(), 6, 8, 0.45f);
         RenderText.renderText("Time: " + SpaceGame.world.mapTime, 6, 7, 0.45f);

         //RenderPlayer.renderPlayer(SpaceGame.map.getCursor(), camera);
     }
}
