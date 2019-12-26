package com.adnre.spacegame.util;

import com.adnre.spacegame.entity.Body;
import com.adnre.spacegame.entity.body.BodyPlanet;
import com.adnre.spacegame.entity.body.BodyStar;
import com.adnre.spacegame.world.Chunk;
import com.adnre.spacegame.world.World;

public class WorldFixer {
    public static void removeDuplicateEntities(World world){
        Body homestar = world.getHomeStar();
        Body homeplanet = world.getHomePlanet();
        for (Chunk[] chunklist : world.getChunks()){
            for (Chunk chunk : chunklist){
                for (Body body : chunk.getBodies()){
                    if (body.equals(homestar)){
                        world.setHomeStar((BodyStar) body);
                    }else if (body.equals(homeplanet)){
                        world.setHomePlanet((BodyPlanet) body);
                    }
                }
            }
        }
    }
}
