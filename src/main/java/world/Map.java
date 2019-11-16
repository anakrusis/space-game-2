package world;

import entity.Entity;
import entity.EntityPlayer;
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

        double[] spawnpos = SpawnUtil.playerNewRespawnPos(this);
        //double[] spawnpos = new double[]{700, 64};
        entities.add(new EntityPlayer(spawnpos[0],spawnpos[1],0, this));
        //System.out.println(this.getPlayer().getChunk().getX());
        //System.out.println(this.getPlayer().getChunk().getY());

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
}
