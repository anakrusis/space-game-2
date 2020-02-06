package com.adnre.spacegame.gui.button;

import com.adnre.spacegame.GuiHandler;
import com.adnre.spacegame.gui.EnumGui;

public class ButtonTogglePoliticalMap extends Button {

    public ButtonTogglePoliticalMap(float x, float y, float width, float height, String header, String textBody, EnumGui eg, boolean visible) {
        super(x, y, width, height, header, textBody, eg, visible);
    }

    @Override
    public void onClick() {
        super.onClick();
        GuiHandler.politicalMapMode = !GuiHandler.politicalMapMode;
    }

    @Override
    public void update() {
        super.update();
        if (GuiHandler.politicalMapMode){
            setHeader("Polit. Overlay");
        }else{
            setHeader("No Overlay");
        }
    }
}
