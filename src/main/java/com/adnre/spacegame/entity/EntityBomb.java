package com.adnre.spacegame.entity;

import com.adnre.spacegame.entity.body.Body;
import com.adnre.spacegame.entity.body.BodyPlanet;
import com.adnre.spacegame.entity.particle.ParticleSmoke;
import com.adnre.spacegame.render.Texture;
import com.adnre.spacegame.render.Textures;
import com.adnre.spacegame.util.CollisionUtil;
import com.adnre.spacegame.util.MathHelper;
import com.adnre.spacegame.world.World;

public class EntityBomb extends Entity {
    public EntityBomb(double x, double y, float dir, World world) {
        super(x, y, dir, world);
    }

    @Override
    public double[] getAbsolutePoints() {
        double[] relpoints = new double[]{
                -0.5, -0.5,
                0.5, -0.5,
                0.5, 0.5,
                -0.5, 0.5
        };
        double[] abspoints = new double[relpoints.length];
        for (int i = 0; i < abspoints.length; i += 2){
            abspoints[i] = MathHelper.rotX(this.dir,relpoints[i],relpoints[ i + 1 ]) + this.x;
            abspoints[i + 1] = MathHelper.rotY(this.dir,relpoints[i],relpoints[ i + 1 ]) + this.y;
        }
        return abspoints;
    }

    @Override
    public void update() {
        super.update();
        this.dir += 0.1;

        if (this.ticksExisted > 1000) {
            this.dead = true;
        }

        if (this.grounded){
            this.explode();
            Body body = this.getGroundedBody();

            int index = CollisionUtil.terrainIndexFromEntityAngle(this, body);
            int indexBefore = MathHelper.loopyMod( index-1, body.getTerrain().length );
            int indexAfter = MathHelper.loopyMod( index+1, body.getTerrain().length );

            if ( this.getGroundedBody() instanceof BodyPlanet){

                BodyPlanet planet = (BodyPlanet) body;
                if (planet.getBuilding(index) != null){
                    planet.getBuilding(index).dead = true;
                }
                if (planet.getBuilding(indexBefore) != null){
                    planet.getBuilding(indexBefore).dead = true;
                }
                if (planet.getBuilding(indexAfter) != null){
                    planet.getBuilding(indexAfter).dead = true;
                }

                body.getTerrain()[indexBefore] -= 1.5;
                body.getTerrain()[index] -= 3;
                body.getTerrain()[indexAfter] -= 1.5;
            }
        }

        if (this.ticksExisted % 10 == 0){
            float dir = (float) (this.dir - Math.PI + (Math.random() * 0.5f));
            ParticleSmoke smoke = new ParticleSmoke(this.x, this.y, dir, this.world);
            this.world.spawnEntity(smoke);
        }
    }

    @Override
    public Texture getTexture() {
        return Textures.bomb;
    }
}
