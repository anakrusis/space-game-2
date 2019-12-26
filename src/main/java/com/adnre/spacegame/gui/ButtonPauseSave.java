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
    }
}
