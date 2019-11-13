package entity;

import util.MathHelper;
import world.Chunk;
import world.Map;

public class BodyPlanet extends Body {

    // The star which the planet orbits around, how far away it orbits, and how often
    BodyStar star;
    float orbitDistance;
    int orbitPeriod;
    float orbitSpeed;

    public BodyPlanet(double x, double y, float dir, Chunk chunk, float radius, BodyStar star, Map map) {
        super(x, y, dir, chunk, radius, map);
        this.star = star;
        this.orbitDistance = 60;
        this.orbitPeriod = 400;
        this.rotSpeed = 0.05f;
        this.color = new float[]{0.5f, 0.5f, 0.5f};

        this.terrain = new float[]{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 };
    }

    @Override
    public void update() {
        super.update();

        float angle = this.map.mapTime * (float)(Math.PI / 2) / this.orbitPeriod;

        this.x = MathHelper.rotX(angle, this.orbitDistance,0) + this.star.getX();
        this.y = MathHelper.rotY(angle, this.orbitDistance, 0) + this.star.getY();
        //this.x += Math.cos(0) * this.orbitSpeed;
        //this.y += Math.sin(0) * this.orbitSpeed;
    }
}
