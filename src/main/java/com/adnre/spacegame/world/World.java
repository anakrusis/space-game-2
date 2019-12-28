package com.adnre.spacegame.world;

import com.adnre.spacegame.entity.*;
import com.adnre.spacegame.entity.body.BodyPlanet;
import com.adnre.spacegame.entity.body.BodyStar;
import com.adnre.spacegame.entity.building.BuildingApartment;
import com.adnre.spacegame.entity.building.BuildingFactory;
import com.adnre.spacegame.util.CollisionUtil;
import com.adnre.spacegame.util.NymGen;
import com.adnre.spacegame.util.SpawnUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class World implements Serializable {
    transient private Chunk[][] chunks;
    transient private ArrayList<Chunk> loadedChunks;
    private HashMap<UUID, Entity> entities;

    // In chunks
    private int width;
    private int height;

    public int mapTime;
    private BodyPlanet homePlanet = null;
    private BodyStar homeStar = null;
    private Nation playerNation = null;

    private EntityCursor cursor;

    // This is kinda hacky, but it means that the player initially spawns on the second game tick,
    // allowing the planets' positions in orbit to be initialized
    public int playerLastDeathTime;
    private int RESPAWN_INTERVAL = 100;

    private static final long serialVersionUID = 3898234898092L;

    public World(int xSize, int ySize, int mapTime, long seed){
        chunks = new Chunk[xSize][ySize];
        this.width = xSize;
        this.height = ySize;

        // For non-body entities like spaceships and animals and people...
        entities = new HashMap<>();

        for (int x = 0; x < xSize; x++){
            for (int y = 0; y < ySize; y++){
                chunks[x][y] = new Chunk(x,y,this);
            }
        }

        this.mapTime = mapTime;
        this.cursor = new EntityCursor(0,0,0,this);
        playerLastDeathTime = mapTime - 99;
    }
    public World(int xSize, int ySize, long seed){
        this(xSize, ySize, 0, seed);
    }


    public Chunk[][] getChunks(){
        return chunks;
    }

    public HashMap<UUID, Entity> getEntities(){
        return entities;
    }

    public EntityPlayer getPlayer(){
        for (Entity entity : entities.values()){
            if (entity instanceof EntityPlayer){
                return (EntityPlayer)entity;
            }
        }
        return null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    // For lazy folks
    public int getChunkCount(){
        return width * height;
    }

    public void update(){

        // Deleting entities marked dead, or if living, updating them
        ArrayList<UUID> markedForDeath = new ArrayList<>();
        ArrayList<UUID> markedForUpdate = new ArrayList<>();

        for (java.util.Map.Entry<UUID, Entity> e : entities.entrySet()) {
            Entity currentEntity = e.getValue();
            UUID currentKey = e.getKey();

            if (currentEntity.isDead()) {

                // For cleaning up the building pointers when a building is destroyed
                if (currentEntity instanceof EntityBuilding && currentEntity.isGrounded()) {
                    if (currentEntity.getGroundedBody() instanceof BodyPlanet) {
                        BodyPlanet planet = (BodyPlanet) currentEntity.getGroundedBody();
                        EntityBuilding building = (EntityBuilding) currentEntity;

                        // -1 is used for floating buildings
                        if (building.getPlanetIndex() != -1) {
                            planet.getBuildings()[((EntityBuilding) currentEntity).getPlanetIndex()] = null;
                        }
                    }
                }

                markedForDeath.add(currentKey);

            } else {
                markedForUpdate.add(currentKey);
            }
        }

        for (UUID uuid : markedForDeath){
            entities.remove(uuid);
        }
        for (UUID uuid : markedForUpdate){
            entities.get(uuid).update();
        }

        // Updating astronomical bodies per chunk
        for (Chunk[] chunk_array : this.getChunks()){
            for (Chunk chunk : chunk_array){
                if (chunk != null){
                    for (java.util.Map.Entry<UUID, Body> e : chunk.getBodies().entrySet()) {
                        Body body = e.getValue();
                        body.update();
                    }
                }
            }
        }

        if (this.mapTime - this.playerLastDeathTime == RESPAWN_INTERVAL){

            this.spawnEntity(new EntityPlayer(0,0,(float)Math.PI, this));

            // This is just a first time kind of thing
            if (playerNation == null){

                homePlanet = SpawnUtil.newHomePlanet(this);
                homeStar = homePlanet.getStar();
                String nationName = NymGen.newName() + " Nation";

                playerNation = new Nation(nationName, homeStar, homePlanet);
                homePlanet.setNation(playerNation);

//                BuildingFactory factory = new BuildingFactory(homePlanet.getX() + homePlanet.getRadius() + 5,
//                        homePlanet.getY(), 0, this, this.getPlayer());
//                BuildingApartment apt = new BuildingApartment(homePlanet.getX() + homePlanet.getRadius() + 5,
//                        homePlanet.getY() + 7, 0, this, this.getPlayer());
                //entities.add(factory);
                //entities.add(apt);
            }

            // This is for all respawns
            double spawnx = homePlanet.getX() + homePlanet.getRadius();
            double spawny = homePlanet.getY();
            this.getPlayer().setX(spawnx);
            this.getPlayer().setY(spawny);
            this.getPlayer().setNation(playerNation);

            float height = CollisionUtil.heightFromEntityAngle(this.getPlayer(), homePlanet);
            double radius = homePlanet.getRadius() + height + 2f;
            this.getPlayer().setX(homePlanet.getX() + radius);
            CollisionUtil.resolveCollision(this.getPlayer(), homePlanet);
        }
        this.mapTime++;
    }

    public BodyStar getHomeStar() {
        return homeStar;
    }

    public BodyPlanet getHomePlanet() {
        return homePlanet;
    }

    public EntityCursor getCursor() {
        return cursor;
    }

    public void setChunks(Chunk[][] chunks) {
        this.chunks = chunks;
    }

    public void setCursor(EntityCursor cursor) {
        this.cursor = cursor;
    }

    public void setHomeStar(BodyStar homeStar) {
        this.homeStar = homeStar;
    }

    public void setHomePlanet(BodyPlanet homePlanet) {
        this.homePlanet = homePlanet;
    }

    public Nation getPlayerNation() {
        return playerNation;
    }

    public void spawnEntity(Entity entity){
        UUID entityuuid = UUID.randomUUID();
        this.entities.put(entityuuid, entity);
        entity.setUuid(entityuuid);
    }
}
