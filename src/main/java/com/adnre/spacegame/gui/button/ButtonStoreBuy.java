package com.adnre.spacegame.gui.button;

import com.adnre.spacegame.SpaceGame;
import com.adnre.spacegame.gui.EnumGui;
import com.adnre.spacegame.gui.button.Button;
import com.adnre.spacegame.item.ItemStack;

public class ButtonStoreBuy extends Button {
    protected ItemStack itemStack;
    public ButtonStoreBuy(float x, float y, ItemStack itemStack){
        super(x, y, 8, 1, itemStack.getItem().getName(), "", EnumGui.GUI_BUTTON_STORE_BUY, false);
        this.itemStack = itemStack;

        this.header = itemStack.getItem().getName() + " ($" + itemStack.getItem().getPrice() + ")";
    }

    @Override
    public void onClick() {
        super.onClick();
        int price = this.itemStack.getItem().getPrice();
        if (SpaceGame.world.getPlayer().getNation().getMoney() >= price){
            SpaceGame.world.getPlayer().getNation().addMoney(-price);
            SpaceGame.world.getPlayer().addInventory(itemStack);
        }
    }
    public ItemStack getItemStack(){
        return itemStack;
    }
}
