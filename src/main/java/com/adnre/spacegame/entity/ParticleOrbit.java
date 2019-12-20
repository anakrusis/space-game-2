package com.adnre.spacegame.entity;

import com.adnre.spacegame.util.MathHelper;
import com.adnre.spacegame.world.Map;

public class ParticleOrbit extends EntityParticle {

    private double size;

    public ParticleOrbit(double x, double y, float dir, Map map) {
        super(x, y, dir, map);
        this.velocity = 0;
        this.size = 0.30;
    }

    @Override
    public void update() {
        this.x += this.velocity * Math.cos(this.dir);
        this.y += this.velocity * Math.sin(this.dir);
        this.ticksExisted++;
        this.size -= 0.00005;
    }

    @Override
    public double[] getAbsolutePoints() {
        double point1x = MathHelper.rotX(this.dir,-size,size) + this.x;
        double point1y = MathHelper.rotY(this.dir,-size,size) + this.y;

        double point2x = MathHelper.rotX(this.dir,size,0d) + this.x;
        double point2y = MathHelper.rotY(this.dir,size,0d) + this.y;

        double point3x = MathHelper.rotX(this.dir,-size,-size * 20) + this.x;
        double point3y = MathHelper.rotY(this.dir,-size,-size * 20) + this.y;

        return new double[]{ point1x, point1y, point2x, point2y, point3x, point3y };
    }

    @Override
    public boolean isDead() {
        return this.ticksExisted > 5000;
    }
}
