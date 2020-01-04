package com.adnre.spacegame.entity.body;

import com.adnre.spacegame.world.Chunk;
import com.adnre.spacegame.world.World;

import java.util.UUID;

public class BodyGravityRadius extends Body {

    UUID dependentBodyUUID;

    public BodyGravityRadius(double x, double y, float dir, Chunk chunk, float radius, World world, UUID bodyUUID) {
        super(x, y, dir, chunk, radius, world);
        this.dependentBodyUUID = bodyUUID;
        this.terrain = new float[]{ 0, 0, 0, 0, 0,  0, 0, 0, 0, 0,  0, 0, 0, 0, 0,  0, 0, 0, 0, 0 };
        this.rotSpeed = 0f;
        this.color = new float[]{0.1f, 0.1f, 0.1f};
    }

    public UUID getDependentBodyUUID() {
        return dependentBodyUUID;
    }
    public Body getDependentBody() {
        return this.chunk.getBodies().get(dependentBodyUUID);
    }

    @Override
    public void update() {
        super.update();
        this.x = this.getDependentBody().getX();
        this.y = this.getDependentBody().getY();
    }
}
