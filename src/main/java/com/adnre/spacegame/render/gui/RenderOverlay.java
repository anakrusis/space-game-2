package com.adnre.spacegame.render.gui;

import com.adnre.spacegame.GuiHandler;
import com.adnre.spacegame.SpaceGame;
import com.adnre.spacegame.entity.body.Body;
import com.adnre.spacegame.entity.body.BodyPlanet;
import com.adnre.spacegame.render.Camera;
import com.adnre.spacegame.util.CollisionUtil;
import com.adnre.spacegame.util.Reference;
import com.adnre.spacegame.world.City;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glColor4d;

public class RenderOverlay {
    public static void renderOverlay(){
        glEnable(GL_BLEND);
        glColor4d(0.5d,0.5d,0.5d, 0.5);
        glBlendFunc(GL_SRC_COLOR, GL_ONE_MINUS_SRC_COLOR);

        glBegin(GL_QUADS);

        glVertex2d(-17.77,-10);
        glVertex2d(-17.77,10);
        glVertex2d(17.77, 10);
        glVertex2d(17.77, -10);

        glEnd();
        glDisable(GL_BLEND);
    }
    public static void renderCitiesOverlay(BodyPlanet planet){
        Camera camera = SpaceGame.camera;

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA);
        // Political map of cities
        if (GuiHandler.politicalMapMode){
            for (City city: planet.getCities().values()){

                double[] n;
                if (city.getNation() == null){
                    n = new double[]{ 1, 0, 0 };
                }else{
                    float[] c = city.getNation().getColor();
                    n = new double[]{ c[0], c[1], c[2] };
                }

                for (Integer index : city.getTerrainIndexes()){
                    double[] tri = CollisionUtil.getTriFromIndex(planet, index);
                    glBegin(GL_TRIANGLES);

                    glColor4d(n[0], n[1], n[2], 0);
                    glVertex2d( camera.getZoom() * (tri[0] - camera.getX()),  camera.getZoom() * (tri[1] - camera.getY()));

                    glColor4d(n[0], n[1], n[2], 1);
                    glVertex2d( camera.getZoom() * (tri[2] - camera.getX()),  camera.getZoom() * (tri[3] - camera.getY()));
                    glVertex2d( camera.getZoom() * (tri[4] - camera.getX()),  camera.getZoom() * (tri[5] - camera.getY()));
                    glEnd();
                }
            }
        }
        glDisable(GL_BLEND);
    }

    public static void renderCollisionDebugOverlay(Body body){
        Camera camera = SpaceGame.camera;

        // Debug lines for collision
        if (SpaceGame.world.getPlayer() != null && Reference.COLLISION_DEBUG_LINES){
            int index = CollisionUtil.terrainIndexFromEntityAngle(SpaceGame.world.getPlayer(), body);
            double[] tri = CollisionUtil.getTriFromIndex(body, index);
            glColor3d(1.0,0.0,0.0);
            glBegin(GL_LINE_LOOP);
            glVertex2d( camera.getZoom() * (tri[0] - camera.getX()),  camera.getZoom() * (tri[1] - camera.getY()));
            glVertex2d( camera.getZoom() * (tri[2] - camera.getX()),  camera.getZoom() * (tri[3] - camera.getY()));
            glVertex2d( camera.getZoom() * (tri[4] - camera.getX()),  camera.getZoom() * (tri[5] - camera.getY()));
            glEnd();

        }
    }
}
