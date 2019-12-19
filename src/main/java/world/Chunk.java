package world;

import entity.Body;
import entity.ParticleOrbit;
import entity.body.BodyPlanet;
import entity.body.BodyStar;
import util.GenUtil;
import util.NymGen;
import util.RandomUtil;
import util.Reference;

import java.util.ArrayList;

public class Chunk {
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

            if (GenUtil.withinPadding(genx, geny, 750)){
                String name = NymGen.newName();
                this.bodies.add(new BodyStar(genx, geny, 0, this, this.map, name));
                break;
            }
        }

        // Every star gets potentially up to 4 planets
        int planetcount = 0;
        for (int i = 0; i < this.bodies.size(); i++){
            Body body = this.bodies.get(i);
            if (body instanceof BodyStar){
                int planetnum = 120 * RandomUtil.fromRangeI(2, 6) + 120;
                for (int planetdist = 240; planetdist < planetnum; planetdist += 120){

                    float orbitDistance = RandomUtil.fromRangeF(planetdist - 20,planetdist + 20);

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
