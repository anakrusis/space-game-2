package entity;

import misc.Reference;
import util.CollisionUtil;
import util.MathHelper;
import world.Chunk;
import world.Map;

public class Entity {
    protected double x;
    protected double y;
    protected float dir; // radians

    protected double velocity;
    protected double acceleration;
    protected boolean grounded;
    protected Body groundedBody;
    protected float mass;

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
        this.grounded = false;
        this.mass = 1f;
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

        this.x += this.velocity * Math.cos(this.dir);
        this.y += this.velocity * Math.sin(this.dir);

        // These are physics that don't apply to stationary bodies. Just small entities like ships, asteroids...
        if (this.getChunk() != null) {
            for (Body body : this.getChunk().getBodies()) {
                if (CollisionUtil.isEntityCollidingWithBody(this, body)) {

                    this.grounded = true;
                    this.groundedBody = body;

                }else{
//                    // Gravitation
//                    double forceMagnitude = (0.01d)* (this.mass * body.mass) / (MathHelper.distance(this.x, this.y, body.getX(), body.getY()));
//
//                    double angleFromCenter = Math.atan2(this.y - body.getY(), this.x - body.getX());
//
//                    int index = CollisionUtil.terrainIndexFromEntityAngle(this, body);
//                    double radius = body.getRadius() + body.getTerrain()[index] + 5d;
//
//                    if (MathHelper.distance(this.x, this.y, body.getX(), body.getY()) > radius){
//                        this.x -= forceMagnitude * Math.cos(angleFromCenter);
//                        this.y -= forceMagnitude * Math.sin(angleFromCenter);
//
//                    }
                }
            }
        }

        if (grounded && groundedBody != null) {
            // This moves the player along with a planet by anticipating where it will be in the next tick
            if (groundedBody instanceof BodyPlanet) {
                BodyPlanet planet = (BodyPlanet) groundedBody;
                float angle = this.map.mapTime * (float) (Math.PI / 2) / planet.orbitPeriod;
                double futurePlanetX = MathHelper.rotX(angle, planet.orbitDistance, 0) + planet.star.getX();
                double futurePlanetY = MathHelper.rotY(angle, planet.orbitDistance, 0) + planet.star.getY();

                this.x += (futurePlanetX - planet.getX());
                this.y += (futurePlanetY - planet.getY());
            }
            Body body = groundedBody;

            // This moves the player along with any rotating body
            this.dir += body.rotSpeed;
            this.x = MathHelper.rotX(body.rotSpeed, this.x - body.getX(), this.y - body.getY()) + body.getX();
            this.y = MathHelper.rotY(body.rotSpeed, this.x - body.getX(), this.y - body.getY()) + body.getY();

            double angleFromCenter = Math.atan2(this.y - body.getY(), this.x - body.getX());
            int index = CollisionUtil.terrainIndexFromEntityAngle(this, body);
            double radius = body.getRadius() + body.getTerrain()[index] + 0.3d;

            this.x = (Math.cos(angleFromCenter) * radius) + body.getX();
            this.y = (Math.sin(angleFromCenter) * radius) + body.getY();

            double distance = MathHelper.distance(this.x, this.y, body.getX(), body.getY());
            if (distance > radius) {
                this.grounded = false;
                this.groundedBody = null;
            }

            this.velocity /= 1.1;
        }

        this.ticksExisted++;
    }

    public boolean isDead(){
        return false;
    }

    public Chunk getChunk(){
        int chunkx = (int) Math.floor( this.x / Reference.CHUNK_SIZE );
        int chunky = (int) Math.floor( this.y / Reference.CHUNK_SIZE );
        if (chunkx >= 0 && chunky >= 0 && chunkx < map.getChunks().length && chunky < map.getChunks()[0].length){
            Chunk entityChunk = this.map.getChunks()[chunkx][chunky];
            return entityChunk;
        }else{
            return null;
        }
    }

    // This method is used for collision handling.
    // The default for an unspecified entity is just to return its position (meaning it only collides as a single point).
    // If it has a definite shape then it should override this and use that!

    public double[] getAbsolutePoints(){
        return new double[] { this.x , this.y };
    }

    // Is the entity grounded on a body?
    public boolean isGrounded() {
        return grounded;
    }

    // If so, which body?
    public Body getGroundedBody(){
        if (!grounded){
            return null;
        }else{
            return groundedBody;
        }
    }

    public void explode(){

    }
}
