package entity;

import world.Map;

public class Entity {
    protected double x;
    protected double y;
    protected float dir; // radians

    protected double velocity;
    protected double acceleration;

    public int ticksExisted;
    protected Map map;

    public Entity (double x, double y, float dir, Map map){
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.velocity = 0;
        this.acceleration = 0;
        this.ticksExisted = 0;
        this.map = map;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public float getDir(){
        return dir;
    }
    public double getVelocity() {
        return velocity;
    }
    public double getAcceleration() {
        return acceleration;
    }

    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }
    public void setDir(float dir){
        this.dir = dir;
    }
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }
    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public void update(){

        //this.velocity += this.acceleration;

        this.x += this.velocity * Math.cos(this.dir);
        this.y += this.velocity * Math.sin(this.dir);

        this.ticksExisted++;
    }

    public boolean isDead(){
        return false;
    }
}
