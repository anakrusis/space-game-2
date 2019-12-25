package com.adnre.spacegame.item;

import com.adnre.spacegame.entity.EntityBuilding;
import com.adnre.spacegame.entity.EntityPlayer;
import com.adnre.spacegame.entity.building.BuildingApartment;
import com.adnre.spacegame.render.Texture;
import com.adnre.spacegame.render.Textures;
import com.adnre.spacegame.world.Map;

public class ItemApartment extends ItemBuilding {
    public ItemApartment(String name) {
        super(name);
        this.price = 25;
    }

    @Override
    public Texture getTexture() {
        return Textures.apartment;
    }

    @Override
    public EntityBuilding getBuilding(double x, double y, float dir, Map map) {
        return new BuildingApartment(x, y, dir, map);
    }
}
