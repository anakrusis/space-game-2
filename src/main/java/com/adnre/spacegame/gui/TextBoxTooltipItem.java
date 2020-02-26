package com.adnre.spacegame.gui;

import com.adnre.spacegame.SpaceGame;
import com.adnre.spacegame.item.ItemStack;
import com.adnre.spacegame.util.CollisionUtil;

public class TextBoxTooltipItem extends TextBox {
    public TextBoxTooltipItem(float x, float y, float width, float height) {
        super(x, y, width, height, EnumGui.GUI_TOOLTIP_ITEM, false, false);
    }

    @Override
    public void update() {
        super.update();
        // Now searches for the hotbar item, and sees if it touches the cursor
        for (int i = SpaceGame.guiElements.size() - 1; i >= 0; i--) {
            TextBox hotbaritem = SpaceGame.guiElements.get(i);
            if (hotbaritem instanceof TextBoxHotbarItem &&
                    CollisionUtil.isPointCollidingInBox(SpaceGame.world.getCursor().getScreenX(),
                            SpaceGame.world.getCursor().getScreenY(), hotbaritem.getX(),
                            hotbaritem.getY(), hotbaritem.getWidth(), hotbaritem.getHeight())){

                // Now searches for items in inventory
                if (SpaceGame.world.getPlayer() != null){
                    ItemStack[] inventory = SpaceGame.world.getPlayer().getInventory();
                    ItemStack item = inventory[ ((TextBoxHotbarItem) hotbaritem).getInventoryIndex() ];

                    // And gives the tooltip the item name
                    // as well as positioning it dynamically with the cursor
                    // and changing its length to match the length of the string
                    if (item != null){
                        this.setVisible(true);
                        this.setX((float) SpaceGame.world.getCursor().getScreenX());
                        this.setY((float) SpaceGame.world.getCursor().getScreenY());
                        this.setHeader( item.getItem().getName() );

                        int len = item.getItem().getName().length();
                        this.setWidth( len / 2f );

                        return;
                    }
                }
            }
        }
        this.setVisible(false);
    }
}
