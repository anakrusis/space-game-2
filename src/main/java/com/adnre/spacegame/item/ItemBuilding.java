package com.adnre.spacegame.item;

import com.adnre.spacegame.entity.building.EntityBuilding;
import com.adnre.spacegame.world.World;

public class ItemBuilding extends Item {
    public ItemBuilding(String name) {
        super(name);
    }

    public EntityBuilding getBuilding(double x, double y, float dir, World world){
        return null;
    }
}
