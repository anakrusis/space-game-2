package com.adnre.spacegame.entity.body;

import com.adnre.spacegame.world.Chunk;
import com.adnre.spacegame.world.World;

public class BodyOrbit extends Body {
    public BodyOrbit(double x, double y, float dir, Chunk chunk, float radius, World world) {
        super(x, y, dir, chunk, radius, world);
        this.color = new float[]{0, 0.45f, 0};
    }

    @Override
    public void update() {
    }
}
