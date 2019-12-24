package com.adnre.spacegame.gui;

import com.adnre.spacegame.FileHandler;
import com.adnre.spacegame.SpaceGame;
import com.adnre.spacegame.entity.EntityCursor;
import com.adnre.spacegame.world.Chunk;

import java.io.IOException;

public class ButtonPauseLoad extends Button {
    public ButtonPauseLoad(float x, float y, String header, String textBody, EnumGui eg) {
        super(x, y, 7, 1, header, textBody, eg, false);
    }

    @Override
    public void onClick() {
        super.onClick();
        String filename;
        try {
            SpaceGame.map = null;
            SpaceGame.map = FileHandler.readMapFromFile("world\\map.txt");

            int width = SpaceGame.map.getWidth();
            int height = SpaceGame.map.getHeight();
            Chunk[][] chunks = new Chunk[width][height];

            for (int i = 0; i < width; i++){
                for (int j = 0; j < height; j++){

                    filename = "world\\chunk_" + i + "_" + j + ".txt";
                    try {
                        Chunk chunk = FileHandler.readChunkFromFile(filename);
                        chunks[i][j] = chunk;

                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
            SpaceGame.map.setChunks(chunks);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
