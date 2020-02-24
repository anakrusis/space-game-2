package com.adnre.spacegame.gui;

import com.adnre.spacegame.SpaceGame;
import com.adnre.spacegame.entity.body.BodyStar;

public class TextBoxHomeStar extends TextBox {
    public TextBoxHomeStar(float x, float y, float width, float height) {
        super(x, y, width, height, EnumGui.GUI_TOOLTIP_HOMESTAR, true);
    }

    @Override
    public void update() {
        super.update();
        BodyStar star = SpaceGame.world.getHomeStar();
        double homestarScreenX = (star.getX() - SpaceGame.camera.getX()) * SpaceGame.camera.getZoom();
        double homestarScreenY = (star.getY() - SpaceGame.camera.getY()) * SpaceGame.camera.getZoom();
        this.setHeader(star.getName());
    }
}
