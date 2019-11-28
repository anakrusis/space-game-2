package entity.body;

import entity.Body;
import world.Chunk;
import world.Map;

public class BodyGravityRadius extends Body {

    Body dependentBody;

    public BodyGravityRadius(double x, double y, float dir, Chunk chunk, float radius, Map map, Body body) {
        super(x, y, dir, chunk, radius, map);
        this.dependentBody = body;
        this.terrain = new float[]{ 0, 0, 0, 0, 0,  0, 0, 0, 0, 0,  0, 0, 0, 0, 0,  0, 0, 0, 0, 0 };
        this.rotSpeed = 0f;
    }

    public Body getDependentBody() {
        return dependentBody;
    }

    @Override
    public void update() {
        super.update();
        this.x = this.dependentBody.getX();
        this.y = this.dependentBody.getY();
    }
}
