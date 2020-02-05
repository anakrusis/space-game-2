package com.adnre.spacegame.gui;

import com.adnre.spacegame.GuiHandler;
import com.adnre.spacegame.world.City;

public class TextBoxCityBackground extends TextBox {
    public TextBoxCityBackground(float x, float y, float width, float height, EnumGui GUI_ID) {
        super(x, y, width, height, GUI_ID);
    }

    @Override
    public void update() {
        super.update();
        City c = GuiHandler.citySelected;
        if (c != null){
            setHeader("City of " + c.getName());
            setTextBody("");
            addTextBody("\nPopulation: " + c.getPopulation());
            addTextBody("\nCenter: " + c.getCenterIndex());
            addTextBody("\nLeftmost: " + c.getExtremeIndexes()[0]);
            addTextBody("\nRightmost: " + c.getExtremeIndexes()[1]);
        }
    }
}
