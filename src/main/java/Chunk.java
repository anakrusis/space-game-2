import java.util.ArrayList;

public class Chunk {
    private int x;
    private int y;

    private ArrayList<Body> bodies;

    public Chunk (int x, int y){
        this.x = x;
        this.y = y;
        this.bodies = new ArrayList<>();

        for (int i = 0; i < 2; i++){
            this.bodies.add(new BodyStar (Main.CHUNK_SIZE * (this.x + Math.random()),
                    Main.CHUNK_SIZE * (this.y + Math.random()), 0, this));
        }

        for (int i= 0; i < this.bodies.size(); i++){
            Body body = this.bodies.get(i);
            if (body instanceof BodyStar){
                this.bodies.add( new BodyPlanet (body.x, body.y, 0, this, 2, (BodyStar)body));
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
