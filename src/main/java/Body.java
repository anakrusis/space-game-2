// Bodies are big entities that are attached to a chunk
// generally slow moving or even stationary... at least expected to stay within their chunk,
// get loaded with the chunk, get unloaded with the chunk...

// examples: star, planet, moon

public class Body extends Entity {

    private float radius;
    private Chunk chunk;

    // Terrain will be constructed of relative radial positions away from the center
    private float[] terrain;

    public Body (double x, double y, float dir, Chunk chunk){
        super(x,y,dir);
        this.chunk = chunk;
    }

    public float[] getTerrain() {
        return terrain;
    }

    public float getRadius() {
        return radius;
    }

    public Chunk getChunk() {
        return chunk;
    }
}
