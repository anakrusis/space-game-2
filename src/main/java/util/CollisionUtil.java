package util;

import entity.Body;
import entity.Entity;

public class CollisionUtil {
    public static boolean isEntityCollidingWithBody (Entity entity, Body body){

        boolean isColliding;

        // Temporarily the entity is just a single point for now,
        // will get its own polygon collision later today
        double[] abspoints = body.getAbsolutePoints();
        double[] entityAbsPoints = entity.getAbsolutePoints();

        double bodyX, bodyY;
        double bodyXNext, bodyYNext;
        double entityX, entityY;

        boolean between;
        boolean intersecting;

        for (int j = 0; j < entityAbsPoints.length; j += 2){

            isColliding = false;

            entityX = entityAbsPoints[j];
            entityY = entityAbsPoints[j + 1];

            for (int i = 0; i < abspoints.length; i += 2){
                bodyX = abspoints[i];
                bodyY = abspoints[i+1];

                if ((i + 3) > abspoints.length){
                    bodyXNext = abspoints[0];
                    bodyYNext = abspoints[1];
                }else {
                    bodyXNext = abspoints[i + 2];
                    bodyYNext = abspoints[i + 3];
                }
                between = (bodyY > entityY) != (bodyYNext > entityY);
                intersecting = entityX < (bodyXNext - bodyX) * (entityY - bodyY) / (bodyYNext - bodyY) + bodyX;

                if (between && intersecting){
                    isColliding = !isColliding;
                }
            }

            // If any of the points on Entity are colliding, then yes it is colliding.
            if (isColliding){
                return isColliding;
            }
        }

        return false;
    }
}
