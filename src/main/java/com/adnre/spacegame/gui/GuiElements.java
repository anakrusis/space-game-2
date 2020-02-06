package com.adnre.spacegame.gui;

import com.adnre.spacegame.gui.button.*;
import com.adnre.spacegame.item.ItemStack;
import com.adnre.spacegame.item.Items;

public class GuiElements {

    public static Window WINDOW_SPACEPORT = new Window(false);
    public static Window WINDOW_STORE = new Window(false);
    public static Window WINDOW_CITY = new Window(false);

    public static void initGui(){

        // Main screen
        TextBox tx = new TextBoxSelectedEntity(8.77f,-3,9,7, EnumGui.GUI_SELECTED_ENTITY);
        for (int i = 0; i < 9; i++){
            TextBoxHotbarItem tbhi = new TextBoxHotbarItem(-6.5f + (i * 1.5f), -7.5f, 1.5f, 1.5f, i);
        }
        Button openStoreButton = new Button(-13, -8, 5, 1, "Store (I)", "", EnumGui.GUI_BUTTON_STORE_OPEN, true);
        TextBox itemTooltip = new TextBox(0, 0, 8, 1, EnumGui.GUI_TOOLTIP_ITEM, false, false);
        Button togglePoliticalMap = new ButtonTogglePoliticalMap(10, -1.5f, 7, 1, "", "", null, true);

        // store screen
        TextBox storeBG = new TextBox( -6, 5, 12, 8, "Store", "", EnumGui.GUI_STORE_BACKGROUND, false, true);
        ButtonStoreBuy buyApt = new ButtonStoreBuy(-5, 4, new ItemStack(Items.ITEM_APARTMENT, 1));
        ButtonStoreBuy buyFac = new ButtonStoreBuy(-5, 2.5f, new ItemStack(Items.ITEM_FACTORY, 1));
        ButtonStoreBuy buyBom = new ButtonStoreBuy(-5, 1f, new ItemStack(Items.ITEM_BOMB, 1));
        Button closeStoreButton = new ButtonCloseWindow(5, 4.75f, WINDOW_STORE);
        WINDOW_STORE.add(storeBG, buyApt);
        WINDOW_STORE.add(buyFac, buyBom);
        WINDOW_STORE.add(closeStoreButton);

        // pause screen
        Button resumeButton = new Button(-4, 2, 7, 1, "  Resume Game", "", EnumGui.GUI_BUTTON_PAUSE_RESUME, false);
        Button savebutton = new ButtonPauseSave(-4, 0, "  Save Game", "", EnumGui.GUI_BUTTON_PAUSE_SAVE);
        Button loadbutton = new ButtonPauseLoad(-4, -2, "  Load Game", "", EnumGui.GUI_BUTTON_PAUSE_LOAD);

        // spaceport screen
        TextBox spaceportBG = new TextBox( -6, 5, 12, 8, "Spaceport", "", EnumGui.GUI_STORE_BACKGROUND, false, true);
        Button spaceportRefuel = new ButtonRefuel(-5, 4, 8, 1, "Refuel", "", EnumGui.GUI_BUTTON_SPACEPORT_REFUEL, false);
        ButtonCloseWindow spaceportClose = new ButtonCloseWindow(5, 4.75f, WINDOW_SPACEPORT);
        WINDOW_SPACEPORT.add(spaceportBG);
        WINDOW_SPACEPORT.add(spaceportRefuel);
        WINDOW_SPACEPORT.add(spaceportClose);

        //city screen
        TextBoxCityBackground cityBG = new TextBoxCityBackground(-6, 5, 12, 8, null);
        ButtonCloseWindow cityClose = new ButtonCloseWindow(5, 4.75f, WINDOW_CITY);
        WINDOW_CITY.add(cityBG, cityClose);
    }
}
