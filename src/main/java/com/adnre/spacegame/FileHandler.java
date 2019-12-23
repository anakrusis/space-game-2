package com.adnre.spacegame;

import com.adnre.spacegame.world.Chunk;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class FileHandler {
    public static void writeChunkToFile(Chunk chunk, String filename){
        try {
            File file = new File(filename);
            file.mkdir();

            FileOutputStream stream = new FileOutputStream(filename);
            ObjectOutputStream objStream = new ObjectOutputStream(stream);

            objStream.writeObject(chunk);
            objStream.close();
            stream.close();

        } catch (IOException exception){

        }
    }
}
