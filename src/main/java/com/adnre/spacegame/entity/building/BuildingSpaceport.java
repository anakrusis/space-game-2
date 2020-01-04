package com.adnre.spacegame.entity.building;

import com.adnre.spacegame.entity.EntityPlayer;
import com.adnre.spacegame.world.World;

public class BuildingSpaceport extends EntityBuilding {
    public BuildingSpaceport(double x, double y, float dir, World world, EntityPlayer playerPlaced) {
        super(x, y, dir, world, playerPlaced);
    }
}
