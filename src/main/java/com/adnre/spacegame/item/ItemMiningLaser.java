package com.adnre.spacegame.item;

import com.adnre.spacegame.render.Texture;
import com.adnre.spacegame.render.Textures;

public class ItemMiningLaser extends Item {

    public ItemMiningLaser(String name){
        super(name);
        this.maxStackSize = 1;
        this.id = 3;
    }

    @Override
    public Texture getTexture() {
        return Textures.mining_laser;
    }
}
