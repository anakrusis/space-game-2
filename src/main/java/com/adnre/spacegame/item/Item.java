package com.adnre.spacegame.item;

import com.adnre.spacegame.render.Texture;

import java.io.Serializable;

public class Item implements Serializable {
    protected int maxStackSize;
    protected String name;
    protected int price;

    private static final long serialVersionUID = 123456798L;

    public Item(String name){
        this.name = name;
        this.maxStackSize = 99;
    }
    public Texture getTexture(){
        return null;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
