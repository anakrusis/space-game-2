package world;

import entity.body.BodyPlanet;
import entity.body.BodyStar;

public class Nation {
    private String name;
    private BodyStar homeStar;
    private BodyPlanet homePlanet;

    public Nation (String name, BodyStar homestar, BodyPlanet homeplanet){
        this.name = name;
        this.homeStar = homestar;
        this.homePlanet = homeplanet;
    }
}
