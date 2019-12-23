package com.adnre.spacegame.gui;

import com.adnre.spacegame.FileHandler;
import com.adnre.spacegame.SpaceGame;

import java.io.IOException;

public class ButtonPauseLoad extends Button {
    public ButtonPauseLoad(float x, float y, String header, String textBody, EnumGui eg) {
        super(x, y, 7, 1, header, textBody, eg, false);
    }

    @Override
    public void onClick() {
        super.onClick();
        try {
            SpaceGame.map.getChunks()[0][0] = FileHandler.readChunkFromFile("world\\chunk_0_0.txt");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
