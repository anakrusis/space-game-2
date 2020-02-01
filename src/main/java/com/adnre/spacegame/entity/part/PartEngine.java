package com.adnre.spacegame.entity.part;

import com.adnre.spacegame.entity.particle.ParticleSmoke;
import com.adnre.spacegame.util.MathHelper;
import com.adnre.spacegame.world.World;

import java.util.UUID;

public class PartEngine extends Part {

    public PartEngine(float relx, float rely, World world, UUID entityUUID) {
        super(relx, rely, world, entityUUID);
    }

    @Override
    public void update() {
        super.update();
    }
}
