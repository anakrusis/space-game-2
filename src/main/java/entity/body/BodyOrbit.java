package entity.body;

import entity.Body;
import world.Chunk;
import world.Map;

public class BodyOrbit extends Body {
    public BodyOrbit(double x, double y, float dir, Chunk chunk, float radius, Map map) {
        super(x, y, dir, chunk, radius, map);
        this.color = new float[]{0, 0.45f, 0};
    }

    @Override
    public void update() {
    }
}
