package com.adnre.spacegame.gui;

import com.adnre.spacegame.FileHandler;
import com.adnre.spacegame.SpaceGame;

public class ButtonPauseSave extends Button {
    public ButtonPauseSave(float x, float y, String header, String textBody, EnumGui eg) {
        super(x, y, 7, 1, header, textBody, eg, false);
    }

    @Override
    public void onClick() {
        super.onClick();
        String filename;

        for (int i = 0; i < SpaceGame.map.getWidth(); i++){
            for (int j = 0; j < SpaceGame.map.getHeight(); j++){
                filename = "world\\chunk_" + i + "_" + j + ".txt";
                FileHandler.writeObjectToFile(SpaceGame.map.getChunks()[i][j], filename);

                //if (SpaceGame.map.getPlayer() != null){
                    FileHandler.writeObjectToFile(SpaceGame.map, "world\\map.txt");
                //}
            }
        }
    }
}
