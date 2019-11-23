package world;

import entity.Body;
import entity.BodyPlanet;
import entity.BodyStar;
import util.GenUtil;
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

            if (GenUtil.withinPadding(genx, geny, 400)){
                this.bodies.add(new BodyStar(genx, geny, 0, this, this.map));
                break;
            }
        }

        // Every star gets a planet (TODO Randomize this)
        for (int i= 0; i < this.bodies.size(); i++){
            Body body = this.bodies.get(i);
            if (body instanceof BodyStar){
                float orbitDistance = RandomUtil.fromRangeF(180,240);
                this.bodies.add( new BodyPlanet(body.getX() + orbitDistance, body.getY(), 0, this, orbitDistance, (BodyStar)body, this.map));
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
}
