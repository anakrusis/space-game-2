package com.adnre.spacegame.gui;

import com.adnre.spacegame.SpaceGame;
import com.adnre.spacegame.entity.Entity;
import com.adnre.spacegame.entity.body.BodyPlanet;
import com.adnre.spacegame.entity.body.BodyStar;
import com.adnre.spacegame.entity.building.*;
import com.adnre.spacegame.util.MathHelper;

import java.text.DecimalFormat;

public class TextBoxSelectedEntity extends TextBox {

    public TextBoxSelectedEntity(float x, float y, float width, float height, EnumGui GUI_ID) {
        super(x, y, width, height, GUI_ID);
    }

    @Override
    public void update() {
        super.update();
        setHeader("");
        setTextBody("");

        Entity e = SpaceGame.world.getCursor().getSelectedEntity();
        if (e != null){
            setHeader(e.getName() + ":");

            // All building properties
            if (e instanceof EntityBuilding){
                if (((EntityBuilding) e).isActive()){
                    addTextBody("\nActive");
                }else{
                    addTextBody("\nInactive");
                }

                if (e.isGrounded()){
                    //addTextBody("\n" + ((EntityBuilding) e).getPlanetIndex());
                    if (MathHelper.isIndexLeftOfIndex(((EntityBuilding) e).getPlanetIndex(), 0, e.getGroundedBody().getTerrainSize())){
                        //addTextBody("\nleft of 0");
                    }
                    //addTextBody("\nDist: " + MathHelper.terrainIndexDistance(((EntityBuilding) e).getPlanetIndex(), 40, e.getGroundedBody().getTerrainSize()));
                }
            }

            // Unique single-class properties
            if (e instanceof BodyPlanet) {
                if (((BodyPlanet) e).getNation() != null) {

                    if (e == SpaceGame.world.getHomePlanet()) {
                        addTextBody("\nHome planet of the \n" + ((BodyPlanet) e).getNation().getName());
                    } else {
                        addTextBody("\nClaimed by the \n" + ((BodyPlanet) e).getNation().getName());
                    }

                } else {
                    addTextBody("\nUnclaimed");
                }
                addTextBody("\n");

                addTextBody("\nPopulation: " + ((BodyPlanet) e).getPopulation());
                addTextBody("\nSize: " + ((BodyPlanet) e).getTerrainSize());

                DecimalFormat df = new DecimalFormat("###.##");
                String fnum = df.format(((BodyPlanet) e).getAtmosphericDensity());
                addTextBody("\nAtmo. Density: " + fnum);

            }else if (e instanceof BodyStar) {
                if (((BodyStar) e).getNation() != null) {

                    if (e == SpaceGame.world.getHomeStar()) {
                        addTextBody("\nHome system of the \n" + ((BodyStar) e).getNation().getName());
                    } else {
                        addTextBody("\nClaimed by the \n" + ((BodyStar) e).getNation().getName());
                    }

                } else {
                    addTextBody("\nUnclaimed");
                }
                addTextBody("\n");
                addTextBody("\nSystem Pop.: " + ((BodyStar) e).getSystemPopulation());

            }else if (e instanceof BuildingApartment) {

                addTextBody("\nPopulation: " + ((BuildingApartment) e).getPopulation());
                addTextBody("/" + ((BuildingApartment) e).getCapacity());

            }else if (e instanceof BuildingFactory) {

                addTextBody("\nEmployees: " + ((BuildingFactory) e).getEmployees());
                addTextBody("/" + ((BuildingFactory) e).getCapacity());
                addTextBody("\nOutput: $" + ((BuildingFactory) e).getOutput());

            }else if (e instanceof BuildingSpaceport){
                addTextBody("\n\nClick without any \nitem selected to \nview the Spaceport \nmenu.");

            }else if (e instanceof BuildingCityHall){
                addTextBody("\n\nClick without any \nitem selected to \nview the City menu.");
            }
        }
    }
}
