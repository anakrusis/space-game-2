package com.adnre.spacegame.item;

import com.adnre.spacegame.render.Texture;
import com.adnre.spacegame.render.Textures;

public class ItemBomb extends Item {
    public ItemBomb(String name) {
        super(name);
        this.price = 250;
        this.id = 4;
    }

    @Override
    public Texture getTexture() {
        return Textures.bomb;
    }
}
