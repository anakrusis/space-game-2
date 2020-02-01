package com.adnre.spacegame.world;

import com.adnre.spacegame.SpaceGame;
import com.adnre.spacegame.entity.body.BodyPlanet;
import com.adnre.spacegame.entity.building.BuildingApartment;
import com.adnre.spacegame.entity.building.BuildingSpaceport;
import com.adnre.spacegame.entity.building.EntityBuilding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class City implements Serializable {

    private String name;
    private int population;
    private ArrayList<Integer> terrainIndexes;

    private static final long serialVersionUID = 409589347L;
    private UUID uuid;
    private UUID planetUUID;
    private int chunkX, chunkY;

    public City (String name, int chunkx, int chunky, UUID planetUUID){
        this.name = name;
        this.population = 0;
        this.uuid = UUID.randomUUID();
        this.terrainIndexes = new ArrayList<>();
        this.planetUUID = planetUUID;
        this.chunkX = chunkx;
        this.chunkY = chunky;
    }

    public void update(){
        population = 0;
        for (Integer index : terrainIndexes){
            EntityBuilding b = getPlanet().getBuilding(index);
            if (b instanceof BuildingApartment){
                population += ((BuildingApartment) b).getPopulation();
            }
        }
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public ArrayList<Integer> getTerrainIndexes() {
        return terrainIndexes;
    }

    public BodyPlanet getPlanet(){
        return (BodyPlanet) SpaceGame.world.getChunks()[chunkX][chunkY].getBodies().get(planetUUID);
    }

    public int getPopulation() {
        return population;
    }
}
