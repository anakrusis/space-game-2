package com.adnre.spacegame.render;

import com.adnre.spacegame.entity.Body;
import com.adnre.spacegame.entity.body.*;
import com.adnre.spacegame.render.entity.RenderPlanet;
import com.adnre.spacegame.render.entity.RenderStar;
import com.adnre.spacegame.util.Reference;
import com.adnre.spacegame.world.Chunk;

import static org.lwjgl.opengl.GL11.*;

public class RenderChunk {
    public static void renderChunk(Chunk chunk, Camera camera){
        double camX = camera.getX();
        double camY = camera.getY();
        double camZoom = camera.getZoom();

        int cbs = Reference.CHUNK_SIZE;

        // Drawing the main chunk grid
        glBegin(GL_LINE_LOOP);
        glColor3d(0.5d,0.5d,0.5d);
        glVertex2d(camZoom * (cbs * (0d + chunk.getX()) - camX), camZoom * (cbs * (0d + chunk.getY()) - camY));
        glVertex2d(camZoom * (cbs * (0d + chunk.getX()) - camX), camZoom * (cbs * (1d + chunk.getY()) - camY));
        glVertex2d(camZoom * (cbs * (1d + chunk.getX()) - camX), camZoom * (cbs * (1d + chunk.getY()) - camY));
        glVertex2d(camZoom * (cbs * (1d + chunk.getX()) - camX), camZoom * (cbs * (0d + chunk.getY()) - camY));
        glEnd();

        // Drawing all the bodies inside the chunk
        for (Body body : chunk.getBodies()){

            // Culled bodies
            if (camZoom > Reference.MAP_SCREEN_THRESHOLD){

                if (body instanceof BodyGravityRadius){
                    RenderStar.renderStar(body, false, camera);
                }else if (body instanceof BodyPlanet){

                    // Yeah maybe don't render the same orbital body 100 times
                    BodyPlanet planet = (BodyPlanet)body;
                    BodyOrbit orbit = new BodyOrbit(planet.getStar().getX(), planet.getStar().getY(), 0, chunk, planet.getOrbitDistance(), chunk.getMap());
                    RenderStar.renderStar(orbit, false, camera);

                    RenderPlanet.renderPlanet(body,camera);
                }
            }

            // Non-culled bodies (just the stars for now)
            if (body instanceof BodyStar){
                RenderStar.renderStar(body, true, camera);
            }
        }
    }
}
