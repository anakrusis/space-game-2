package com.adnre.spacegame.gui.button;

import com.adnre.spacegame.FileHandler;
import com.adnre.spacegame.SpaceGame;
import com.adnre.spacegame.gui.EnumGui;
import com.adnre.spacegame.gui.button.Button;
import com.adnre.spacegame.world.*;

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
            SpaceGame.world = FileHandler.readWorldFromFile("world\\map");

            int width = SpaceGame.world.getWidth();
            int height = SpaceGame.world.getHeight();
            Chunk[][] chunks = new Chunk[width][height];

            for (int i = 0; i < width; i++){
                for (int j = 0; j < height; j++){

                    filename = "world\\chunk_" + i + "_" + j;
                    try {
                        Chunk chunk = FileHandler.readChunkFromFile(filename);
                        chunks[i][j] = chunk;

                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
            SpaceGame.world.setChunks(chunks);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
