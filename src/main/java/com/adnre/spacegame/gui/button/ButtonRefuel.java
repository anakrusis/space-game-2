package com.adnre.spacegame.gui.button;

import com.adnre.spacegame.SpaceGame;
import com.adnre.spacegame.entity.EntityPlayer;
import com.adnre.spacegame.gui.EnumGui;

public class ButtonRefuel extends Button {

    int refuelcost;

    public ButtonRefuel(float x, float y, float width, float height, String header, String textBody, EnumGui eg, boolean visible) {
        super(x, y, width, height, header, textBody, eg, visible);
    }

    @Override
    public void onClick() {
        super.onClick();
        if (SpaceGame.world.getPlayer() != null){
            EntityPlayer player = SpaceGame.world.getPlayer();
            player.setFuel( player.getFuelCapacity() );
            player.addMoney( -refuelcost );
        }
    }

    @Override
    public void update() {
        super.update();
        if (SpaceGame.world.getPlayer() != null){
            EntityPlayer p = SpaceGame.world.getPlayer();
            refuelcost = (int) ((p.getFuelCapacity() - p.getFuel()));
            setHeader("Refuel ($" + refuelcost + ")");
        } else {
            setHeader("Refuel");
        }
    }
}
