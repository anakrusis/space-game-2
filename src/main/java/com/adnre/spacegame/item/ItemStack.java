package com.adnre.spacegame.item;

import java.io.Serializable;

public class ItemStack implements Serializable {

    private Item item;

    private int amount;

    private static final long serialVersionUID = 50983489453L;

    public ItemStack (Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    public void addAmount(int amount){
        this.amount += amount;
    }

    public void shrink () {
        this.amount --;
    }

}
