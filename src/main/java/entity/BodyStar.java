package entity;

import util.RandomUtil;
import world.Chunk;
import world.Map;

import java.util.Random;

public class BodyStar extends Body {
    public BodyStar(double x, double y, float dir, Chunk chunk, Map map) {
        super(x, y, dir, chunk, 32, map);

        this.radius = RandomUtil.fromRangeF(64f,80f);

        this.rotSpeed = 0.01f;
        this.terrain = new float[]{ 0, 0, 0, 0, 0,  0, 0, 0, 0, 0,  0, 0, 0, 0, 0,  0, 0, 0, 0, 0 };
        this.canEntitiesCollide = true;

        this.name = "Star " + chunk.getX() + " " + chunk.getY();

        BodyGravityRadius bgr = new BodyGravityRadius(this.x, this.y, this.dir, this.chunk, this.radius * 2, this.map, this);
        this.chunk.getBodies().add(bgr);
    }

    @Override
    public void update() {
        super.update();
        for (int i = 0; i < this.terrain.length; i++){
            //float random = (float)Math.random();
            //this.terrain[i] = random;
        }
    }
}
