package util;

public class MathHelper {
    public static double rotX(float angle, double x, double y){
        return (x * Math.cos(angle)) - (y * Math.sin(angle));
    }

    public static double rotY(float angle, double x, double y){
        return (x * Math.sin(angle)) + (y * Math.cos(angle));
    }

    public static double distance (double x1, double y1, double x2, double y2){
        return Math.sqrt( Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2) );
    }

    public static int chebyshev (int x1, int y1, int x2, int y2){
        return Math.max( Math.abs( x2 - x1 ), Math.abs( y2 - y1 ) );
    }

    public static double screenToWorldX(double screenX, double camX, double camZoom){
        return (screenX / camZoom) + camX;
    }
    public static double screenToWorldY(double screenY, double camY, double camZoom){
        return (screenY / camZoom) + camY;
    }
}
