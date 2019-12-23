package com.adnre.spacegame;

import com.adnre.spacegame.entity.Body;
import com.adnre.spacegame.world.Chunk;

import java.io.*;

public class FileHandler {
    public static void writeChunkToFile(Chunk chunk, String filename){
        try {
            File file = new File("world");
            file.mkdir();

            FileOutputStream stream = new FileOutputStream(filename);
            ObjectOutputStream objStream = new ObjectOutputStream(stream);

            objStream.writeObject(chunk);
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
}
