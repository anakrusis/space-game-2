package com.adnre.spacegame.entity.building;

import com.adnre.spacegame.entity.EntityPlayer;
import com.adnre.spacegame.item.ItemStack;
import com.adnre.spacegame.item.Items;
import com.adnre.spacegame.render.Texture;
import com.adnre.spacegame.render.Textures;
import com.adnre.spacegame.world.World;

import java.util.UUID;

public class BuildingSpaceport extends EntityBuilding {
    public BuildingSpaceport(double x, double y, float dir, World world, UUID nationUUID) {
        super(x, y, dir, world, nationUUID);
        this.price = 100;
        this.name = "Spaceport";
    }

    public Texture getTexture(){
        return Textures.spaceport;
    }

    @Override
    public ItemStack getItemDropped() {
        return new ItemStack (Items.ITEM_SPACEPORT, 1);
    }
}
