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

        if (this.getChunk() != null) {
            for (Body body : this.getChunk().getBodies()) {
                if (CollisionUtil.isEntityCollidingWithBody(this, body)) {

                    this.grounded = true;

                    // This moves the player along with a planet by anticipating where it will be in the next tick
                    if (body instanceof BodyPlanet){
                        BodyPlanet planet = (BodyPlanet)body;
                        float angle = this.map.mapTime * (float)(Math.PI / 2) / planet.orbitPeriod;
                        double futurePlanetX = MathHelper.rotX(angle, planet.orbitDistance,0) + planet.star.getX();
                        double futurePlanetY = MathHelper.rotY(angle, planet.orbitDistance, 0) + planet.star.getY();

                        this.x += (futurePlanetX - body.getX());
                        this.y += (futurePlanetY - body.getY());
                    }

                    // This moves the player along with any rotating body
                    this.dir += body.rotSpeed;
                    this.x = MathHelper.rotX(body.rotSpeed, this.x - body.getX(), this.y - body.getY()) + body.getX();
                    this.y = MathHelper.rotY(body.rotSpeed, this.x - body.getX(), this.y - body.getY()) + body.getY();

                    // This is supposed to keep the player from going through it (equal and opposite reaction y'know)
                    // but it needs a little help.
                    this.x -= this.velocity * Math.cos(this.dir);
                    this.y -= this.velocity * Math.sin(this.dir);

                    // This is that slight extra push that keeps the player out.
                    double angleFromCenter = Math.atan2(this.y - body.getY(), this.x - body.getX());
                    this.x += 0.005d * Math.cos(angleFromCenter);
                    this.y += 0.005d * Math.sin(angleFromCenter);

                    // TODO add gravity. Otherwise the player doesn't stay on the planet lol

                    this.velocity /= 1.1;
                }
            }
        }

        this.ticksExisted++;
    }

    public boolean isDead(){
        return false;
    }

    public Chunk getChunk(){
        int chunkx = (int) Math.floor( this.x / Reference.CHUNK_SIZE );
        int chunky = (int) Math.floor( this.y / Reference.CHUNK_SIZE );
        if (chunkx >= 0 && chunky >= 0){
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
    public Body groundedBody(){
        if (!grounded){
            return null;
        }else{
            for (Body body : this.getChunk().getBodies()){
                if (CollisionUtil.isEntityCollidingWithBody(this,body)){
                    return body;
                }
            }
            return null;
        }
    }
}
