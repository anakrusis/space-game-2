package com.adnre.spacegame.util;

import com.adnre.spacegame.entity.Body;
import com.adnre.spacegame.entity.body.BodyPlanet;
import com.adnre.spacegame.entity.body.BodyStar;
import com.adnre.spacegame.world.Chunk;
import com.adnre.spacegame.world.Map;

public class WorldFixer {
    public static void removeDuplicateEntities(Map map){
        Body homestar = map.getHomeStar();
        Body homeplanet = map.getHomePlanet();
        for (Chunk[] chunklist : map.getChunks()){
            for (Chunk chunk : chunklist){
                for (Body body : chunk.getBodies()){
                    if (body.equals(homestar)){
                        map.setHomeStar((BodyStar) body);
                    }else if (body.equals(homeplanet)){
                        map.setHomePlanet((BodyPlanet) body);
                    }
                }
            }
        }
    }
}
