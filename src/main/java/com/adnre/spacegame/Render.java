package com.adnre.spacegame;

import com.adnre.spacegame.entity.Entity;
import com.adnre.spacegame.entity.EntityBuilding;
import com.adnre.spacegame.entity.EntityPlayer;
import com.adnre.spacegame.gui.TextBox;
import com.adnre.spacegame.gui.TextBoxHotbarItem;
import com.adnre.spacegame.render.*;
import com.adnre.spacegame.render.entity.RenderBuilding;
import com.adnre.spacegame.render.entity.RenderParticle;
import com.adnre.spacegame.render.entity.RenderPlayer;
import com.adnre.spacegame.util.MathHelper;
import com.adnre.spacegame.util.Reference;
import com.adnre.spacegame.world.Chunk;
import com.adnre.spacegame.world.Map;

public class Render {
     public static void renderMain(Map map){
         Camera camera = SpaceGame.camera;

         // Camera follows player if it exists
         if (SpaceGame.map.getPlayer() != null){
             camera.setX(SpaceGame.map.getPlayer().getX());
             camera.setY(SpaceGame.map.getPlayer().getY());
         }

         // Drawing entities
         for (Entity entity : SpaceGame.map.getEntities()){
             if (entity instanceof EntityPlayer) {

             }else if (entity instanceof EntityBuilding){
                 RenderBuilding.renderBuilding(entity,camera);
             }else{
                 RenderParticle.renderParticle(entity, camera);
             }
         }
         if (map.getPlayer() != null){
             RenderPlayer.renderPlayer(map.getPlayer(), camera);
         }

         // Drawing chunks
         for (Chunk[] xarray : SpaceGame.map.getChunks()){

             for (Chunk chunk : xarray){

                 if (camera.getChunk() != null){
                     int cameraChunkX = camera.getChunk().getX();
                     int cameraChunkY = camera.getChunk().getY();

                     int chebyschev = MathHelper.chebyshev(chunk.getX(), chunk.getY(),cameraChunkX,cameraChunkY);

                     if (chebyschev <= Reference.RENDER_DISTANCE){
                         RenderChunk.renderChunk(chunk, camera);
                     }
                 }
             }
         }

         // Drawing textBoxes, gui stuff
         for (int i = 0; i < SpaceGame.guiElements.size(); i++){
             TextBox tx = SpaceGame.guiElements.get(i);
             if (tx instanceof TextBoxHotbarItem && map.getPlayer() != null){
                RenderHotbarItem.renderHotbarItem(tx, map.getPlayer());
             }else{
                 RenderTextBox.renderTextBox(tx);
             }
         }

         // Drawing pause overlay
         if (SpaceGame.isPaused()){
             RenderOverlay.renderOverlay();
             RenderText.renderText("Paused", -6, 4, 2, new float[]{0, 0, 0}, true);
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

         if (SpaceGame.map.getPlayer() != null){
             EntityPlayer player = SpaceGame.map.getPlayer();

             if (player.getChunk() != null){
                 RenderText.renderText(player.getChunk().getX() + "X", 15, -2, 1.0f);
                 RenderText.renderText(player.getChunk().getY() + "Y", 15, -3, 1.0f);
             }
             RenderText.renderText("$" + player.getMoney(), -12, 6, 0.45f);
         }
         //RenderPlayer.renderPlayer(SpaceGame.map.getCursor(), camera);
     }
}
