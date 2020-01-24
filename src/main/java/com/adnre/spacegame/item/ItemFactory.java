package com.adnre.spacegame.item;

import com.adnre.spacegame.entity.building.EntityBuilding;
import com.adnre.spacegame.entity.building.BuildingFactory;
import com.adnre.spacegame.render.Texture;
import com.adnre.spacegame.render.Textures;
import com.adnre.spacegame.world.World;

public class ItemFactory extends ItemBuilding {
    public ItemFactory(String name) {
        super(name);
        this.price = 50;
        this.id = 1;
    }

    @Override
    public Texture getTexture() {
        return Textures.factory;
    }

    @Override
    public EntityBuilding getBuilding(double x, double y, float dir, World world) {
        return new BuildingFactory(x, y, dir, world, world.getPlayerNationUUID());
    }
}
