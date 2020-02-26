package com.adnre.spacegame.gui;

import com.adnre.spacegame.SpaceGame;
import com.adnre.spacegame.item.ItemStack;

public class TextBoxHotbarItem extends TextBox {

    protected int inventoryIndex;
    public TextBoxHotbarItem (float x, float y, float width, float height, int index) {
        super(x, y, width, height, EnumGui.GUI_HOTBAR_ITEM);
        this.inventoryIndex = index;
        this.outlined = true;
    }

    public int getInventoryIndex() {
        return inventoryIndex;
    }

    @Override
    public void onClick() {
        super.onClick();
        if (SpaceGame.world.getPlayer() != null){
            SpaceGame.world.getPlayer().setCurrentItemSlot(this.getInventoryIndex());
        }
    }

    @Override
    public void update() {
        super.update();
        if (SpaceGame.world.getPlayer() != null){
            ItemStack item = SpaceGame.world.getPlayer().getInventory()[this.getInventoryIndex()];
            if (item != null){
                this.setHeader("\n\n" + item.getAmount());
            }else{
                this.setHeader("");
            }

            // Lights up the hotbar item which is currently selected by the player, if any
            if (SpaceGame.world.getPlayer().getCurrentItemSlot() == this.getInventoryIndex()){
                this.setBgColor(new float[]{ 0.9f, 0.9f, 1f });
            }else{
                this.setBgColor(new float[]{ 0.50f, 0.55f, 0.65f });
            }
        }
    }
}
