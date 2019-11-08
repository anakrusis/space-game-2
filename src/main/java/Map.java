public class Map {
    private Chunk[][] chunks;

    public Map (int xSize, int ySize){
        chunks = new Chunk[xSize][ySize];

        for (int x = 0; x < xSize; x++){
            for (int y = 0; y < ySize; y++){
                chunks[x][y] = new Chunk(x,y);
            }
        }
    }

    public Chunk[][] getChunks(){
        return chunks;
    }
}
