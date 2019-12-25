package com.adnre.spacegame.item;

import com.adnre.spacegame.entity.EntityBuilding;
import com.adnre.spacegame.entity.EntityPlayer;
import com.adnre.spacegame.world.Map;

public class ItemBuilding extends Item {
    public ItemBuilding(String name) {
        super(name);
    }

    public EntityBuilding getBuilding(double x, double y, float dir, Map map){
        return null;
    }
}
