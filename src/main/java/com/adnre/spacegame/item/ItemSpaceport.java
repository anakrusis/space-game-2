package com.adnre.spacegame.item;

import com.adnre.spacegame.entity.building.BuildingApartment;
import com.adnre.spacegame.entity.building.BuildingSpaceport;
import com.adnre.spacegame.entity.building.EntityBuilding;
import com.adnre.spacegame.render.Texture;
import com.adnre.spacegame.render.Textures;
import com.adnre.spacegame.world.World;

public class ItemSpaceport extends ItemBuilding {
    public ItemSpaceport(String name) {
        super(name);
        this.price = 100;
        this.id = 5;
    }

    @Override
    public Texture getTexture() {
        return Textures.spaceport;
    }

    @Override
    public EntityBuilding getBuilding(double x, double y, float dir, World world) {
        return new BuildingSpaceport(x, y, dir, world);
    }
}
