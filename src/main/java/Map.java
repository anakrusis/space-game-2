import java.lang.reflect.Array;
import java.util.ArrayList;

public class Map {
    private Chunk[][] chunks;
    private ArrayList<Entity> entities;

    public Map (int xSize, int ySize){
        chunks = new Chunk[xSize][ySize];

        // For non-body entities like spaceships and animals and people...
        entities = new ArrayList<Entity>();

        entities.add(new EntityPlayer(0,0,0));

        for (int x = 0; x < xSize; x++){
            for (int y = 0; y < ySize; y++){
                chunks[x][y] = new Chunk(x,y);
            }
        }
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
}
