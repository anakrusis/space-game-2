package com.adnre.spacegame.gui;

import com.adnre.spacegame.SpaceGame;

import java.util.ArrayList;

public class Window {

    // A window is like a container of gui elements. it collectively decides when they all show on screen at once

    ArrayList<TextBox> guiElements;
    boolean isVisible;

    // the closer is the button which makes this window close, i guess
    TextBox closer;

    public Window (boolean visible){
        guiElements = new ArrayList<>();
        isVisible = visible;
        SpaceGame.windows.add(this);
    }
    public void add (TextBox t){
        guiElements.add(t);
    }

    public void setCloser(TextBox closer) {
        this.closer = closer;
    }

    public TextBox getCloser() {
        return closer;
    }

    public ArrayList<TextBox> getGuiElements() {
        return guiElements;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
