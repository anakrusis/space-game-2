package entity;

import util.MathHelper;
import util.RandomUtil;
import world.Chunk;
import world.Map;

public class BodyPlanet extends Body {

    // The star which the planet orbits around, how far away it orbits, and how often
    BodyStar star;
    float orbitDistance;
    int orbitPeriod;
    private float orbitStart;
    private float orbitAngle;

    public BodyPlanet(double x, double y, float dir, Chunk chunk, float orbitDistance, BodyStar star, Map map) {
        super(x, y, dir, chunk, RandomUtil.fromRangeF(8,16), map);
        this.star = star;
        this.orbitDistance = orbitDistance;
        this.orbitPeriod = 16000;
        //this.rotSpeed = 0.05f;
        this.rotSpeed = 0.005f;
        this.color = new float[]{0.5f, 0.5f, 0.5f};
        this.canEntitiesCollide = true;

        this.terrain = new float[]{
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0 ,0 ,0 ,0 ,0,
                0 ,0 ,0 ,0, 0 };
        this.name = "Planet " + chunk.getX() + " " + chunk.getY();

        BodyGravityRadius bgr = new BodyGravityRadius(this.x, this.y, this.dir, this.chunk, this.radius * 2, this.map, this);
        this.chunk.getBodies().add(bgr);
        this.orbitStart =  RandomUtil.fromRangeF(0f,(float)Math.PI * 2);
    }

    @Override
    public void update() {
        super.update();

        this.orbitAngle = this.orbitStart + (this.map.mapTime * (float)(Math.PI / 2) / this.orbitPeriod);

        this.x = MathHelper.rotX(this.orbitAngle, this.orbitDistance,0) + this.star.getX();
        this.y = MathHelper.rotY(this.orbitAngle, this.orbitDistance, 0) + this.star.getY();
        //this.x += Math.cos(0) * this.orbitSpeed;
        //this.y += Math.sin(0) * this.orbitSpeed;

        if (this.ticksExisted % 250 == 0 && this.map.getPlayer() != null){
            if (this.map.getPlayer().getChunk() != null){
                if (this.map.getPlayer().getChunk() == this.chunk){
                    this.map.getEntities().add( new ParticleOrbit(this.x, this.y, this.dir, this.map) );
                }
            }
        }
    }

    public float getOrbitAngle() {
        return orbitAngle;
    }

    public BodyStar getStar() {
        return star;
    }
}
