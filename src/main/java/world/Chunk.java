package world;

import entity.Body;
import entity.BodyPlanet;
import entity.BodyStar;
import misc.Reference;

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

        for (int i = 0; i < 1; i++){
            this.bodies.add(new BodyStar(Reference.CHUNK_SIZE * (this.x + Math.random()),
                Reference.CHUNK_SIZE * (this.y + Math.random()), 0, this, this.map));
        }

        for (int i= 0; i < this.bodies.size(); i++){
            Body body = this.bodies.get(i);
            if (body instanceof BodyStar){
                this.bodies.add( new BodyPlanet(body.getX(), body.getY(), 0, this, 5, (BodyStar)body, this.map));
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
