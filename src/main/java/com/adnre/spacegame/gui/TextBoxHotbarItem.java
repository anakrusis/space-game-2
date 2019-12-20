package com.adnre.spacegame.gui;

import com.adnre.spacegame.SpaceGame;

public class TextBoxHotbarItem extends TextBox {

    protected int inventoryIndex;
    public TextBoxHotbarItem (float x, float y, float width, float height, int index) {
        super(x, y, width, height, EnumGui.GUI_HOTBAR_ITEM);
        this.inventoryIndex = index;
    }

    public int getInventoryIndex() {
        return inventoryIndex;
    }

    @Override
    public void onClick() {
        super.onClick();
        if (SpaceGame.map.getPlayer() != null){
            SpaceGame.map.getPlayer().setCurrentItemSlot(this.getInventoryIndex());
        }
    }
}
