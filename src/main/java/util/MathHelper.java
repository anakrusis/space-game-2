package util;

public class MathHelper {
    public static double rotX(float angle, double x, double y){
        return (x * Math.cos(angle)) - (y * Math.sin(angle));
    }

    public static double rotY(float angle, double x, double y){
        return (x * Math.sin(angle)) + (y * Math.cos(angle));
    }
}
