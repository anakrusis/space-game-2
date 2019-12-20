package com.adnre.spacegame.render;

import com.adnre.spacegame.util.Reference;
import com.adnre.spacegame.world.Chunk;
import com.adnre.spacegame.world.Map;

public class Camera {
    private double x;
    private double y;
    private double zoom;
    private Map map;

    public Camera (double x, double y, double zoom, Map map){
        this.x = x;
        this.y = y;
        this.zoom = zoom;
        this.map = map;
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
        if (chunkx >= 0 && chunky >= 0 && chunkx < map.getChunks().length && chunky < map.getChunks()[0].length){
            Chunk entityChunk = this.map.getChunks()[chunkx][chunky];
            return entityChunk;
        }else{
            return null;
        }
    }
}
