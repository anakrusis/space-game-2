package com.adnre.spacegame.util;

public class MathHelper {
    public static double rotX(double angle, double x, double y){
        return (x * Math.cos(angle)) - (y * Math.sin(angle));
    }

    public static double rotY(double angle, double x, double y){
        return (x * Math.sin(angle)) + (y * Math.cos(angle));
    }

    public static double distance (double x1, double y1, double x2, double y2){
        return Math.sqrt( Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2) );
    }

    public static int chebyshev (int x1, int y1, int x2, int y2){
        return Math.max( Math.abs( x2 - x1 ), Math.abs( y2 - y1 ) );
    }

    public static int terrainIndexDistance(int index1, int index2, int terrainSize){
        int rightDiff = index2 - (index1 - terrainSize);
        int leftDiff =  index1 - index2;
        return Math.min( Math.abs(leftDiff), Math.abs(rightDiff));
    }

    public static boolean isIndexLeftOfIndex(int index1, int index2, int terrainSize){
        int diff = index1 - index2;
        if (diff > terrainSize / 2){
            diff = (index1 - terrainSize) - index2;
        }
        return diff < 0;
    }

    public static double screenToWorldX(double screenX, double screenWidth, double camX, double camZoom){
        screenX = screenToGLX( screenX, screenWidth);
        return ( screenX / camZoom ) + camX;
    }
    public static double screenToWorldY(double screenY, double screenHeight, double camY, double camZoom){
        screenY = screenToGLY( screenY, screenHeight);
        return ( screenY / camZoom ) + camY;
    }

    public static double screenToGLX(double screenX, double screenWidth) {
        screenX /= screenWidth;
        screenX *= 35.55;
        screenX -= 17.77;
        return screenX;
    }

    public static double screenToGLY(double screenY, double screenHeight) {
        screenY /= screenHeight;
        screenY *= 20;
        screenY -= 10;

        // Because Y increases towards the top in the gl viewport
        screenY *= -1;
        return screenY;
    }

    public static int loopyMod(int x, int m) {
        return (x % m + m) % m;
    }
}
