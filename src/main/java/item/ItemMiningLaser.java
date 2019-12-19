package item;

import render.Texture;
import render.Textures;

public class ItemMiningLaser extends Item {

    public ItemMiningLaser(){
        this.maxStackSize = 1;
    }

    @Override
    public Texture getTexture() {
        return Textures.mining_laser;
    }
}
