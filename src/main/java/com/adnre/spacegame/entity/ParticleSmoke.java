package com.adnre.spacegame.entity;

import com.adnre.spacegame.util.MathHelper;
import com.adnre.spacegame.world.Map;

public class ParticleSmoke extends EntityParticle {
    private double size;

    public ParticleSmoke(double x, double y, float dir, Map map) {
        super(x, y, dir, map);
        this.velocity = 0.05;
        this.size = 0.02 + Math.random() * 0.2;
    }

    @Override
    public double[] getAbsolutePoints() {
        double point1x = MathHelper.rotX(this.dir,-size,size) + this.x;
        double point1y = MathHelper.rotY(this.dir,-size,size) + this.y;

        double point2x = MathHelper.rotX(this.dir,size,0d) + this.x;
        double point2y = MathHelper.rotY(this.dir,size,0d) + this.y;

        double point3x = MathHelper.rotX(this.dir,-size,-size) + this.x;
        double point3y = MathHelper.rotY(this.dir,-size,-size) + this.y;

        return new double[]{ point1x, point1y, point2x, point2y, point3x, point3y };
    }
}
