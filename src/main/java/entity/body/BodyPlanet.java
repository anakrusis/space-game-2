package entity.body;

import entity.Body;
import entity.EntityBuilding;
import entity.ParticleOrbit;
import entity.body.BodyGravityRadius;
import entity.body.BodyStar;
import entity.building.BuildingApartment;
import entity.building.BuildingFactory;
import util.MathHelper;
import util.RandomUtil;
import world.Chunk;
import world.Map;
import world.Nation;

public class BodyPlanet extends Body {

    // The star which the planet orbits around, how far away it orbits, and how often
    BodyStar star;
    private float orbitDistance;
    private int orbitPeriod;
    private float orbitStart;
    private float orbitAngle;
    private EntityBuilding[] buildings;
    private float[] stoneColor;

    private int terrainSize = 40;

    private int population;
    private Nation nation;

    public BodyPlanet(double x, double y, float dir, Chunk chunk, float orbitDistance, BodyStar star, Map map, String name) {
        super(x, y, dir, chunk, RandomUtil.fromRangeF(16,32), map);
        this.star = star;
        this.orbitDistance = orbitDistance;
        this.orbitPeriod = 16000;
        //this.rotSpeed = 0.05f;
        this.rotSpeed = 0.0005f;

        // the stone color, or whatever
        this.stoneColor = new float[]{0.5f, 0.5f, 0.5f};
        this.color = new float[]{RandomUtil.fromRangeF(0f,1f), RandomUtil.fromRangeF(0f,1f), RandomUtil.fromRangeF(0f,1f)};
        this.canEntitiesCollide = true;

        this.terrain = new float[terrainSize];
        for (int i = 0; i < terrain.length; i++){
            this.terrain[i] = RandomUtil.fromRangeF(-0.5f,1.6f);
        }

        this.name = name;

        BodyGravityRadius bgr = new BodyGravityRadius(this.x, this.y, this.dir, this.chunk, this.radius * 2, this.map, this);
        this.chunk.getBodies().add(bgr);
        this.orbitStart =  RandomUtil.fromRangeF(0f,(float)Math.PI * 2);

        buildings = new EntityBuilding[terrain.length];
        population = 0;

        this.nation = null; // Unclaimed by default
    }

    public BodyPlanet (double x, double y, float dir, Chunk chunk, float orbitDistance, BodyStar star, Map map){
        this(x, y, dir, chunk, orbitDistance, star, map, "Planet " + chunk.getX() + " " + chunk.getY());
    }

    @Override
    public void update() {
        super.update();

        // This moves the planet around the star in orbit
        this.orbitAngle = this.orbitStart + (this.map.mapTime * (float)(Math.PI / 2) / this.orbitPeriod);
        this.x = MathHelper.rotX(this.orbitAngle, this.orbitDistance,0) + this.star.getX();
        this.y = MathHelper.rotY(this.orbitAngle, this.orbitDistance, 0) + this.star.getY();

        // Calculating the planet's population from the sum of individual apt buildings
        int pop = 0;
        for (int i = 0; i < terrainSize; i++){
            EntityBuilding build = this.buildings[i];
            if (build instanceof BuildingApartment){
                pop += ((BuildingApartment) build).getPopulation();
            }
        }
        this.population = pop;

        // Calculating the amount of workers per factory from the population
        // Todo evenly distribute workers among factories
        for (int i = 0; i < terrainSize; i++){
            EntityBuilding build = this.buildings[i];
            if (build instanceof BuildingFactory){
                BuildingFactory fact = (BuildingFactory)build;
                fact.setEmployees(0);
                if (pop > 0){
                    int subAmt = Math.min(fact.getCapacity(), pop);
                    fact.setEmployees(subAmt);
                    pop -= subAmt;
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

    public EntityBuilding[] getBuildings() {
        return buildings;
    }

    public float[] getStoneColor() {
        return stoneColor;
    }

    public double[] getStonePoints(){
        double[] stonePoints = new double[terrain.length * 2];

        for (int i = 0; i < terrain.length; i++){
            double angle = this.dir + (i * (2 * Math.PI) / terrain.length);

            double terrane = Math.min(0, terrain[i]);
            double pointx = MathHelper.rotX((float) angle, this.radius + terrane, 0.0d) + this.x;
            double pointy = MathHelper.rotY((float) angle, this.radius + terrane, 0.0d) + this.y;

            stonePoints[2 * i] = pointx;
            stonePoints[(2 * i) + 1] = pointy;
        }

        return stonePoints;
    }

    public int getPopulation() {
        return population;
    }

    public float getOrbitDistance() {
        return orbitDistance;
    }

    public void setNation(Nation nation) {
        this.nation = nation;
    }

    public Nation getNation() {
        return nation;
    }
}
