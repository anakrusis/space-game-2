package com.adnre.spacegame.entity.body;

import com.adnre.spacegame.util.RandomUtil;
import com.adnre.spacegame.world.Chunk;
import com.adnre.spacegame.world.World;
import com.adnre.spacegame.world.Nation;

import java.util.ArrayList;
import java.util.UUID;

public class BodyStar extends Body {

    private ArrayList<UUID> planetUUIDs;

    public BodyStar(double x, double y, float dir, Chunk chunk, World world, String name) {
        super(x, y, dir, chunk, 32, world);

        this.radius = RandomUtil.fromRangeF(110f,160f);

        this.rotSpeed = 0.01f;
        this.terrain = new float[]{ 0, 0, 0, 0, 0,  0, 0, 0, 0, 0,  0, 0, 0, 0, 0,  0, 0, 0, 0, 0 };
        this.canEntitiesCollide = true;

        this.name = name;

        planetUUIDs = new ArrayList<>();
    }

    public BodyStar(double x, double y, float dir, Chunk chunk, World world){
        this(x, y, dir, chunk, world, "Star " + chunk.getX() + " " + chunk.getY());
    }

    @Override
    public void update() {
        super.update();
    }

    public ArrayList<UUID> getPlanetUUIDs() {
        return planetUUIDs;
    }

    public int getSystemPopulation() {
        int pop = 0;
        for (int i = 0; i < getPlanetUUIDs().size(); i++){
            pop += getPlanet(getPlanetUUIDs().get(i)).getPopulation();
        }
        return pop;
    }

    public Nation getNation() {
        Nation nation = null;
        for (UUID planetUUID : planetUUIDs) {
            BodyPlanet planet = getPlanet(planetUUID);
            if (planet.getNation() != null) {
                nation = planet.getNation();
            }
        }
        return nation;
    }

    public BodyPlanet getPlanet(UUID uuid){
        return (BodyPlanet) this.chunk.getBodies().get(uuid);
    }
}
