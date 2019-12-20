package com.adnre.spacegame.entity.building;

import com.adnre.spacegame.entity.EntityBuilding;
import com.adnre.spacegame.entity.EntityPlayer;
import com.adnre.spacegame.world.Map;

public class BuildingSpaceport extends EntityBuilding {
    public BuildingSpaceport(double x, double y, float dir, Map map, EntityPlayer playerPlaced) {
        super(x, y, dir, map, playerPlaced);
    }
}
