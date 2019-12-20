package com.adnre.spacegame.item;

import com.adnre.spacegame.render.Texture;

public class Item {
    protected int maxStackSize;
    protected String name;
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
}
