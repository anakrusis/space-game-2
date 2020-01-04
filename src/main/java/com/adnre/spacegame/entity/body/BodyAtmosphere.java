package com.adnre.spacegame.entity.body;

import com.adnre.spacegame.world.Chunk;
import com.adnre.spacegame.world.World;

public class BodyAtmosphere extends Body {

    BodyPlanet planet;

    public BodyAtmosphere(double x, double y, float dir, Chunk chunk, float radius, World world, BodyPlanet planet) {
        super(x, y, dir, chunk, radius, world);
        this.canEntitiesCollide = false;
        this.planet = planet;
    }
}
