package com.adnre.spacegame.entity.body;

import com.adnre.spacegame.entity.Body;
import com.adnre.spacegame.world.Chunk;
import com.adnre.spacegame.world.World;

public class BodyGravityRadius extends Body {

    Body dependentBody;

    public BodyGravityRadius(double x, double y, float dir, Chunk chunk, float radius, World world, Body body) {
        super(x, y, dir, chunk, radius, world);
        this.dependentBody = body;
        this.terrain = new float[]{ 0, 0, 0, 0, 0,  0, 0, 0, 0, 0,  0, 0, 0, 0, 0,  0, 0, 0, 0, 0 };
        this.rotSpeed = 0f;
        this.color = new float[]{0.1f, 0.1f, 0.1f};
    }

    public Body getDependentBody() {
        return dependentBody;
    }

    @Override
    public void update() {
        super.update();
        this.x = this.dependentBody.getX();
        this.y = this.dependentBody.getY();
    }
}
