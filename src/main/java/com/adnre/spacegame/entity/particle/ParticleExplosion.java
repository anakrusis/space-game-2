package com.adnre.spacegame.entity.particle;

import com.adnre.spacegame.util.MathHelper;
import com.adnre.spacegame.world.World;

public class ParticleExplosion extends EntityParticle {
    public ParticleExplosion(double x, double y, float dir, World world) {
        super(x, y, dir, world);
        this.velocity = 1;
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
}
