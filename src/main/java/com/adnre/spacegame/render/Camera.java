package com.adnre.spacegame.render;

import com.adnre.spacegame.util.Reference;
import com.adnre.spacegame.world.Chunk;
import com.adnre.spacegame.world.World;

public class Camera {
    private double x;
    private double y;
    private double zoom;
    private World world;

    public Camera (double x, double y, double zoom, World world){
        this.x = x;
        this.y = y;
        this.zoom = zoom;
        this.world = world;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    public Chunk getChunk(){
        int chunkx = (int)Math.floor( this.x / Reference.CHUNK_SIZE );
        int chunky = (int)Math.floor( this.y / Reference.CHUNK_SIZE );
        if (chunkx >= 0 && chunky >= 0 && chunkx < world.getChunks().length && chunky < world.getChunks()[0].length){
            Chunk entityChunk = this.world.getChunks()[chunkx][chunky];
            return entityChunk;
        }else{
            return null;
        }
    }
}
