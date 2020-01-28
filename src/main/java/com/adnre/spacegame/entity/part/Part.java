package com.adnre.spacegame.entity.part;

import com.adnre.spacegame.SpaceGame;
import com.adnre.spacegame.entity.Entity;
import com.adnre.spacegame.render.Texture;
import com.adnre.spacegame.util.MathHelper;
import com.adnre.spacegame.world.World;

import java.util.UUID;

public class Part extends Entity {

    // Relative coordinates where 0,0 is the same as the entity it corresponds to
    float relativeX, relativeY;
    UUID entityUUID;

    public Part (float relx, float rely, World world, UUID entityUUID) {
        super(world.getEntities().get(entityUUID).getX(),
                world.getEntities().get(entityUUID).getY(),
                world.getEntities().get(entityUUID).getDir(), world);

        this.relativeX = relx;
        this.relativeY = rely;
        this.entityUUID = entityUUID;
    }

    public UUID getEntityUUID() {
        return entityUUID;
    }

    public Entity getEntity() {
        return SpaceGame.world.getEntities().get(entityUUID);
    }

    public float getRelativeX() {
        return relativeX;
    }

    public float getRelativeY() {
        return relativeY;
    }

    public double[] getAbsolutePoints() {
        double[] relpoints = new double[]{
                -1, -1,
                1,  -1,
                1,   1,
                -1,  1
        };
        double[] abspoints = new double[relpoints.length];
        for (int i = 0; i < abspoints.length; i += 2){
            abspoints[i] = MathHelper.rotX(getEntity().getDir(),relpoints[i],relpoints[ i + 1 ]) + x;
            abspoints[i + 1] = MathHelper.rotY(getEntity().getDir(),relpoints[i],relpoints[ i + 1 ]) + y;
        }
        return abspoints;
    }

    public Texture getTexture(){
        return null;
    }

    @Override
    public void update() {
        super.update();
    }
}
