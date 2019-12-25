package com.adnre.spacegame.entity.body;

import com.adnre.spacegame.entity.Body;
import com.adnre.spacegame.entity.EntityBuilding;
import com.adnre.spacegame.entity.building.BuildingApartment;
import com.adnre.spacegame.entity.building.BuildingFactory;
import com.adnre.spacegame.util.MathHelper;
import com.adnre.spacegame.util.RandomUtil;
import com.adnre.spacegame.world.Chunk;
import com.adnre.spacegame.world.Map;
import com.adnre.spacegame.world.Nation;

public class BodyPlanet extends Body {

    // The star which the planet orbits around, how far away it orbits, and how often
    BodyStar star;
    private float orbitDistance;
    private int orbitPeriod;
    private float orbitStart;
    private float orbitAngle;
    private EntityBuilding[] buildings;
    private float[] stoneColor;

    private int terrainSize;

    private int population;
    private Nation nation;

    public BodyPlanet(double x, double y, float dir, Chunk chunk, float orbitDistance, BodyStar star, Map map, String name) {
        super(x, y, dir, chunk, RandomUtil.fromRangeF(32,64), map);
        this.star = star;
        this.orbitDistance = orbitDistance;
        this.orbitPeriod = 16000;
        //this.rotSpeed = 0.05f;
        this.rotSpeed = 0.0005f;

        // the stone color, or whatever
        this.stoneColor = new float[]{0.5f, 0.5f, 0.5f};
        this.color = new float[]{RandomUtil.fromRangeF(0f,1f), RandomUtil.fromRangeF(0f,1f), RandomUtil.fromRangeF(0f,1f)};
        this.canEntitiesCollide = true;

        this.terrainSize = (int)(this.radius * (40/16));
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

        // Calculating the planet's population from the sum of individual apt buildings,
        // and the number of factories from the sum of individual factories.
        int pop = 0;
        int factoryCount = 0;
        if (this.buildings == null){
            this.buildings = new EntityBuilding[terrainSize];
        }
        for (int i = 0; i < terrainSize; i++){
            EntityBuilding build = this.buildings[i];
            if (build instanceof BuildingApartment){
                pop += ((BuildingApartment) build).getPopulation();
            }else if (build instanceof BuildingFactory){
                factoryCount++;
            }
        }
        this.population = pop;

        // This stuff calculates the amount of workers per factory from the population.

        // Distributes the quotient among factories
        for (int i = 0; i < terrainSize; i++){
            EntityBuilding build = this.buildings[i];
            if (build instanceof BuildingFactory){
                BuildingFactory fact = (BuildingFactory)build;
                fact.setEmployees(0);
                int subAmt = 0;
                if (pop > 0) {
                    subAmt = this.population / factoryCount;
                    fact.setEmployees(Math.min(fact.getEmployees() + subAmt, fact.getCapacity()));
                    pop -= subAmt;
                }
            }
        }
        // Distributes the remainder among factories
        if (factoryCount > 0){
            int i = this.population % factoryCount;
            while (i > 0){
                for (int j = 0; j < terrainSize; j++){
                    EntityBuilding build = this.buildings[j];
                    if (build instanceof BuildingFactory) {
                        BuildingFactory fact = (BuildingFactory) build;
                        if (i > 0){
                            fact.setEmployees(Math.min(fact.getEmployees() + 1,fact.getCapacity()));
                            i--;
                        }
                    }
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

    public int getTerrainSize() {
        return terrainSize;
    }

    public void setBuildings(EntityBuilding[] buildings) {
        this.buildings = buildings;
    }

    public float getOrbitStart() {
        return orbitStart;
    }
}
