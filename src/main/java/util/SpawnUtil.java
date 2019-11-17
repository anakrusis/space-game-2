package util;

import entity.Body;
import entity.BodyPlanet;
import world.Chunk;
import world.Map;

import java.util.*;

public class SpawnUtil {

    static Random rand = new Random();

    public static double[] playerNewRespawnPos(Map map){

        Chunk[][] chunks = map.getChunks();
        Chunk[][] shuffled_chunks = new Chunk[chunks.length][chunks[0].length];
        for (int x = 0; x < chunks.length; x++){
            System.arraycopy(chunks[x], 0, shuffled_chunks[x], 0, chunks[0].length);
        }

        List<Chunk> shuffledRow;
        List<Chunk[]> shuffledCols;
        ArrayList<Body> shuffledbodies;

        // Each of the rows are shuffled diabolically
        for (int x = 0; x < map.getHeight(); x++){
            shuffledRow = Arrays.asList( shuffled_chunks[x] );
            Collections.shuffle(shuffledRow);
            shuffled_chunks[x] = shuffledRow.toArray(shuffled_chunks[x]);
        }
        // Then, later, the columns are shuffled, equally diabolically
        shuffledCols = Arrays.asList ( shuffled_chunks );
        Collections.shuffle(shuffledCols);
        shuffled_chunks = shuffledCols.toArray(shuffled_chunks);

        double spawnx;
        double spawny;

        // This ensures that the first chunk we randomly pick to spawn in isn't always 0,0
        // But that we also go through each one at least once, trying to find a spot to
        // plop the player down on.

        for (int x = 0; x < map.getWidth(); x++){
            for (int y = 0; y < map.getHeight(); y++){

                shuffledbodies = shuffled_chunks[x][y].getBodies();
                for (int z = 0; z < shuffledbodies.size(); z++){
                    if (shuffledbodies.get(z) instanceof BodyPlanet){
                        BodyPlanet planet = (BodyPlanet)shuffledbodies.get(z);
                        spawnx = planet.getX() + planet.getRadius();
                        spawny = planet.getY();
                        return new double[] {spawnx, spawny};
                    }
                }
            }
        }
        return new double[] {64, 64};
    }
}
