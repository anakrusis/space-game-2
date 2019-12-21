package com.adnre.spacegame.gui;

import com.adnre.spacegame.SpaceGame;
import com.adnre.spacegame.item.ItemBuilding;
import com.adnre.spacegame.item.ItemStack;

public class ButtonStoreBuy extends Button {
    protected ItemStack itemStack;
    public ButtonStoreBuy(float x, float y, ItemStack itemStack){
        super(x, y, 6, 1, itemStack.getItem().getName(), "", EnumGui.GUI_BUTTON_STORE_BUY, false);
        this.itemStack = itemStack;

        this.header = itemStack.getItem().getName() + " ($" + itemStack.getItem().getPrice() + ")";
    }

    @Override
    public void onClick() {
        super.onClick();
        int price = this.itemStack.getItem().getPrice();
        if (SpaceGame.map.getPlayer().getMoney() >= price){
            SpaceGame.map.getPlayer().addMoney(-price);
            SpaceGame.map.getPlayer().addInventory(itemStack);
        }
    }
    public ItemStack getItemStack(){
        return itemStack;
    }
}
