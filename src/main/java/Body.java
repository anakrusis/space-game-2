public class Body extends Entity {
    // Bodies are big entities that are attached to a chunk
    // generally slow moving or even stationary... at least expected to stay within their chunk,
    // get loaded with the chunk, get unloaded with the chunk...

    // examples: star, planet, moon
    public Body (double x, double y, float dir){
        super(x,y,dir);
    }
}
