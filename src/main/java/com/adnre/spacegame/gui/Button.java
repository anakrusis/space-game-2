package com.adnre.spacegame.gui;

public class Button extends TextBox {
    public Button(float x, float y, float width, float height, String header, String textBody, EnumGui eg, boolean visible) {

        // All buttons are clickable (otherwise they wouldn't be buttons)
        super(x, y, width, height, header, textBody, eg, visible, true);
    }
}
