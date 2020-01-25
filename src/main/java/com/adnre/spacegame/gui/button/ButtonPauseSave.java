package com.adnre.spacegame.gui.button;


import com.adnre.spacegame.FileHandler;
import com.adnre.spacegame.SpaceGame;
import com.adnre.spacegame.gui.EnumGui;
import com.adnre.spacegame.gui.button.Button;

public class ButtonPauseSave extends Button {
    public ButtonPauseSave(float x, float y, String header, String textBody, EnumGui eg) {
        super(x, y, 7, 1, header, textBody, eg, false);
    }

    @Override
    public void onClick() {
        super.onClick();
        String filename;

        for (int i = 0; i < SpaceGame.world.getWidth(); i++){
            for (int j = 0; j < SpaceGame.world.getHeight(); j++){
                filename = "world\\chunk_" + i + "_" + j + ".txt";
                FileHandler.writeObjectToFile(SpaceGame.world.getChunks()[i][j], filename);

                //if (SpaceGame.map.getPlayer() != null){
                FileHandler.writeObjectToFile(SpaceGame.world, "world\\map.txt");
                //}
            }
        }
    }
}
