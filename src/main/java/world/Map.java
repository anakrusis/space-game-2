package world;

import entity.*;
import util.CollisionUtil;
import util.SpawnUtil;
import world.Chunk;

import java.util.ArrayList;

public class Map {
    private Chunk[][] chunks;
    private ArrayList<Entity> entities;

    // In chunks
    private int width;
    private int height;

    public int mapTime;
    private BodyPlanet homePlanet = null;
    private BodyStar homeStar = null;

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
            // This is just a first time kind of thing
            if (homePlanet == null){
                homePlanet = SpawnUtil.newHomePlanet(this);
                homeStar = homePlanet.getStar();

                BuildingFactory factory = new BuildingFactory(homePlanet.getX() + homePlanet.getRadius() + 5,
                        homePlanet.getY(), 0, this);
                entities.add(factory);
            }
            double spawnx = homePlanet.getX() + homePlanet.getRadius();
            double spawny = homePlanet.getY();
            entities.add(new EntityPlayer(spawnx,spawny,(float)Math.PI, this));

            int index = CollisionUtil.terrainIndexFromEntityAngle(this.getPlayer(), homePlanet);
            double radius = homePlanet.getRadius() + homePlanet.getTerrain()[index];
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
}
