package com.adnre.spacegame.gui;

import com.adnre.spacegame.FileHandler;
import com.adnre.spacegame.SpaceGame;
import com.adnre.spacegame.util.Reference;
import com.adnre.spacegame.world.ChunkChange;
import com.adnre.spacegame.world.ChunkChangelog;

import java.util.ArrayList;

public class ButtonPauseSave extends Button {
    public ButtonPauseSave(float x, float y, String header, String textBody, EnumGui eg) {
        super(x, y, 7, 1, header, textBody, eg, false);
    }

    @Override
    public void onClick() {
        super.onClick();
        String filename;
        FileHandler.writeObjectToFile(Reference.seed, "world\\seed.txt");
        FileHandler.writeObjectToFile(SpaceGame.map.mapTime, "world\\map.txt");

        for (int i = 0; i < SpaceGame.map.getWidth(); i++){
            for (int j = 0; j < SpaceGame.map.getHeight(); j++){
                filename = "world\\chunk_" + i + "_" + j + ".txt";
                if (SpaceGame.map.getChunks()[i][j].getChunkChangelog().getChanges().size() > 0){
                    ChunkChangelog changes = SpaceGame.map.getChunks()[i][j].getChunkChangelog();
                    FileHandler.writeObjectToFile(changes, filename);
                }
            }
        }
    }
}
