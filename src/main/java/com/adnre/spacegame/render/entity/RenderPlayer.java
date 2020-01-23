package com.adnre.spacegame.render.entity;

import com.adnre.spacegame.SpaceGame;
import com.adnre.spacegame.entity.Entity;
import com.adnre.spacegame.entity.EntityPlayer;
import com.adnre.spacegame.item.ItemStack;
import com.adnre.spacegame.item.Items;
import com.adnre.spacegame.render.Camera;
import com.adnre.spacegame.util.Reference;

import static org.lwjgl.opengl.GL11.*;

public class RenderPlayer {

    public static void renderPlayer(Entity entity, Camera camera){

        EntityPlayer player = (EntityPlayer)entity;
        double camX = camera.getX();
        double camY = camera.getY();
        double camZoom = camera.getZoom();

        double[] abspoints = entity.getAbsolutePoints();
        double cx;
        double cy;

        // This renders the laser beam from the mining laser!
        ItemStack stack = player.getCurrentItemStack();
        // Todo ActiveToolType method in player
        if (player.isToolActive() && stack != null){
            if (stack.getItem().getId() == Items.ITEM_MINING_LASER.getId()){
                glColor3d(1.0f, 0.0f, 0.0f);
                double cursorX, cursorY;
                double playerX, playerY;
                cursorX = camZoom * (SpaceGame.world.getCursor().getX() - camX);
                cursorY = camZoom * (SpaceGame.world.getCursor().getY() - camY);
                playerX = camZoom * (player.getX() - camX);
                playerY = camZoom * (player.getY() - camY);

                glBegin(GL_LINES);
                glVertex2d(cursorX, cursorY);
                glVertex2d(playerX, playerY);
                glEnd();
            }
        }

        // This renders the player ship as a filled blue dorito
        glBegin(GL_POLYGON);
        glColor3d(player.getColor()[0], player.getColor()[1], player.getColor()[2]);

        for (int i = 0; i < abspoints.length; i += 2){
            cx = camZoom * (abspoints[i] - camX);
            cy = camZoom * (abspoints[i + 1] - camY);
            if (camZoom < Reference.MAP_SCREEN_THRESHOLD){
                cx *= 80;
                cy *= 80;
            }

            if (entity.ticksExisted % 20 > 10 || camZoom > Reference.MAP_SCREEN_THRESHOLD) {
                glVertex2d(cx, cy);
            }
        }
        glEnd();
    }
}
