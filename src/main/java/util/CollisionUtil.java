package util;

import com.sun.javafx.geom.Vec2d;
import entity.Body;
import entity.Entity;

public class CollisionUtil {

    @Deprecated
    public static boolean isEntityCollidingWithBody (Entity entity, Body body){

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

    public static boolean isEntityCollidingWithEntity (Entity entity1, Entity entity2){
        Entity e1 = entity1;
        Entity e2 = entity2;
        double[] e1AbsPoints, e2AbsPoints;
        // Start and end points of diagonal
        double diagSX, diagSY;
        double diagEX, diagEY;

        // Start and end points of edge
        double edgeSX, edgeSY;
        double edgeEX, edgeEY;

        double displaceX, displaceY;

        for (int currentpoly = 0; currentpoly < 2; currentpoly++){
            if (currentpoly == 1){
                e1 = entity2;
                e2 = entity1;
            }
            e1AbsPoints = e1.getAbsolutePoints();
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
                        return true;
                    }

                }
//                double coefficient = (currentpoly == 0 ? -1 : 1);
//                if (!(e1 instanceof Body)){
//                    if (e2 instanceof Body){
//                        if ( ((Body) e2).canEntitiesCollide ){
//                            e1.setX( e1.getX() + displaceX * coefficient);
//                            e1.setY( e1.getY() + displaceY * coefficient);
//                        }
//                    }else{
//                        e1.setX( e1.getX() + displaceX * coefficient);
//                        e1.setY( e1.getY() + displaceY * coefficient);
//                    }
//                }
            }
        }

        return false;
    }
}
