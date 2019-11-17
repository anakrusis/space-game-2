package entity;

import util.MathHelper;
import world.Map;

public class EntityParticle extends Entity {
    public EntityParticle(double x, double y, float dir, Map map) {
        super(x, y, dir, map);
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
