package com.adnre.spacegame.entity.part;

import com.adnre.spacegame.util.MathHelper;
import com.adnre.spacegame.world.World;

import java.util.UUID;

public class PartChassis extends Part {
    public PartChassis(float relx, float rely, World world, UUID entityUUID) {
        super(relx, rely, world, entityUUID);
        height = 1;
        width = 1.5;
    }
}
