package entity;

import world.Chunk;
import world.Map;

public class BodyStar extends Body {
    public BodyStar(double x, double y, float dir, Chunk chunk, Map map) {
        super(x, y, dir, chunk,16, map);

        this.rotSpeed = 0.01f;
        this.terrain = new float[]{ 0, 0, 0, 0, 0,  0, 0, 0, 0, 0,  0, 0, 0, 0, 0,  0, 0, 0, 0, 0 };
    }

    @Override
    public void update() {
        super.update();
        for (int i = 0; i < this.terrain.length; i++){
            float random = (float)Math.random();
            this.terrain[i] = random;
        }
    }
}
