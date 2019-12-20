package com.adnre.spacegame.item;

import com.adnre.spacegame.entity.EntityBuilding;
import com.adnre.spacegame.entity.EntityPlayer;
import com.adnre.spacegame.entity.building.BuildingFactory;
import com.adnre.spacegame.render.Texture;
import com.adnre.spacegame.render.Textures;
import com.adnre.spacegame.world.Map;

public class ItemFactory extends ItemBuilding {
    @Override
    public Texture getTexture() {
        return Textures.factory;
    }

    @Override
    public EntityBuilding getBuilding(double x, double y, float dir, Map map, EntityPlayer player) {
        return new BuildingFactory(x, y, dir, map, player);
    }
}
