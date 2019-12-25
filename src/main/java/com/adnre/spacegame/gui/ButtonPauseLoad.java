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

public class ButtonPauseLoad extends Button {
    public ButtonPauseLoad(float x, float y, String header, String textBody, EnumGui eg) {
        super(x, y, 7, 1, header, textBody, eg, false);
    }

    @Override
    public void onClick() {
        super.onClick();
        String filename;
        try {
            Long seed = (Long)FileHandler.readObject("world\\seed.txt");
            RandomUtil.setSeed(seed);
            int maptime = (Integer)FileHandler.readObject("world\\map.txt");
            SpaceGame.map = new Map(10, 10, maptime);

            int width = SpaceGame.map.getWidth();
            int height = SpaceGame.map.getHeight();


            for (int i = 0; i < width; i++){
                for (int j = 0; j < height; j++){

                    Chunk chunk = SpaceGame.map.getChunks()[i][j];
                    for (Body body : chunk.getBodies()) {
                        if (body instanceof BodyPlanet){
                            BodyPlanet planet = (BodyPlanet)body;
                            float angle = planet.getOrbitStart() + (SpaceGame.map.mapTime * (float)(Math.PI / 2));
                            double futurePlanetX = MathHelper.rotX(angle, planet.getOrbitDistance(), 0) + planet.getStar().getX();
                            double futurePlanetY = MathHelper.rotY(angle, planet.getOrbitDistance(), 0) + planet.getStar().getY();

                            planet.setX(futurePlanetX);
                            planet.setY(futurePlanetY);
                        }
                    }

                    filename = "world\\chunk_" + i + "_" + j + ".txt";
                    try {
                        Object obj = FileHandler.readObject(filename);

                        ChunkChangelog changelog = (ChunkChangelog)obj;
                        for (ChunkChange change : changelog.getChanges()){
                            if (change instanceof ChunkChangeBuildingPlace){
                                ChunkChangeBuildingPlace ccbp = (ChunkChangeBuildingPlace)change;
                                EntityBuilding build = ccbp.getBuilding();
                                
                                EntityBuilding newBuild = null;
                                if (build instanceof BuildingFactory){
                                    newBuild = new BuildingFactory(build.getX() ,build.getY(), build.getDir(), SpaceGame.map);
                                }else if (build instanceof BuildingApartment){
                                    newBuild = new BuildingApartment(build.getX() ,build.getY(), build.getDir(), SpaceGame.map, ((BuildingApartment) build).getPopulation());
                                }
                                if (newBuild != null){
                                    SpaceGame.map.getEntities().add(newBuild);
                                }
                            }
                        }


                    } catch (IOException | ClassNotFoundException e) {

                    }

                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
