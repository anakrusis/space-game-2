package com.adnre.spacegame.render;

import com.adnre.spacegame.SpaceGame;
import com.adnre.spacegame.entity.body.Body;
import com.adnre.spacegame.entity.body.*;
import com.adnre.spacegame.render.entity.RenderAtmosphere;
import com.adnre.spacegame.render.entity.RenderPlanet;
import com.adnre.spacegame.render.entity.RenderStar;
import com.adnre.spacegame.util.CollisionUtil;
import com.adnre.spacegame.util.Reference;
import com.adnre.spacegame.world.Chunk;

import java.util.UUID;

import static org.lwjgl.opengl.GL11.*;

public class RenderChunk {
    public static void renderChunk(Chunk chunk, Camera camera){
        double camX = camera.getX();
        double camY = camera.getY();
        double camZoom = camera.getZoom();

        int cbs = Reference.CHUNK_SIZE;

        // Drawing the main chunk grid
        glBegin(GL_LINE_LOOP);
        glColor3d(0.55d,0.5d,0.55d);
        glVertex2d(camZoom * (cbs * (0d + chunk.getX()) - camX), camZoom * (cbs * (0d + chunk.getY()) - camY));
        glVertex2d(camZoom * (cbs * (0d + chunk.getX()) - camX), camZoom * (cbs * (1d + chunk.getY()) - camY));
        glVertex2d(camZoom * (cbs * (1d + chunk.getX()) - camX), camZoom * (cbs * (1d + chunk.getY()) - camY));
        glVertex2d(camZoom * (cbs * (1d + chunk.getX()) - camX), camZoom * (cbs * (0d + chunk.getY()) - camY));
        glEnd();

        // Drawing all the bodies inside the chunk
        for (java.util.Map.Entry<UUID, Body> e : chunk.getBodies().entrySet()) {
            Body body = e.getValue();

            // Culled bodies
            if (camZoom > Reference.MAP_SCREEN_THRESHOLD){

                if (body instanceof BodyPlanet) {

                    // Yeah maybe don't render the same orbital body 100 times
                    BodyPlanet planet = (BodyPlanet) body;
                    BodyOrbit orbit = new BodyOrbit(planet.getStar().getX(), planet.getStar().getY(), 0, chunk, planet.getOrbitDistance(), chunk.getWorld());
                    RenderStar.renderStar(orbit, false, camera);

                    RenderPlanet.renderPlanet(body, camera);

                }else if (body instanceof BodyAtmosphere){
                    RenderAtmosphere.renderAtmosphere(body, camera);
                } else {
                    RenderStar.renderStar(body, false, camera);
                }
            }

            // Non-culled bodies (just the stars for now)
            if (body instanceof BodyStar){
                RenderStar.renderStar(body, true, camera);
            }

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
}
