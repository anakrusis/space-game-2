package com.adnre.spacegame.entity.building;

import com.adnre.spacegame.item.ItemStack;
import com.adnre.spacegame.item.Items;
import com.adnre.spacegame.render.Texture;
import com.adnre.spacegame.render.Textures;
import com.adnre.spacegame.world.World;

import java.util.UUID;

public class BuildingCityHall extends EntityBuilding {
    public BuildingCityHall(double x, double y, float dir, World world, UUID nationUUID) {
        super(x, y, dir, world, nationUUID);
        this.price = 500;
        this.name = "City Hall";
    }

    public Texture getTexture(){
        return Textures.city_hall;
    }

    @Override
    public ItemStack getItemDropped() {
        return new ItemStack (Items.ITEM_CITY_HALL, 1);
    }

    @Override
    public Texture getWindowTexture() {
        return Textures.city_hall_windows;
    }
}
