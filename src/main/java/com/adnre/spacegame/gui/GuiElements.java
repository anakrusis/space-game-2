package com.adnre.spacegame.gui;

import com.adnre.spacegame.item.ItemStack;
import com.adnre.spacegame.item.Items;

public class GuiElements {
    public static void initGui(){

        // Main screen
        TextBox tx = new TextBox(8.77f,-3,9,7, EnumGui.GUI_SELECTED_ENTITY);
        for (int i = 0; i < 9; i++){
            TextBoxHotbarItem tbhi = new TextBoxHotbarItem(-6.5f + (i * 1.5f), -7.5f, 1.5f, 1.5f, i);
        }
        Button openStoreButton = new Button(-13, -8, 5, 1, "Store (I)", "", EnumGui.GUI_BUTTON_STORE_OPEN, true);

        // store screen
        TextBox storeBG = new TextBox( -6, 5, 12, 8, "Store", "", EnumGui.GUI_STORE_BACKGROUND, false);
        ButtonStoreBuy buyApt = new ButtonStoreBuy(-5, 4, new ItemStack(Items.ITEM_APARTMENT, 1));
        ButtonStoreBuy buyFac = new ButtonStoreBuy(-5, 2.5f, new ItemStack(Items.ITEM_FACTORY, 1));
        ButtonStoreBuy buyBom = new ButtonStoreBuy(-5, 1f, new ItemStack(Items.ITEM_BOMB, 1));
        Button closeStoreButton = new Button(5, 4.75f, 0.75f, 0.75f, "X", "", EnumGui.GUI_BUTTON_STORE_CLOSE, false);

        // pause screen
        Button resumeButton = new Button(-4, 2, 7, 1, "Resume Game", "", EnumGui.GUI_BUTTON_PAUSE_RESUME, false);
        Button savebutton = new ButtonPauseSave(-4, 0, "Save Game", "", EnumGui.GUI_BUTTON_PAUSE_SAVE);
        Button loadbutton = new ButtonPauseLoad(-4, -2, "Load Game", "", EnumGui.GUI_BUTTON_PAUSE_LOAD);
    }
}
