package entity;

import world.Map;

public class EntityParticle extends Entity {
    public EntityParticle(double x, double y, float dir, Map map) {
        super(x, y, dir, map);
    }

    @Override
    public boolean isDead() {
        return this.ticksExisted > 100;
    }
}
