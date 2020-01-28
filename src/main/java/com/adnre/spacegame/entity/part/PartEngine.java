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
        if (this.getEntity().getVelocity() > 0.1 && this.ticksExisted % 10 == 0){
            float dir = (float) (this.dir - Math.PI + (Math.random() * 0.5f));
            ParticleSmoke smoke = new ParticleSmoke(this.x, this.y, dir, this.world);
            this.world.spawnEntity(smoke);
        }
    }
}
