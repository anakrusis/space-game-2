package com.adnre.spacegame.util;

import com.adnre.spacegame.entity.Body;
import com.adnre.spacegame.entity.body.BodyPlanet;
import com.adnre.spacegame.world.Chunk;
import com.adnre.spacegame.world.World;

import java.util.*;

public class SpawnUtil {

    static Random rand = new Random();

    // This new version of the random planet finder is fully seeded, and way smaller and simpler.
    // The only problem is it keeps going forever if there's no planets on the map.
    public static BodyPlanet newHomePlanet(World world){
        while (true){
            int xIndex = RandomUtil.fromRangeI(0, world.getWidth());
            int yIndex = RandomUtil.fromRangeI(0, world.getHeight());

            Chunk chunk = world.getChunks()[xIndex][yIndex];
            if (chunk != null){
                for (Body body : chunk.getBodies().values()) {
                    if (body instanceof BodyPlanet){
                        return (BodyPlanet) body;
                    }
                }
            }
        }
    }
}
