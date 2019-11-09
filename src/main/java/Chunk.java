import java.util.ArrayList;

public class Chunk {
    private int x;
    private int y;

    private ArrayList<Body> bodies;

    public Chunk (int x, int y){
        this.x = x;
        this.y = y;
        this.bodies = new ArrayList<>();

        for (int i = 0; i < 5; i++){
            this.bodies.add(new BodyStar (Main.CHUNK_SIZE * (this.x + Math.random()),
                    Main.CHUNK_SIZE * (this.y + Math.random()), 0, this));
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
