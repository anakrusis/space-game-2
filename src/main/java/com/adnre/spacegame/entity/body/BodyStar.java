package com.adnre.spacegame.entity.body;

import com.adnre.spacegame.entity.Body;
import com.adnre.spacegame.util.RandomUtil;
import com.adnre.spacegame.world.Chunk;
import com.adnre.spacegame.world.Map;
import com.adnre.spacegame.world.Nation;

import java.util.ArrayList;

public class BodyStar extends Body {

    private ArrayList<BodyPlanet> planets;

    public BodyStar(double x, double y, float dir, Chunk chunk, Map map, String name) {
        super(x, y, dir, chunk, 32, map);

        this.radius = RandomUtil.fromRangeF(110f,160f);

        this.rotSpeed = 0.01f;
        this.terrain = new float[]{ 0, 0, 0, 0, 0,  0, 0, 0, 0, 0,  0, 0, 0, 0, 0,  0, 0, 0, 0, 0 };
        this.canEntitiesCollide = true;

        this.name = name;

        BodyGravityRadius bgr = new BodyGravityRadius(this.x, this.y, this.dir, this.chunk, this.radius * 2, this.map, this);
        this.chunk.getBodies().add(bgr);
        planets = new ArrayList<>();
    }

    public BodyStar(double x, double y, float dir, Chunk chunk, Map map){
        this(x, y, dir, chunk, map, "Star " + chunk.getX() + " " + chunk.getY());
    }

    @Override
    public void update() {
        super.update();
    }

    public ArrayList<BodyPlanet> getPlanets() {
        return planets;
    }

    public int getSystemPopulation() {
        int pop = 0;
        for (int i = 0; i < planets.size(); i++){
            pop += planets.get(i).getPopulation();
        }
        return pop;
    }

    public Nation getNation() {
        Nation nation = null;
        for (int i = 0; i < planets.size(); i++){
            if (planets.get(i).getNation() != null){
                nation = planets.get(i).getNation();
            }
        }
        return nation;
    }
}
