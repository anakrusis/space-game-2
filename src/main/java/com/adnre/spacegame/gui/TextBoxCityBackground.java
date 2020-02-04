package com.adnre.spacegame.gui;

import com.adnre.spacegame.GuiHandler;

public class TextBoxCityBackground extends TextBox {
    public TextBoxCityBackground(float x, float y, float width, float height, EnumGui GUI_ID) {
        super(x, y, width, height, GUI_ID);
    }

    @Override
    public void update() {
        super.update();
        if (GuiHandler.citySelected != null){
            setHeader("City of " + GuiHandler.citySelected.getName());
            setTextBody("");
            addTextBody("\nPopulation: " + GuiHandler.citySelected.getPopulation());
        }
    }
}
