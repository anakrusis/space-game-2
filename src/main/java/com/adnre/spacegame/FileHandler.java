package com.adnre.spacegame;

import com.adnre.spacegame.entity.Body;
import com.adnre.spacegame.entity.Entity;
import com.adnre.spacegame.world.Chunk;
import com.adnre.spacegame.world.Map;

import java.io.*;

public class FileHandler {
    public static void writeObjectToFile(Object obj, String filename){
        try {
            File file = new File("world");
            file.mkdir();

            FileOutputStream stream = new FileOutputStream(filename);
            ObjectOutputStream objStream = new ObjectOutputStream(stream);

            objStream.writeObject(obj);
            objStream.close();
            stream.close();

        } catch (IOException exception){

        }
    }

    public static Chunk readChunkFromFile(String filename) throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(file);
        Chunk chunk = (Chunk) in.readObject();

        in.close();
        file.close();
        chunk.setMap(SpaceGame.map);
        for (Body body : chunk.getBodies()){
            body.setMap(SpaceGame.map);
        }
        return chunk;
    }

    public static Map readMapFromFile(String filename) throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(file);
        Map map = (Map) in.readObject();

        in.close();
        file.close();
        for (Entity entity : map.getEntities()){
            entity.setMap(map);
        }
        map.getCursor().setMap(map);
        return map;
    }
}
