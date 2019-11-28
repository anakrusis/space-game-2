package entity.body;

import entity.Body;
import world.Chunk;
import world.Map;

public class BodyAtmosphere extends Body {

    BodyPlanet planet;

    public BodyAtmosphere(double x, double y, float dir, Chunk chunk, float radius, Map map, BodyPlanet planet) {
        super(x, y, dir, chunk, radius, map);
        this.canEntitiesCollide = false;
        this.planet = planet;
    }
}
