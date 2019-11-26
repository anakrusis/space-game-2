package entity;

// Bodies are big entities that are attached to a chunk
// generally slow moving or even stationary... at least expected to stay within their chunk,
// get loaded with the chunk, get unloaded with the chunk...
// are large enough to be gravitationally rounded and therefore have a radius.

// comets and asteroids would not be bodies. they are fast enough to go between chunks often
// and so would be loaded with the map entities

// examples: star, planet, moon

import util.MathHelper;
import world.Chunk;
import world.Map;

public class Body extends Entity {

    protected String name;

    protected float radius;
    protected Chunk chunk;
    protected float rotSpeed; //radians per tick

    public boolean canEntitiesCollide = false;

    float[] color;

    // Each item in terrain is a relative coordinate away or toward 0, which is the radius
    protected float[] terrain;

    public Body (double x, double y, float dir, Chunk chunk, float radius, Map map){
        super(x,y,dir,map);
        this.chunk = chunk;
        this.radius = radius;
        this.mass = radius * 50;
        this.color = new float[]{1f, 1f, 1f};
        this.name = "Body";
    }

    public float[] getTerrain() {
        return terrain;
    }

    public float getRadius() {
        return radius;
    }

    @Override
    public Chunk getChunk() {
        return chunk;
    }

    @Override
    public void update() {
        this.dir += rotSpeed;
        this.ticksExisted++;
    }

    // Based off of the terrain, returns the points of the body in absolute coordinates.
    // These are used for collision and rendering.
    @Override
    public double[] getAbsolutePoints(){

        double[] absPoints = new double[terrain.length * 2];

        for (int i = 0; i < terrain.length; i++){
            double angle = this.dir + (i * (2 * Math.PI) / terrain.length);

            double pointx = MathHelper.rotX((float) angle, this.radius + terrain[i], 0.0d) + this.x;
            double pointy = MathHelper.rotY((float) angle, this.radius + terrain[i], 0.0d) + this.y;

            absPoints[2 * i] = pointx;
            absPoints[(2 * i) + 1] = pointy;
        }

        return absPoints;
    }

    public float[] getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public float getRotSpeed() {
        return rotSpeed;
    }
}
