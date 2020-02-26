package com.adnre.spacegame.gui;

import com.adnre.spacegame.SpaceGame;
import com.adnre.spacegame.entity.body.BodyStar;
import com.adnre.spacegame.util.Reference;

public class TextBoxHomeStar extends TextBox {
    public TextBoxHomeStar(float x, float y, float width, float height) {
        super(x, y, width, height, EnumGui.GUI_TOOLTIP_HOMESTAR, true);
    }

    @Override
    public void update() {
        super.update();

        BodyStar star = SpaceGame.world.getHomeStar();
        float homestarScreenX = (float) ((star.getX() - SpaceGame.camera.getX()) * SpaceGame.camera.getZoom());
        float homestarScreenY = (float) ((star.getY() - SpaceGame.camera.getY()) * SpaceGame.camera.getZoom());

        // margins
        float leftEdge = -17;
        float rightEdge = 17;
        float topEdge = 8;
        float bottomEdge = -9;

        // preventing offscreen going
        homestarScreenX = Math.max(leftEdge, homestarScreenX);
        homestarScreenX = Math.min(homestarScreenX, rightEdge);
        homestarScreenY = Math.max(bottomEdge, homestarScreenY);
        homestarScreenY = Math.min(homestarScreenY, topEdge);

        // text generation
        String hdr = (char) 0x7f + " " + star.getName();
        if (homestarScreenX == leftEdge || homestarScreenX == rightEdge){
            if (SpaceGame.camera.getX() > star.getX()){
                hdr = "<-" + hdr;
            }else{
                hdr = hdr + "->";
            }
        };
        this.setHeader(hdr);
        int len = hdr.length();
        this.setWidth( len / 2f );

        // re-evaluating margin after text generation is finished
        homestarScreenX = Math.min(homestarScreenX, rightEdge - this.width);

        setX( homestarScreenX );
        setY(homestarScreenY + 1);
        setVisible(SpaceGame.camera.getZoom() < Reference.MAP_SCREEN_THRESHOLD);
    }
}
