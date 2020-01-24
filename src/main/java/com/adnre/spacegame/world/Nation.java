package com.adnre.spacegame.world;

import com.adnre.spacegame.entity.body.BodyPlanet;
import com.adnre.spacegame.entity.body.BodyStar;

import java.io.Serializable;
import java.util.UUID;

public class Nation implements Serializable {
    private String name;
    private BodyStar homeStar;
    private BodyPlanet homePlanet;
    private float[] color;

    private UUID uuid;
    private static final long serialVersionUID = 40958934789238L;

    public Nation (String name, BodyStar homestar, BodyPlanet homeplanet){
        this.name = name;
        this.homeStar = homestar;
        this.homePlanet = homeplanet;
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

    public BodyPlanet getHomePlanet() {
        return homePlanet;
    }

    public BodyStar getHomeStar() {
        return homeStar;
    }

    public UUID getUuid() {
        return uuid;
    }
}
