package com.adnre.spacegame;

import com.adnre.spacegame.entity.Entity;
import com.adnre.spacegame.entity.building.BuildingSpaceport;
import com.adnre.spacegame.entity.building.EntityBuilding;
import com.adnre.spacegame.entity.EntityCursor;
import com.adnre.spacegame.entity.body.BodyPlanet;
import com.adnre.spacegame.entity.body.BodyStar;
import com.adnre.spacegame.entity.building.BuildingApartment;
import com.adnre.spacegame.entity.building.BuildingFactory;
import com.adnre.spacegame.gui.EnumGui;
import com.adnre.spacegame.gui.TextBox;
import com.adnre.spacegame.gui.TextBoxHotbarItem;
import com.adnre.spacegame.gui.Window;
import com.adnre.spacegame.item.ItemStack;
import com.adnre.spacegame.world.City;

import java.util.ArrayList;

public class GuiHandler {

    public static City citySelected;
    public static boolean politicalMapMode = true;

    public static void update( ArrayList<TextBox> elements){
        EntityCursor cursor = SpaceGame.world.getCursor();

        for (int i = 0; i < elements.size(); i ++){
            TextBox tx = elements.get(i);
            tx.update();

            if (tx.getGuiID() == EnumGui.GUI_BUTTON_PAUSE_RESUME || tx.getGuiID() == EnumGui.GUI_BUTTON_PAUSE_LOAD
                    || tx.getGuiID() == EnumGui.GUI_BUTTON_PAUSE_SAVE){
                tx.setVisible( SpaceGame.isPaused() );
            }
        }

        // Updating individual gui elements inside of a window to comply with the main window visibility
        for (Window window : SpaceGame.windows){
            for (TextBox t : window.getGuiElements()){
                t.setVisible(window.isVisible());
            }
        }
    }
}
