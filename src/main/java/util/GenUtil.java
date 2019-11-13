package util;

public class GenUtil {
    // Returns false if too close to a chunk boundary
    public static boolean withinPadding(double x, double y, double padding){
        double size = Reference.CHUNK_SIZE;
        return ((x % size > padding) && (x % size < size-padding) &&
                (y % size > padding) && (y % size < size-padding));
    }
}
