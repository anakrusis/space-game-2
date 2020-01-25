package com.adnre.spacegame.gui.button;

import com.adnre.spacegame.gui.EnumGui;
import com.adnre.spacegame.gui.Window;
import com.adnre.spacegame.gui.button.Button;

public class ButtonCloseWindow extends Button {

    // The window which this button closes.
    Window window;

    public ButtonCloseWindow(float x, float y, Window window) {
        super(x, y, 0.75f, 0.75f, "X", "", EnumGui.GUI_BUTTON_CLOSE_WINDOW, false);
        this.window = window;
    }

    @Override
    public void onClick() {
        super.onClick();
        this.window.setVisible(false);
    }

    public Window getWindow() {
        return window;
    }
}
