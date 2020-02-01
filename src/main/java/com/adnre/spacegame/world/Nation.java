package com.adnre.spacegame.world;

import com.adnre.spacegame.SpaceGame;
import com.adnre.spacegame.entity.body.BodyPlanet;
import com.adnre.spacegame.entity.body.BodyStar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Nation implements Serializable {
    private String name;
    private float[] color;
    private int homeChunkX;
    private int homeChunkY;

    private UUID uuid;
    private UUID homeStarUUID;
    private UUID homePlanetUUID;
    private UUID capitalCityUUID;
    private ArrayList<UUID> planetUUIDs;
    private ArrayList<UUID> cityUUIDs;

    private static final long serialVersionUID = 40958934789238L;

    public Nation (String name, int homeChunkX, int homeChunkY, UUID homestarUUID, UUID homeplanetUUID, UUID capitalCityUUID){
        this.name = name;
        this.homeStarUUID = homestarUUID;
        this.homePlanetUUID = homeplanetUUID;
        this.homeChunkX = homeChunkX;
        this.homeChunkY = homeChunkY;
        this.capitalCityUUID = capitalCityUUID;

        this.planetUUIDs = new ArrayList<>();
        planetUUIDs.add(homeplanetUUID);

        this.color = new float[] {0.4f, 0.95f, 0.95f};
        //this.color = new float[] {0.01f, 0.50f, 0.2f};
        this.uuid = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public float[] getColor() {
        return color;
    }

    public void setColor(float[] color) {
        this.color = color;
    }

    public BodyPlanet getHomePlanet() {
        return (BodyPlanet) SpaceGame.world.getChunks()[homeChunkX][homeChunkY].getBodies().get(homePlanetUUID);
    }

    public BodyStar getHomeStar() {
        return (BodyStar) SpaceGame.world.getChunks()[homeChunkX][homeChunkY].getBodies().get(homeStarUUID);
    }

    public UUID getUuid() {
        return uuid;
    }
}
