package com.adnre.spacegame.world;

import com.adnre.spacegame.entity.*;
import com.adnre.spacegame.entity.body.Body;
import com.adnre.spacegame.entity.body.BodyPlanet;
import com.adnre.spacegame.entity.body.BodyStar;
import com.adnre.spacegame.entity.building.BuildingApartment;
import com.adnre.spacegame.entity.building.BuildingFactory;
import com.adnre.spacegame.entity.building.BuildingSpaceport;
import com.adnre.spacegame.entity.building.EntityBuilding;
import com.adnre.spacegame.entity.part.PartChassisWood;
import com.adnre.spacegame.util.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class World implements Serializable {
    transient private Chunk[][] chunks;
    transient private ArrayList<Chunk> loadedChunks;
    private HashMap<UUID, Entity> entities;
    private HashMap<UUID, Nation> nations;
    private EntityCursor cursor;

    // In chunks
    private int width;
    private int height;
    private long seed;

    public int mapTime;

    private UUID homePlanetUUID;
    private UUID homeStarUUID;
    private UUID playerNationUUID;

    private int homeChunkX;
    private int homeChunkY;
    private int homeIndex;

    // This is kinda hacky, but it means that the player initially spawns on the second game tick,
    // allowing the planets' positions in orbit to be initialized
    public int playerLastDeathTime;
    private int RESPAWN_INTERVAL = 100;

    private static final long serialVersionUID = 3898234898092L;

    public World(int xSize, int ySize, int mapTime, long seed){
        this.seed = seed;
        RandomUtil.setSeed(seed);
        chunks = new Chunk[xSize][ySize];
        this.width = xSize;
        this.height = ySize;

        // For non-body entities like spaceships and animals and people...
        entities = new HashMap<>();
        nations = new HashMap<>();

        for (int x = 0; x < xSize; x++){
            for (int y = 0; y < ySize; y++){
                chunks[x][y] = new Chunk(x,y,this);
            }
        }

        this.mapTime = mapTime;
        this.cursor = new EntityCursor(0,0,0,this);
        playerLastDeathTime = mapTime - 99;

        // Init of player nation stuff

        BodyPlanet homePlanet = SpawnUtil.newHomePlanet(this);
        BodyStar homeStar = homePlanet.getStar();
        String nationName = NymGen.newName() + " Nation";

        Nation playerNation = new Nation(nationName, homeStar, homePlanet);
        homePlanet.setNationUUID(playerNation.getUuid());
        nations.put(playerNation.getUuid(), playerNation);

        Nation rivalNation = new Nation(nationName, homeStar, homePlanet);
        rivalNation.setColor( new float[] { 0.7f, 0.2f, 0.2f } );
        nations.put(rivalNation.getUuid(), rivalNation);

        this.playerNationUUID = playerNation.getUuid();
        this.homePlanetUUID = homePlanet.getUuid();
        this.homeStarUUID = homeStar.getUuid();
        this.homeChunkX = homePlanet.getChunk().getX();
        this.homeChunkY = homePlanet.getChunk().getY();
        this.homeIndex = 0;

        homePlanet.spawnCity(0, playerNationUUID);
        homePlanet.spawnCity(40, rivalNation.getUuid());
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
                            planet.getBuildingUUIDs()[((EntityBuilding) currentEntity).getPlanetIndex()] = null;
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

            EntityPlayer player = new EntityPlayer(0,0,(float)Math.PI, this);
            this.spawnEntity(player);

            // This is for all respawns
            this.getPlayer().moveToIndexOnPlanet(homeIndex, getHomePlanet());
            this.getPlayer().setNationUUID(getPlayerNation().getUuid());
        }
        this.mapTime++;
    }

    public BodyStar getHomeStar() {
        return getHomePlanet().getStar();
    }

    public BodyPlanet getHomePlanet() {
        return (BodyPlanet) chunks[homeChunkX][homeChunkY].getBodies().get(homePlanetUUID);
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

    public Nation getPlayerNation() {
        return nations.get(playerNationUUID);
    }

    public UUID getPlayerNationUUID() {
        return playerNationUUID;
    }

    public void spawnEntity(Entity entity){
        this.entities.put(entity.getUuid(), entity);
    }

    public long getSeed() {
        return seed;
    }

    public HashMap<UUID, Nation> getNations() {
        return nations;
    }
}
