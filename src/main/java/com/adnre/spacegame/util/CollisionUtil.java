package com.adnre.spacegame.util;

import com.adnre.spacegame.entity.EntityShip;
import com.adnre.spacegame.entity.body.Body;
import com.adnre.spacegame.entity.Entity;
import com.adnre.spacegame.entity.part.Part;
import com.sun.javafx.geom.Vec3d;

public class CollisionUtil {

    public static boolean isColliding(Entity entity, Body body){
       double nearestDist = getNearestDistance(entity, body, false);
        double expectedDist = body.getRadius() + heightFromEntityAngle(entity, body);
        return nearestDist < expectedDist;
    }

    public static double getNearestDistance(Entity entity, Body body, boolean partsIncluded){
        double[] entityAbsPoints = entity.getAbsolutePoints();
        double nearestDist = 100000000;
        double cd;

        double nearestEntityX, nearestEntityY;
        double cx, cy;

        if (entity instanceof EntityShip && partsIncluded){
            for (Part part : ((EntityShip) entity).getParts()){
                double partnearestdist = getNearestDistance(part, body, false);
                if (partnearestdist < nearestDist){
                    nearestDist = partnearestdist;
                }
            }
        }else{
            // Step 1: get the point on the entity nearest to the center of the body
            for (int i = 0; i < entityAbsPoints.length; i += 2){
                cx = entityAbsPoints[i];
                cy = entityAbsPoints[i + 1];
                cd = MathHelper.distance(cx, cy, body.getX(), body.getY());
                if (cd < nearestDist){
                    nearestDist = cd;
                    nearestEntityX = cx;
                    nearestEntityY = cy;
                }
            }
        }

        return nearestDist;
    }

    public static void resolveCollision(Entity entity, Body body){
        double newx = entity.getX(), newy = entity.getY();
        // Used if the entity is too far in (ie beneath the surface), so it gets teleported to the surface
        double angleFromCenter = Math.atan2(entity.getY() - body.getY(), entity.getX() - body.getX());
        double nearestDist = MathHelper.distance(entity.getX(), entity.getY(), body.getX(), body.getY());
        double expectedDist = body.getRadius() + heightFromEntityAngle(entity, body);
        double difference = (expectedDist - nearestDist);

        if (nearestDist < expectedDist) {
            newx = (Math.cos(angleFromCenter) * (expectedDist + (difference * 0)) ) + body.getX();
            newy = (Math.sin(angleFromCenter) * (expectedDist + (difference * 0)) ) + body.getY();
        }
        entity.setX(newx); entity.setY(newy);
    }

    // Based off of where an entity is on the body, returns the index of the body's terrain point.
    // Maybe usable for collision, since it can get the particular heightmap index accessible from the entity's position

    public static int terrainIndexFromEntityAngle(Entity entity, Body body){
        float[] terrain = body.getTerrain();
        int length = terrain.length;
        double index;
        double angle = Math.atan2(entity.getY() - body.getY(), entity.getX() - body.getX());
        float bodydir = body.getDir();

        // This maps the value to between -pi and pi
        bodydir += Math.PI;
        bodydir %= 2 * Math.PI;
        bodydir -= Math.PI;

        angle -= bodydir;

        // I was just being safe here in fear of the dreaded IndexOutOfBoundsException
        angle += Math.PI;
        angle %= 2 * Math.PI;
        angle -= Math.PI;

        if (angle < 0){
            index = angle * ((0.5 * length) / Math.PI) + terrain.length;
        }else{
            index = angle * ((0.5 * length) / Math.PI);
        }

        return (int)Math.floor(index);
    }

    // Returns 3 points, the first being the center of the body and two being surface points.
    // (Negative indices are fine)
    public static double[] getTriFromIndex(Body body, int terrainindex){
        terrainindex = loopyMod(terrainindex, body.getTerrain().length);

        int xIndex = loopyMod(2 * terrainindex, body.getAbsolutePoints().length);
        int yIndex = loopyMod((2 * terrainindex) + 1,  body.getAbsolutePoints().length);
        int xIndexNext = (2 * (terrainindex + 1)) % body.getAbsolutePoints().length;
        int yIndexNext = (2 * (terrainindex + 1) + 1) % body.getAbsolutePoints().length;
        return new double[]{
                body.getX(), body.getY(),
                body.getAbsolutePoints()[xIndex], body.getAbsolutePoints()[yIndex],
                body.getAbsolutePoints()[xIndexNext], body.getAbsolutePoints()[yIndexNext]
        };
    }

    static int loopyMod(int x, int m) {
        return (x % m + m) % m;
    }

    public static float heightFromEntityAngle(Entity entity, Body body){
        float[] terrain = body.getTerrain();
        int length = terrain.length;

        // If the player is halfway between terrainPoints 3 and 4, then it will be 3.5,
        // (for example)
        double messyIndex;
        int cleanIndex;
        double messyDifference;
        float currentHeight;
        float nextHeight;

        double interpolatedHeight;

        double angle = Math.atan2(entity.getY() - body.getY(), entity.getX() - body.getX());
        float bodydir = body.getDir();

        // This maps the value to between -pi and pi
        bodydir += Math.PI;
        bodydir %= 2 * Math.PI;
        bodydir -= Math.PI;

        angle -= bodydir;

        // I was just being safe here in fear of the dreaded IndexOutOfBoundsException
        angle += Math.PI;
        angle %= 2 * Math.PI;
        angle -= Math.PI;

        if (angle < 0){
            messyIndex = angle * ((0.5 * length) / Math.PI) + terrain.length;
        }else{
            messyIndex = angle * ((0.5 * length) / Math.PI);
        }
        cleanIndex = (int)messyIndex;
        messyDifference = messyIndex - cleanIndex;

        currentHeight = terrain[cleanIndex];
        nextHeight = terrain[(cleanIndex + 1) % terrain.length];

        interpolatedHeight = (nextHeight * messyDifference) + (currentHeight * (1 - messyDifference));
        return (float)interpolatedHeight;
    }

    public static boolean isPointCollidingInBox( double pointx, double pointy, double boxX, double boxY, double boxWidth, double boxHeight){
        return (pointx > boxX && pointx < (boxX + boxWidth) &&
                pointy < boxY && pointy > (boxY - boxHeight));
    }

    public static boolean isEntityCollidingWithEntity(Entity entity, Entity body){
        boolean isColliding;

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
