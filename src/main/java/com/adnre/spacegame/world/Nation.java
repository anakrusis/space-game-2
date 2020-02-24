package com.adnre.spacegame.world;

import com.adnre.spacegame.SpaceGame;
import com.adnre.spacegame.entity.body.BodyPlanet;
import com.adnre.spacegame.entity.body.BodyStar;
import com.adnre.spacegame.util.RandomUtil;
import com.adnre.spacegame.util.Reference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class Nation implements Serializable {
    private String name;
    private float[] color;
    private float money;

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

        this.cityUUIDs = new ArrayList<>();
        cityUUIDs.add(capitalCityUUID);

        this.planetUUIDs = new ArrayList<>();
        planetUUIDs.add(homeplanetUUID);

        this.color = new float[]{RandomUtil.fromRangeF(0, 1), RandomUtil.fromRangeF(0,1), RandomUtil.fromRangeF(0,1)};
        this.uuid = UUID.randomUUID();

        if (Reference.DEASTL_MODE){
            this.money = 69000000;
        }else{
            this.money = 1500;
        }
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

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public void addMoney(float money){
        this.money += money;
    }
}
