package com.adnre.spacegame.entity.body;

import com.adnre.spacegame.world.Chunk;
import com.adnre.spacegame.world.World;

import java.util.UUID;

public class BodyAtmosphere extends Body {

    UUID dependentBodyUUID;

    public BodyAtmosphere(double x, double y, float dir, Chunk chunk, float radius, World world, UUID bodyUUID) {
        super(x, y, dir, chunk, radius, world);
        this.canEntitiesCollide = false;
        this.dependentBodyUUID = bodyUUID;
        this.rotSpeed = 0.01f;
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
