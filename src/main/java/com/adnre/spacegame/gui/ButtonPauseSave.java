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
        FileHandler.writeChunkToFile(SpaceGame.map.getChunks()[0][0], "world\\chunk_0_0.txt");
    }
}
