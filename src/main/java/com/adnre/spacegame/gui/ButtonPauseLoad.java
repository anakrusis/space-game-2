package com.adnre.spacegame.gui;

import com.adnre.spacegame.FileHandler;
import com.adnre.spacegame.SpaceGame;
import com.adnre.spacegame.entity.Body;
import com.adnre.spacegame.entity.EntityBuilding;
import com.adnre.spacegame.entity.EntityCursor;
import com.adnre.spacegame.entity.body.BodyPlanet;
import com.adnre.spacegame.entity.building.BuildingApartment;
import com.adnre.spacegame.entity.building.BuildingFactory;
import com.adnre.spacegame.util.MathHelper;
import com.adnre.spacegame.util.RandomUtil;
import com.adnre.spacegame.util.WorldFixer;
import com.adnre.spacegame.world.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ButtonPauseLoad extends Button {
    public ButtonPauseLoad(float x, float y, String header, String textBody, EnumGui eg) {
        super(x, y, 7, 1, header, textBody, eg, false);
    }

    @Override
    public void onClick() {
        super.onClick();

    }
}
