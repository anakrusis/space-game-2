package com.adnre.spacegame.entity.part;

import com.adnre.spacegame.render.Texture;
import com.adnre.spacegame.render.Textures;
import com.adnre.spacegame.world.World;

import java.util.UUID;

public class PartChassisWood extends PartChassis {


    public PartChassisWood(float relx, float rely, World world, UUID entityUUID) {
        super(relx, rely, world, entityUUID);
    }

    @Override
    public Texture getTexture() {
        return Textures.chassis_wood;
    }
}
