package com.adnre.spacegame.world;

import com.adnre.spacegame.entity.Body;
import com.adnre.spacegame.entity.body.BodyPlanet;
import com.adnre.spacegame.entity.body.BodyStar;
import com.adnre.spacegame.util.GenUtil;
import com.adnre.spacegame.util.NymGen;
import com.adnre.spacegame.util.RandomUtil;
import com.adnre.spacegame.util.Reference;

import java.io.Serializable;
import java.util.ArrayList;

public class Chunk implements Serializable {
    private int x;
    private int y;
    private Map map;

    private ArrayList<Body> bodies;

    public Chunk (int x, int y, Map map){
        this.x = x;
        this.y = y;
        this.map = map;
        this.bodies = new ArrayList<>();

        // Makes five attempts to spawn a star within the chunk padding.
        // If all five of these attempts fail then fuggedaboutit, no star in the chunk.
        for (int i = 0; i < 5; i++){
            double genx = Reference.CHUNK_SIZE * (this.x + Math.random());
            double geny = Reference.CHUNK_SIZE * (this.y + Math.random());

            if (GenUtil.withinPadding(genx, geny, 1000)){
                String name = NymGen.newName();
                this.bodies.add(new BodyStar(genx, geny, 0, this, this.map, name));
                break;
            }
        }

        // Every star gets potentially up to 4 planets
        int planetcount = 0;
        int orbitDistanceInterval = 200;
        int orbitVariance = 40;

        for (int i = 0; i < this.bodies.size(); i++){
            Body body = this.bodies.get(i);
            if (body instanceof BodyStar){
                int planetnum = orbitDistanceInterval * RandomUtil.fromRangeI(2, 6) + orbitDistanceInterval;
                for (int planetdist = 2 * orbitDistanceInterval; planetdist < planetnum; planetdist += orbitDistanceInterval){

                    float orbitDistance = RandomUtil.fromRangeF(planetdist - orbitVariance,planetdist + orbitVariance);

                    String name = body.getName() + " " + NymGen.greekLetters()[planetcount];

                    BodyPlanet planet = new BodyPlanet(body.getX() + orbitDistance, body.getY(), 0, this,
                            orbitDistance, (BodyStar)body, this.map, name );

                    ((BodyStar) body).getPlanets().add(planet);
                    this.bodies.add(planet);
                    planetcount++;
                }
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ArrayList<Body> getBodies() {
        return bodies;
    }

    public Map getMap() {
        return map;
    }
}
