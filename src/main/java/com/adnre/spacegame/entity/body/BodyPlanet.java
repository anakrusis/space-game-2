package com.adnre.spacegame.entity.body;

import com.adnre.spacegame.SpaceGame;
import com.adnre.spacegame.entity.building.*;
import com.adnre.spacegame.util.CollisionUtil;
import com.adnre.spacegame.util.MathHelper;
import com.adnre.spacegame.util.NymGen;
import com.adnre.spacegame.util.RandomUtil;
import com.adnre.spacegame.world.Chunk;
import com.adnre.spacegame.world.City;
import com.adnre.spacegame.world.World;
import com.adnre.spacegame.world.Nation;

import java.util.HashMap;
import java.util.UUID;

public class BodyPlanet extends Body {

    // The id of the star which the planet orbits around, how far away it orbits, and how often
    UUID starUUID;
    private float orbitDistance;
    private int orbitPeriod;
    private float orbitStart;
    private float orbitAngle;
    private UUID[] buildingUUIDs;
    private float[] stoneColor;

    private int terrainSize;
    private float atmosphericDensity;

    private int population;
    private UUID nationUUID;

    private HashMap<UUID, City> cities;

    public BodyPlanet(double x, double y, float dir, Chunk chunk, float orbitDistance, UUID starUUID, World world, String name) {
        super(x, y, dir, chunk, RandomUtil.fromRangeF(32,64), world);
        this.starUUID = starUUID;
        this.orbitDistance = orbitDistance;
        this.orbitPeriod = RandomUtil.fromRangeI(90000, 200000);
        this.rotSpeed = 0.0005f;
        this.atmosphericDensity = RandomUtil.nextFloat(1);

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

        this.orbitStart =  RandomUtil.fromRangeF(0f,(float)Math.PI * 2);

        buildingUUIDs = new UUID[terrain.length];
        population = 0;

        this.nationUUID = null; // Unclaimed by default
        cities = new HashMap<>();
    }

    public BodyPlanet (double x, double y, float dir, Chunk chunk, float orbitDistance, UUID starUUID, World world){
        this(x, y, dir, chunk, orbitDistance, starUUID, world, "Planet " + chunk.getX() + " " + chunk.getY());
    }

    @Override
    public void update() {
        super.update();

        // This moves the planet around the star in orbit
        this.orbitAngle = this.orbitStart + (this.world.mapTime * (float)(Math.PI / 2) / this.orbitPeriod);
        this.x = MathHelper.rotX(this.orbitAngle, this.orbitDistance,0) + this.getStar().getX();
        this.y = MathHelper.rotY(this.orbitAngle, this.orbitDistance, 0) + this.getStar().getY();

        // Calculating the planet's population from the sum of individual apt buildings,
        // and the number of factories from the sum of individual factories.
        int pop = 0;
        int factoryCount = 0;

        for (int i = 0; i < terrainSize; i++){
            EntityBuilding build = getBuilding(i);
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
            EntityBuilding build = this.getBuilding(i);
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
                    EntityBuilding build = this.getBuilding(j);
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

        for (City city : cities.values()){
            city.update();
        }
    }

    public float getOrbitAngle() {
        return orbitAngle;
    }

    public BodyStar getStar() {
        return (BodyStar) this.chunk.getBodies().get(starUUID);
    }

    public UUID getStarUUID() {
        return starUUID;
    }

    public UUID[] getBuildingUUIDs() {
        return buildingUUIDs;
    }

    public EntityBuilding getBuilding (int index){
        UUID key = this.buildingUUIDs[index];
        if (key != null){
            return (EntityBuilding) SpaceGame.world.getEntities().get( key );
        }else{
            return null;
        }
    }

    public int getPopulation() {
        return population;
    }

    public float getOrbitDistance() {
        return orbitDistance;
    }

    public void setNationUUID(UUID uuid) {
        this.nationUUID = uuid;
    }

    public Nation getNation() {
        return world.getNations().get(nationUUID);
    }

    public int getTerrainSize() {
        return terrainSize;
    }

    public float getOrbitStart() {
        return orbitStart;
    }

    public void spawnBuilding(EntityBuilding building, int index){
        if (this.buildingUUIDs[index] == null){
            world.spawnEntity(building);

            buildingUUIDs[index] = building.getUuid();
            building.setPlanetIndex(index);
            building.setGrounded(true);
            building.setGroundedBodyUUID(this.uuid);

            building.moveToIndexOnPlanet(index, this);
        }
    }

    public void spawnCity(City city, int index, UUID nation){

        // Spawns a city centered around the index 0 of the planet.
        spawnBuilding(new BuildingSpaceport(0, 0, 0, world, nation), index);

        int cityhallindex = MathHelper.loopyMod( index - 1, this.getTerrainSize() );
        spawnBuilding(new BuildingCityHall(0, 0, 0, world, nation), cityhallindex);

        for (int i = -5; i < 5; i++){
            int ind2 = MathHelper.loopyMod( index + i, this.getTerrainSize() );
            EntityBuilding building;

            // Apartments are randomly filled between half and full capacity.
            if (RandomUtil.fromRangeI(0, 100) > 76){
                building = new BuildingApartment(0, 0, 0, world, nation);
                BuildingApartment apt = ((BuildingApartment) building);
                int pop = RandomUtil.fromRangeI( apt.getCapacity()/2, apt.getCapacity() );
                apt.setPopulation( pop );
            }else{
                building = new BuildingFactory(0, 0, 0, world, nation);
            }
            this.spawnBuilding(building, ind2);
            city.getTerrainIndexes().add(ind2);
        }
        this.cities.put(city.getUuid(), city);
    }

    public float getAtmosphericDensity() {
        return atmosphericDensity;
    }

    // Searches for a city which has this terrain index (should be more efficient later, idK)
    public City getCityAtIndex(int index){
        for (City city: cities.values()){
            for (Integer ci : city.getTerrainIndexes()){
                if (ci == index){
                    return city;
                }
            }
        }
        return null;
    }
}
