package com.adnre.spacegame.world;

import com.adnre.spacegame.entity.EntityBuilding;

public class ChunkChangeBuildingPlace extends ChunkChange {
    private EntityBuilding building;
    public ChunkChangeBuildingPlace(EntityBuilding building){
        this.building = building;
    }

    public EntityBuilding getBuilding() {
        return building;
    }
}
