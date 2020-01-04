package com.adnre.spacegame.util;

import com.adnre.spacegame.entity.body.Body;
import com.adnre.spacegame.entity.Entity;

public class CollisionUtil {

    // Todo fix still existing collision issues

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

    // Based off of where an com.adnre.spacegame.entity is on the body, returns the index of the body's terrain point.
    // Maybe usable for collision, since it can get the particular heightmap index accessible from the com.adnre.spacegame.entity's position

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

    public static void resolveCollision(Entity entity1, Entity entity2){
        Entity e1 = entity1;
        Entity e2 = entity2;
        double[] e1AbsPoints, e2AbsPoints;
        // Start and end points of diagonal
        double diagSX, diagSY;
        double diagEX, diagEY;

        // Start and end points of edge
        double edgeSX, edgeSY;
        double edgeEX, edgeEY;

        int terrainindex;

        double displaceX, displaceY;

        for (int currentpoly = 0; currentpoly < 2; currentpoly++){
            if (currentpoly == 1){
                e1 = entity2;
                e2 = entity1;
            }
            if (e1 instanceof Body){
                e1AbsPoints = CollisionUtil.getTriFromIndex((Body)e1, CollisionUtil.terrainIndexFromEntityAngle(e2, (Body)e1));
            }
            e1AbsPoints = e1.getAbsolutePoints();

            // I tried doing a single triangle "pizza slice" of the body, but it seems
            // to yield glitchier results than the full poly. Regardless, it is more optimized
            if (e2 instanceof Body){
                e2AbsPoints = CollisionUtil.getTriFromIndex((Body)e2, CollisionUtil.terrainIndexFromEntityAngle(e1, (Body)e2));
            }
            e2AbsPoints = e2.getAbsolutePoints();


            for (int p = 0; p < e1AbsPoints.length; p += 2){
                diagSX = e1.getX();
                diagSY = e1.getY();
                diagEX = e1AbsPoints[p];
                diagEY = e1AbsPoints[p + 1];
                displaceX = 0;
                displaceY = 0;

                for (int q = 0; q < e2AbsPoints.length; q += 2){
                    edgeSX = e2AbsPoints[q];
                    edgeSY = e2AbsPoints[q + 1];
                    edgeEX = e2AbsPoints[ (q+2) % e2AbsPoints.length ];
                    edgeEY = e2AbsPoints[ (q+3) % e2AbsPoints.length ];

                    double h = (edgeEX - edgeSX) * (diagSY - diagEY) - (diagSX - diagEX) * (edgeEY - edgeSY);
                    double t1 = ((edgeSY - edgeEY) * (diagSX - edgeSX) + (edgeEX - edgeSX) * (diagSY - edgeSY)) / h;
                    double t2 = ((diagSY - diagEY) * (diagSX - edgeSX) + (diagEX - diagSX) * (diagSY - edgeSY)) / h;
                    if (t1 >= 0.0f && t1 < 1.0f && t2 >= 0.0f && t2 < 1.0f)
                    {
                        displaceX += (1.0f - t1) * (diagEX - diagSX);
                        displaceY += (1.0f - t1) * (diagEY - diagSY);
                    }
                }
                double coefficient = (currentpoly == 0 ? -1f : 1f);

                if (!(e1 instanceof Body)){
                    e1.setX( e1.getX() + displaceX * coefficient);
                    e1.setY( e1.getY() + displaceY * coefficient);
                }
            }

        }
    }

    // Don't worry about negative indices; this method can handle them.
    // Returns 3 points, the first being the center of the body and two being surface points.
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

        interpolatedHeight = (currentHeight * messyDifference) + (nextHeight * (1 - messyDifference));
        return (float)interpolatedHeight;
    }

    public static boolean isPointCollidingInBox( double pointx, double pointy, double boxX, double boxY, double boxWidth, double boxHeight){
        return (pointx > boxX && pointx < (boxX + boxWidth) &&
                pointy < boxY && pointy > (boxY - boxHeight));
    }
}
