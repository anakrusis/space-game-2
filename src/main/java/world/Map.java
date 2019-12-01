package world;

import entity.*;
import entity.body.BodyPlanet;
import entity.body.BodyStar;
import entity.building.BuildingApartment;
import entity.building.BuildingFactory;
import util.CollisionUtil;
import util.SpawnUtil;

import java.util.ArrayList;

public class Map {
    private Chunk[][] chunks;
    private ArrayList<Chunk> loadedChunks;
    private ArrayList<Entity> entities;

    // In chunks
    private int width;
    private int height;

    public int mapTime;
    private BodyPlanet homePlanet = null;
    private BodyStar homeStar = null;

    EntityCursor cursor;

    // This is kinda hacky, but it means that the player initially spawns on the second game tick,
    // allowing the planets' positions in orbit to be initialized
    public int playerLastDeathTime = -99;
    private int RESPAWN_INTERVAL = 100;

    public Map (int xSize, int ySize){
        chunks = new Chunk[xSize][ySize];
        this.width = xSize;
        this.height = ySize;

        // For non-body entities like spaceships and animals and people...
        entities = new ArrayList<Entity>();

        for (int x = 0; x < xSize; x++){
            for (int y = 0; y < ySize; y++){
                chunks[x][y] = new Chunk(x,y,this);
            }
        }

        this.mapTime = 0;
        this.cursor = new EntityCursor(0,0,0,this);
    }

    public Chunk[][] getChunks(){
        return chunks;
    }

    public ArrayList<Entity> getEntities(){
        return entities;
    }

    public EntityPlayer getPlayer(){
        for (Entity entity : entities){
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
        if (this.mapTime - this.playerLastDeathTime == RESPAWN_INTERVAL){

            entities.add(new EntityPlayer(0,0,(float)Math.PI, this));

            // This is just a first time kind of thing
            if (homePlanet == null){
                homePlanet = SpawnUtil.newHomePlanet(this);
                homeStar = homePlanet.getStar();

                BuildingFactory factory = new BuildingFactory(homePlanet.getX() + homePlanet.getRadius() + 5,
                        homePlanet.getY(), 0, this, this.getPlayer());
                BuildingApartment apt = new BuildingApartment(homePlanet.getX() + homePlanet.getRadius() + 5,
                        homePlanet.getY() + 7, 0, this, this.getPlayer());
                entities.add(factory);
                entities.add(apt);
            }

            // This is for all respawns
            double spawnx = homePlanet.getX() + homePlanet.getRadius();
            double spawny = homePlanet.getY();
            this.getPlayer().setX(spawnx);
            this.getPlayer().setY(spawny);

            float height = CollisionUtil.heightFromEntityAngle(this.getPlayer(), homePlanet);
            double radius = homePlanet.getRadius() + height + 2f;
            this.getPlayer().setX(homePlanet.getX() + radius);
            CollisionUtil.resolveCollision(this.getPlayer(), homePlanet);
        }

        this.cursor.update();
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
}
