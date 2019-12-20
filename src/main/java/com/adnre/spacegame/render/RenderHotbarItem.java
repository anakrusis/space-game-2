package com.adnre.spacegame.render;

import com.adnre.spacegame.entity.EntityPlayer;
import com.adnre.spacegame.gui.TextBox;
import com.adnre.spacegame.gui.TextBoxHotbarItem;
import com.adnre.spacegame.item.Item;
import com.adnre.spacegame.item.ItemStack;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glColor4d;

public class RenderHotbarItem {

    // EntityPlayer is passed in to access the player's inventory...

    public static void renderHotbarItem(TextBox tx, EntityPlayer player){
        RenderTextBox.renderTextBox(tx);
        float[] abspoints = new float[]{
            tx.getX() + 0.5f,          tx.getY() - 1.0f,
            tx.getX() + 0.5f,          tx.getY() - 1.0f + tx.getHeight(),
            tx.getX() + tx.getWidth(), tx.getY() - 1.0f + tx.getHeight(),
            tx.getX() + tx.getWidth(), tx.getY() - 1.0f,
        };
        float[] texpoints = new float[]{
                0, 0,
                1f, 0,
                1f, 1f,
                0, 1f
        };
        if (tx instanceof TextBoxHotbarItem){
            TextBoxHotbarItem hotbarItem = (TextBoxHotbarItem)tx;
            ItemStack stack = player.getInventory()[hotbarItem.getInventoryIndex()];
            if (stack != null) {
                Item item = stack.getItem();
                if (item != null){
                    item.getTexture().bind();
                    glEnable(GL_BLEND);
                    glBlendFunc(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA);
                    glColor4d(1d,1d,1d,1d);

                    glBegin(GL_QUADS);
                    for (int i = 0; i < abspoints.length; i += 2){
                        glVertex2d(abspoints[i], abspoints[i + 1]);
                        glTexCoord2f(texpoints[i], texpoints[i + 1]);
                    }
                    glEnd();
                    glDisable(GL_BLEND);
                }
            }
        }
    }
}