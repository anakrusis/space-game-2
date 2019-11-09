// Bodies are big entities that are attached to a chunk
// generally slow moving or even stationary... at least expected to stay within their chunk,
// get loaded with the chunk, get unloaded with the chunk...
// are large enough to be gravitationally rounded and therefore have a radius.

// comets and asteroids would not be bodies. they are fast enough to go between chunks often
// and so would be loaded with the map entities

// examples: star, planet, moon

public class Body extends Entity {

    private float radius;
    private Chunk chunk;

    // Terrain will be constructed of relative radial positions away from the center
    private float[] terrain;

    public Body (double x, double y, float dir, Chunk chunk, float radius){
        super(x,y,dir);
        this.chunk = chunk;
        this.radius = radius;
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
