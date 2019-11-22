package util;

import java.util.Random;

/**
 * Credit to @DeaSTL, this is a util from Everything is Bad
 */
public class RandomUtil {
    public static Random random = new Random();

    /**
     * generates a random number between two integers
     * @param min
     * @param max
     * @return random integer
     */
    public static int fromRangeI(int min,int max){
        if(min <= 0) {
            return random.nextInt(max + (Math.abs(min))) + min;
        }else {
            return random.nextInt(max - (Math.abs(min))) + min;
        }
    }

    /**
     * generates a random number between two floating points
     * @param min
     * @param max
     * @return random float
     */
    public static float fromRangeF(float min, float max){
        if(min <= 0) {
            return nextFloat(max + (Math.abs(min))) + min;
        }else{
            return nextFloat(max - (Math.abs(min))) + min;
        }
    }

    /**
     * Improves the vanilla nextFloat function by adding a bound parameter
     * @param bound
     * @return new random bounded float
     */
    public static float nextFloat(float bound){
        return random.nextFloat()*bound;
    }

    /**
     * returns true if probability has been met
     * @param percent
     * @return boolean
     */
    public static boolean percentChance(int percent){
        int percentMM = percent > 100 ? 100 : percent;

        return random.nextInt(101) <= percentMM;
    }

    /**
     * provides a 1 in (n) chance to return true
     * @param probabbility
     * @return boolean
     */
    public static boolean withinChance(int probabbility){
        if(random.nextInt(probabbility) == 1){
            return true;
        }
        return false;
    }
    public static boolean percentChance(float probabbility){
        return random.nextFloat() * 100f <= Math.max(probabbility, 0) + 1f;
    }
}
