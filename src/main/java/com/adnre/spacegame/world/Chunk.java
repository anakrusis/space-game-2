package com.adnre.spacegame.world;

import com.adnre.spacegame.entity.Body;
import com.adnre.spacegame.entity.body.BodyPlanet;
import com.adnre.spacegame.entity.body.BodyStar;
import com.adnre.spacegame.util.GenUtil;
import com.adnre.spacegame.util.NymGen;
import com.adnre.spacegame.util.RandomUtil;
import com.adnre.spacegame.util.Reference;

import java.io.Serializable;
import java.util.ArrayList;

public class Chunk implements Serializable {
    private int x;
    private int y;

    // the map is not serialized because we would all hate to have 8000 copies of the same map in your world file :(
    transient private World world;
    private static final long serialVersionUID = 239418290893842389L;

    private ArrayList<Body> bodies;

    private ChunkChangelog chunkChangelog;

    public Chunk (int x, int y, World world){
        this.x = x;
        this.y = y;
        this.world = world;
        this.bodies = new ArrayList<>();
        this.chunkChangelog = new ChunkChangelog();

        // Makes five attempts to spawn a star within the chunk padding.
        // If all five of these attempts fail then fuggedaboutit, no star in the chunk.
        for (int i = 0; i < 5; i++){
            double genx = Reference.CHUNK_SIZE * (this.x + RandomUtil.fromRangeF(0f, 1f));
            double geny = Reference.CHUNK_SIZE * (this.y + RandomUtil.fromRangeF(0f, 1f));

            if (GenUtil.withinPadding(genx, geny, 1100)){
                String name = NymGen.newName();
                this.bodies.add(new BodyStar(genx, geny, 0, this, this.world, name));
                break;
            }
        }

        // Every star gets potentially up to 4 planets
        int planetcount = 0;
        int orbitDistanceInterval = 200;
        int orbitVariance = 40;

        for (int i = 0; i < this.bodies.size(); i++){
            Body body = this.bodies.get(i);
            if (body instanceof BodyStar){
                int planetnum = orbitDistanceInterval * RandomUtil.fromRangeI(2, 6) + orbitDistanceInterval;
                for (int planetdist = 2 * orbitDistanceInterval; planetdist < planetnum; planetdist += orbitDistanceInterval){

                    float orbitDistance = RandomUtil.fromRangeF(planetdist - orbitVariance,planetdist + orbitVariance);

                    String name = body.getName() + " " + NymGen.greekLetters()[planetcount];

                    BodyPlanet planet = new BodyPlanet(body.getX() + orbitDistance, body.getY(), 0, this,
                            orbitDistance, (BodyStar)body, this.world, name );

                    ((BodyStar) body).getPlanets().add(planet);
                    this.bodies.add(planet);
                    planetcount++;
                }
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ArrayList<Body> getBodies() {
        return bodies;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public ChunkChangelog getChunkChangelog() {
        return chunkChangelog;
    }

    // Returns the which star it is in the chunk (usually theres only 1 so anything else would return null)
    public BodyStar getStar( int index ){
        int counter = 0; // Counts the number of stars which have been iterated through
        for (int i = 0; i < this.getBodies().size(); i++){
            if (this.getBodies().get(i) instanceof BodyStar){
                if (counter == index){
                    return (BodyStar)this.getBodies().get(i);
                }else{
                    counter++;
                }
            }
        }
        return null;
    }
}
