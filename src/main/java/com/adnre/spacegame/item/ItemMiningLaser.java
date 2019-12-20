package com.adnre.spacegame.item;

import com.adnre.spacegame.render.Texture;
import com.adnre.spacegame.render.Textures;

public class ItemMiningLaser extends Item {

    public ItemMiningLaser(){
        this.maxStackSize = 1;
    }

    @Override
    public Texture getTexture() {
        return Textures.mining_laser;
    }
}
