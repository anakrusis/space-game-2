package com.adnre.spacegame.item;

import com.adnre.spacegame.render.Texture;

public class Item {
    protected int maxStackSize;
    public Item(){
        this.maxStackSize = 99;
    }
    public Texture getTexture(){
        return null;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }
}
