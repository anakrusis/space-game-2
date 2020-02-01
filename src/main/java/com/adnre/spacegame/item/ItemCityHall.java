package com.adnre.spacegame.item;

import com.adnre.spacegame.entity.building.BuildingCityHall;
import com.adnre.spacegame.entity.building.BuildingSpaceport;
import com.adnre.spacegame.entity.building.EntityBuilding;
import com.adnre.spacegame.render.Texture;
import com.adnre.spacegame.render.Textures;
import com.adnre.spacegame.world.World;

public class ItemCityHall extends ItemBuilding {
    public ItemCityHall(String name) {
        super(name);
        this.price = 500;
        this.id = 6;
    }

    @Override
    public Texture getTexture() {
        return Textures.city_hall;
    }

    @Override
    public EntityBuilding getBuilding(double x, double y, float dir, World world) {
        return new BuildingCityHall(x, y, dir, world, world.getPlayerNationUUID());
    }
}
