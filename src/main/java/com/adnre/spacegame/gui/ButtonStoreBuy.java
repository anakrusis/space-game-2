package com.adnre.spacegame.gui;

import com.adnre.spacegame.item.ItemStack;

public class ButtonStoreBuy extends Button {
    protected ItemStack itemStack;
    public ButtonStoreBuy(float x, float y, ItemStack itemStack){
        super(x, y, 3, 1, itemStack.getItem().getName(), "", EnumGui.GUI_BUTTON_STORE_BUY, false);
        this.itemStack = itemStack;
    }

    @Override
    public void onClick() {
        super.onClick();
    }
    public ItemStack getItemStack(){
        return itemStack;
    }
}
