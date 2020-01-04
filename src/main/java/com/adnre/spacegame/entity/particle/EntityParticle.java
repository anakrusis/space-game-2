package com.adnre.spacegame.entity.particle;

import com.adnre.spacegame.entity.Entity;
import com.adnre.spacegame.world.World;

public class EntityParticle extends Entity {
    public EntityParticle(double x, double y, float dir, World world) {
        super(x, y, dir, world);
        this.velocity = 0;
    }

    @Override
    public boolean isDead() {
        return this.ticksExisted > 75;
    }

    @Override
    public void update() {
        this.x += this.velocity * Math.cos(this.dir);
        this.y += this.velocity * Math.sin(this.dir);
        this.ticksExisted++;
    }
}
