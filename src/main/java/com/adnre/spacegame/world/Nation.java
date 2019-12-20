package com.adnre.spacegame.world;

import com.adnre.spacegame.entity.body.BodyPlanet;
import com.adnre.spacegame.entity.body.BodyStar;

public class Nation {
    private String name;
    private BodyStar homeStar;
    private BodyPlanet homePlanet;
    private float[] color;

    public Nation (String name, BodyStar homestar, BodyPlanet homeplanet){
        this.name = name;
        this.homeStar = homestar;
        this.homePlanet = homeplanet;
        this.color = new float[] {0, 0, 0};
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
}
