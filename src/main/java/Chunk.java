import java.util.ArrayList;

public class Chunk {
    private int x;
    private int y;

    private ArrayList<Body> bodies;

    public Chunk (int x, int y){
        this.x = x;
        this.y = y;
        this.bodies = new ArrayList<>();

        this.bodies.add(new Body (Main.CHUNK_SIZE * (this.x + Math.random()),
                Main.CHUNK_SIZE * (this.y + Math.random()), 0, this));
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
