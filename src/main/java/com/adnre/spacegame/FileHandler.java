package com.adnre.spacegame;

import com.adnre.spacegame.entity.Body;
import com.adnre.spacegame.entity.Entity;
import com.adnre.spacegame.world.Chunk;
import com.adnre.spacegame.world.World;

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
        chunk.setWorld(SpaceGame.world);
        for (Body body : chunk.getBodies()){
            body.setWorld(SpaceGame.world);
        }
        return chunk;
    }

    public static World readMapFromFile(String filename) throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(file);
        World world = (World) in.readObject();

        in.close();
        file.close();
        for (Entity entity : world.getEntities().values()){
            entity.setWorld(world);
        }
        world.getCursor().setWorld(world);
        return world;
    }

    public static Object readObject (String filename) throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(file);
        Object obj = in.readObject();

        in.close();
        file.close();
        return obj;
    }
}
