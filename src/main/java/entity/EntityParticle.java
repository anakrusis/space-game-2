package entity;

import util.MathHelper;
import world.Map;

public class EntityParticle extends Entity {
    public EntityParticle(double x, double y, float dir, Map map) {
        super(x, y, dir, map);
        this.velocity = 1;
    }

    @Override
    public boolean isDead() {
        return this.ticksExisted > 50;
    }

    @Override
    public double[] getAbsolutePoints() {
        double point1x = MathHelper.rotX(this.dir,-0.1d,0.1d) + this.x;
        double point1y = MathHelper.rotY(this.dir,-0.1d,0.1d) + this.y;

        double point2x = MathHelper.rotX(this.dir,0.1d,0.0d) + this.x;
        double point2y = MathHelper.rotY(this.dir,0.1d,0.0d) + this.y;

        double point3x = MathHelper.rotX(this.dir,-0.1d,-0.1d) + this.x;
        double point3y = MathHelper.rotY(this.dir,-0.1d,-0.1d) + this.y;

        return new double[]{ point1x, point1y, point2x, point2y, point3x, point3y };
    }

    @Override
    public void update() {
        this.x += this.velocity * Math.cos(this.dir);
        this.y += this.velocity * Math.sin(this.dir);
        this.ticksExisted++;
    }
}
