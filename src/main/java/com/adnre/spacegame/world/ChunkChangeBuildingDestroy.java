package com.adnre.spacegame.world;

import com.adnre.spacegame.entity.EntityBuilding;

public class ChunkChangeBuildingDestroy extends ChunkChange {
    private EntityBuilding building;
    public ChunkChangeBuildingDestroy(EntityBuilding building){
        this.building = building;
    }

    public EntityBuilding getBuilding() {
        return building;
    }
}
